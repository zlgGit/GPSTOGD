package com.example.zangliguang.gpstogd;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps2d.CoordinateConverter;
import com.amap.api.maps2d.model.LatLng;

public class MainActivity extends AppCompatActivity {

    private static final int GPS_REQUEST_CODE = 1001;
    private boolean isGpsEnabled;
    private LocationManager locationManager;

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String GPS_LOCATION_NAME = android.location.LocationManager.GPS_PROVIDER;
    private static final int REQUEST_PRESSMION_CODE = 10000;
    private final static String[] MULTI_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION};
    private String locateType;
    private TextView mGpsText;
    private TextView mGdText;
    private ClipboardManager mClip;
    private Button mCopy;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();


    }

    private void initView() {

        mGpsText = (TextView) findViewById(R.id.gps_id);
        mGdText = (TextView) findViewById(R.id.GD_id);
        mEditText = (EditText) findViewById(R.id.edit);
        mCopy = (Button) findViewById(R.id.btn);
        mCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable text = mEditText.getText();

                mClip.setText("高德："+mGdText.getText()+"\n"
                                +"GPS： "+mGpsText.getText()+"\n"
                                +text.toString());
                Toast.makeText(MainActivity.this,"复制到剪切板成功",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {

        //获取定位服务
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        //判断是否开启GPS定位功能
        isGpsEnabled = locationManager.isProviderEnabled(GPS_LOCATION_NAME);
        //定位类型：GPS
        locateType = locationManager.GPS_PROVIDER;
        openGPSSettings();


        mClip = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);


    }

    private LocationListener mLocationListener = new LocationListener() {

        /**
         * 位置信息变化时触发:当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
         * @param location
         */
        @Override
        public void onLocationChanged(Location location) {
            Toast.makeText(MainActivity.this, "onLocationChanged函数被触发！", Toast.LENGTH_SHORT).show();
            updateUI(location);
            Log.i(TAG, "时间：" + location.getTime());
            Log.i(TAG, "经度：" + location.getLongitude());
            Log.i(TAG, "纬度：" + location.getLatitude());
            Log.i(TAG, "海拔：" + location.getAltitude());
            updateUI(location);
        }

        /**
         * GPS状态变化时触发:Provider被disable时触发此函数，比如GPS被关闭
         * @param provider
         * @param status
         * @param extras
         */
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                //GPS状态为可见时
                case LocationProvider.AVAILABLE:
                    Toast.makeText(MainActivity.this, "onStatusChanged：当前GPS状态为可见状态", Toast.LENGTH_SHORT).show();
                    break;
                //GPS状态为服务区外时
                case LocationProvider.OUT_OF_SERVICE:
                    Toast.makeText(MainActivity.this, "onStatusChanged:当前GPS状态为服务区外状态", Toast.LENGTH_SHORT).show();
                    break;
                //GPS状态为暂停服务时
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Toast.makeText(MainActivity.this, "onStatusChanged:当前GPS状态为暂停服务状态", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };


    /**
     * 方法描述：在View上更新位置信息的显示
     *
     * @param location
     */
    private void updateUI(Location location) {
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        double latitude2 = location.getLatitude();


        CoordinateConverter converter = new CoordinateConverter();
// CoordType.GPS 待转换坐标类型
        converter.from(CoordinateConverter.CoordType.GPS);
// sourceLatLng待转换坐标点 LatLng类型
        converter.coord(new LatLng(latitude,longitude));
        // 执行转换操作
        LatLng desLatLng = converter.convert();

        mGpsText.setText("纬度："+latitude+"  经度："+longitude);
        mGdText.setText("纬度："+desLatLng.latitude+"  经度："+desLatLng.longitude);


    }


    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            request(MULTI_PERMISSIONS);
            return;
        }
        Location location = locationManager.getLastKnownLocation(locateType); // 通过GPS获取位置
        if (location != null) {
            updateUI(location);
        }
        // 设置监听*器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
        locationManager.requestLocationUpdates(locateType, 100, 0,
                mLocationListener);

    }

    private void request(String[] multiPermissions) {

        ActivityCompat.requestPermissions(this, multiPermissions, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                return;
            }
        }
        getLocation();
    }

    private boolean checkGPSIsOpen() {
        boolean isOpen;
        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        isOpen = locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
        return isOpen;
    }


    /**
     * 跳转GPS设置
     */
    private void openGPSSettings() {
        if (checkGPSIsOpen()) {
            getLocation();
        } else {

            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, GPS_REQUEST_CODE);


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPS_REQUEST_CODE) {
            //做需要做的事情，比如再次检测是否打开GPS了 或者定位
            openGPSSettings();
        }
    }


}
