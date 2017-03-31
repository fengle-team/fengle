package com.yunqi.fengle.util.map;

import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.util.LogEx;

/**
 * @Author: Huangweicai
 * @date 2017-02-22 13:57
 * @Description: 高德地图获取定位管理
 */

public class MapLocationMgr {

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener;
    //声明AMapLocationClientOption对象
    AMapLocationClientOption mLocationOption = null;

    private ResponseListener listener;

    private static MapLocationMgr mInstance;

    private boolean isStarted = false;

    public MapLocationMgr() {
        init();
    }

//    public static MapLocationMgr getInstance() {
//        if (mInstance == null) {
//            synchronized (MapLocationMgr.class) {
//                if (mInstance == null) {
//                    mInstance = new MapLocationMgr();
//                }
//            }
//        }
//        return mInstance;
//    }

    private void init() {
        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                        LogEx.e("获得定位");
                        if (listener != null) {
                            LocationModel model = new LocationModel();
                            model.setAddress(amapLocation.getAddress());
                            model.setLatitude(amapLocation.getLatitude());
                            model.setLongitude(amapLocation.getLongitude());
                            listener.onSuccess(new NetResponse(0,"success",model));
                        }
                    } else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                        listener.onFaild(new NetResponse(-1,"获取定位失败"));
                    }
                }
            }
        };
        //初始化定位
        mLocationClient = new AMapLocationClient(App.getInstance());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

//初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //获取一次定位结果：
        //该方法默认为false。
//        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //关闭缓存机制
//        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
    }

    public void start(ResponseListener listener) {
        this.listener = listener;
//        if (mLocationClient.isStarted()) {
//            return;
//        }
        if (isStarted) {
            return;
        }
        //启动定位
        mLocationClient.startLocation();
        isStarted = true;
    }


    private void stop() {
        mLocationClient.stopLocation();
    }

    public void onDestory() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
            mLocationClient = null;
        }
    }

}
