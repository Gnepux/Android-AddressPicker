package com.xupeng.addresspicker;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xupeng.constants.AddressConstant;

/**
 * Created by xupeng on 2015/9/10.
 */
public class AddressPicker extends Fragment implements View.OnClickListener {

    private static final int TRANSLATE_DURATION = 200;

    private static final int ALPHA_DURATION = 300;

    private int PAGE_PROVINCE = 1;

    private int PAGE_CITY = 2;

    private int PAGE_DISTRICT = 3;

    // 选择器的高度， 默认800px
    private static float mHeightPx = 800;

    // 当前页
    private int currentPage;

    private View mainView;

    Button provinceButton, cityButton, districtButton;

    View underLineProvince, underLineCity, underLineDistrict;

    ListView addressPickerListView;

    ImageView closeImageView;

    private boolean mIsProvinceSelected = false, mIsCitySelected = false, mIsDistrictSelected = false;

    private int mLastProvince = -1, mLastCity = -1, mLastDistrict = -1;

    private String mLastProvinceName, mLastCityName, mLastDistrictName;

    private AddressPickerListener mListener;

    private boolean mDismissed = true;

    private View mView;

    private ViewGroup mGroup;

    private View mBg;

    private LinearLayout mPanel;

    MyAdapter mAdapter;

    private class MyAdapter extends BaseAdapter {

        String[] addressList = null;

        class ViewHolder {
            TextView text;
        }

        public void setDataList(String[] addressList) {
            this.addressList = addressList;
        }

        @Override
        public int getCount() {
            return addressList.length;
        }

        @Override
        public Object getItem(int position) {
            return addressList[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.listview_item_address_select, null);
                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.tv_address);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.text.setText(addressList[position]);
            return convertView;
        }
    }

    public static Builder createBuilder(Context context,
                                        FragmentManager fragmentManager) {
        return new Builder(context, fragmentManager);
    }

    public static class Builder {

        private Context mContext;
        private FragmentManager mFragmentManager;
        private String mTag = "addressPicker";
        private AddressPickerListener mListener;

        public Builder(Context context, FragmentManager fragmentManager) {
            mContext = context;
            mFragmentManager = fragmentManager;
        }

        public Builder setListener(AddressPickerListener listener) {
            this.mListener = listener;
            return this;
        }

        /**
         * 设置空间高度
         * @param heightPx
         * @return
         */
        public Builder setHeight(float heightPx) {
            mHeightPx = heightPx;
            return this;
        }

        public AddressPicker show() {
            AddressPicker addressPicker = (AddressPicker) Fragment.instantiate(
                    mContext, AddressPicker.class.getName(), null);
            addressPicker.setActionSheetListener(mListener);
            addressPicker.show(mFragmentManager, mTag);
            return addressPicker;
        }
    }

    private void setActionSheetListener(AddressPickerListener listener) {
        mListener = listener;
    }

    private void show(FragmentManager manager, String tag) {
        if (!mDismissed) {
            return;
        }
        mDismissed = false;
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void dismiss() {
        if (mDismissed) {
            return;
        }
        mDismissed = true;
        getFragmentManager().popBackStack();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.remove(this);
        ft.commit();
    }

    public static interface AddressPickerListener {

        void onDismiss(AddressPicker addressPicker);

        void onSelectedComplete(String address, String provinceStr, String cityStr, String districtStr);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            View focusView = getActivity().getCurrentFocus();
            if (focusView != null) {
                imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
            }
        }

        initView();

        mView = createView();
        mGroup = (ViewGroup) getActivity().getWindow().getDecorView();

        mPanel.addView(mainView);

        mGroup.addView(mView);
        mBg.startAnimation(createAlphaInAnimation());
        mPanel.startAnimation(createTranslationInAnimation());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private View createView() {
        FrameLayout parent = new FrameLayout(getActivity());
        parent.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        mBg = new View(getActivity());
        mBg.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        mBg.setBackgroundColor(Color.argb(136, 0, 0, 0));
        mBg.setOnClickListener(this);

        mPanel = new LinearLayout(getActivity());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, (int)(mHeightPx / getResources().getDisplayMetrics().density));
        params.gravity = Gravity.BOTTOM;
        mPanel.setLayoutParams(params);
        mPanel.setOrientation(LinearLayout.VERTICAL);

        parent.addView(mBg);
        parent.addView(mPanel);
        return parent;
    }

    private Animation createTranslationInAnimation() {
        int type = TranslateAnimation.RELATIVE_TO_SELF;
        TranslateAnimation an = new TranslateAnimation(type, 0, type, 0, type,
                1, type, 0);
        an.setDuration(TRANSLATE_DURATION);
        return an;
    }

    private Animation createAlphaInAnimation() {
        AlphaAnimation an = new AlphaAnimation(0, 1);
        an.setDuration(ALPHA_DURATION);
        return an;
    }

    private Animation createTranslationOutAnimation() {
        int type = TranslateAnimation.RELATIVE_TO_SELF;
        TranslateAnimation an = new TranslateAnimation(type, 0, type, 0, type,
                0, type, 1);
        an.setDuration(TRANSLATE_DURATION);
        an.setFillAfter(true);
        return an;
    }

    private Animation createAlphaOutAnimation() {
        AlphaAnimation an = new AlphaAnimation(1, 0);
        an.setDuration(ALPHA_DURATION);
        an.setFillAfter(true);
        return an;
    }

    private void initView() {

        mainView = LayoutInflater.from(getActivity()).inflate(R.layout.address_selector, null);

        provinceButton = (Button) mainView.findViewById(R.id.btn_province);
        cityButton = (Button) mainView.findViewById(R.id.btn_city);
        districtButton = (Button) mainView.findViewById(R.id.btn_district);

        underLineProvince = mainView.findViewById(R.id.view_undlerline_provice);
        underLineCity = mainView.findViewById(R.id.view_undlerline_city);
        underLineDistrict = mainView.findViewById(R.id.view_undlerline_district);

        provinceButton.setOnClickListener(this);
        cityButton.setOnClickListener(this);
        districtButton.setOnClickListener(this);

        closeImageView = (ImageView) mainView.findViewById(R.id.iv_address_close);
        closeImageView.setOnClickListener(this);

        switchToProvince();

        addressPickerListView = (ListView) mainView.findViewById(R.id.lv_address);
        mAdapter = new MyAdapter();

        addressPickerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentPage == PAGE_PROVINCE) {
                    mLastProvince = position;
                    mLastProvinceName = (String) mAdapter.getItem(position);
                    refreshProvince(position);
                } else if (currentPage == PAGE_CITY) {
                    mLastCity = position;
                    mLastCityName = (String) mAdapter.getItem(position);
                    refreshCity(position);
                } else if (currentPage == PAGE_DISTRICT) {
                    mLastCity = position;
                    mLastDistrictName = (String) mAdapter.getItem(position);
                    refreshDistrict(position);
                }
            }
        });

        mAdapter.setDataList(AddressConstant.getProvinceList());

        addressPickerListView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        mPanel.startAnimation(createTranslationOutAnimation());
        mBg.startAnimation(createAlphaOutAnimation());
        mView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mGroup.removeView(mView);
            }
        }, ALPHA_DURATION);
        if (mListener != null) {
            mListener.onDismiss(this);
        }
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {

        int vId = v.getId();

        if (vId == R.id.btn_province) {
            switchToProvince();
            if (mAdapter != null) {
                mAdapter.setDataList(AddressConstant.getProvinceList());
                mAdapter.notifyDataSetChanged();
            }
        } else if (vId == R.id.btn_city) {
            if (mIsProvinceSelected) {
                switchToCity();
                refreshProvince(mLastProvince);
            }
        } else if (vId == R.id.btn_district) {

        } else if (vId == R.id.iv_address_close) {
            dismiss();
        } else {

        }

    }

    private void switchToProvince() {
        underLineProvince.setBackgroundColor(getResources().getColor(R.color.main_blue));
        underLineCity.setBackgroundColor(getResources().getColor(R.color.transparent));
        underLineDistrict.setBackgroundColor(getResources().getColor(R.color.transparent));
        currentPage = PAGE_PROVINCE;
        mIsProvinceSelected = false;
        mIsCitySelected = false;
        mIsDistrictSelected = false;
    }

    private void switchToCity() {
        underLineProvince.setBackgroundColor(getResources().getColor(R.color.transparent));
        underLineCity.setBackgroundColor(getResources().getColor(R.color.main_blue));
        underLineDistrict.setBackgroundColor(getResources().getColor(R.color.transparent));
        currentPage = PAGE_CITY;
        mIsProvinceSelected = true;
        mIsCitySelected = false;
        mIsDistrictSelected = false;
    }

    private void switchToDistrict() {
        underLineProvince.setBackgroundColor(getResources().getColor(R.color.transparent));
        underLineCity.setBackgroundColor(getResources().getColor(R.color.transparent));
        underLineDistrict.setBackgroundColor(getResources().getColor(R.color.main_blue));
        currentPage = PAGE_DISTRICT;
        mIsProvinceSelected = true;
        mIsCitySelected = true;
        mIsDistrictSelected = true;
    }

    private void refreshProvince(int position) {
        mIsProvinceSelected = true;
        // 获取城市列表
        String[] cityList = AddressConstant.getCityList(mLastProvinceName);
        if (cityList == null) return;
        mAdapter.setDataList(cityList);
        mAdapter.notifyDataSetChanged();
        if (mIsProvinceSelected) {
            switchToCity();
        }
    }

    private void refreshCity(int position) {
        mIsCitySelected = true;
        // 获取行政区列表
        String[] districtList = AddressConstant.getDistrictList(mLastProvinceName + mLastCityName);
        if (districtList == null) return;
        mAdapter.setDataList(districtList);
        mAdapter.notifyDataSetChanged();
        if (mIsCitySelected) {
            switchToDistrict();
        }
    }

    private void refreshDistrict(int position) {
        String address = mLastProvinceName + "-" + mLastCityName + "-" + mLastDistrictName;
        if (mListener != null) {
            mListener.onSelectedComplete(address, mLastProvinceName, mLastCityName, mLastDistrictName);
        }
        dismiss();
    }

}
