package com.xupeng.sample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.xupeng.addresspicker.AddressPicker;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goAddressPicker(View v) {
        AddressPicker.createBuilder(this, getSupportFragmentManager())
                .setHeight(800)
                .setListener(new AddressPicker.AddressPickerListener() {
                    @Override
                    public void onDismiss(AddressPicker actionSheet) {
                        Toast.makeText(MainActivity.this, "dismiss", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSelectedComplete(String address, String provinceStr, String cityStr, String districtStr) {
                        Toast.makeText(MainActivity.this, provinceStr + " " + cityStr + " " + districtStr, Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }

}
