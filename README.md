#Android-AddressPicker

Android地址选择器

###调用方法
```Java
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
```

###运行截图
![](https://github.com/Gnepux/Android-AddressPicker/blob/master/snapshot.png)
