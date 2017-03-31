package com.yunqi.fengle.ui.activity;

import android.Manifest;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.request.SignAddRequest;
import com.yunqi.fengle.presenter.MoveAttendancePresenter;
import com.yunqi.fengle.presenter.contract.MoveAttendanceContract;
import com.yunqi.fengle.ui.fragment.dialog.SimpleDialogEditFragment;
import com.yunqi.fengle.ui.fragment.dialog.SimpleDialogFragment;
import com.yunqi.fengle.util.DateUtil;
import com.yunqi.fengle.util.DialogHelper;
import com.yunqi.fengle.util.PermissionsUtils;
import com.yunqi.fengle.util.ToastUtil;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @Author: Huangweicai
 * @date 2017-02-17 15:58
 * @Description:移动考勤 {@link MoveAttendanceActivity}
 *              签到
 */

public class MoveAttendanceSignInActivity extends BaseActivity<MoveAttendancePresenter> implements MoveAttendanceContract.View,LocationSource,AMapLocationListener {
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.tvAddr)
    TextView tvAddr;
    @BindView(R.id.tvBack)
    TextView tvBack;
    @BindView(R.id.tvNote)
    TextView tvNote;
    @BindView(R.id.tvReq)
    TextView tvReq;
    @BindView(R.id.tvDate)
    TextView tvDate;

    public static int TYPE_SIGN_IN = 0x01;
    public static int TYPE_SIGN_OUT = 0x02;
    public static int TYPE_TYPE = TYPE_SIGN_IN;
    public static String TAG_TYPE = "type";

    private long timeNow;

    private AMap aMap;
    private UiSettings mUiSettings;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private AMapLocation amapLocation;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("移动考勤");
        initView();

        if (!PermissionsUtils.isLocation(getApplicationContext())) {
            ToastUtil.toast(this, "请开启手机定位");
            return;
        }

        initMap(savedInstanceState);
        doLocation();
    }

    private void initView() {
        timeNow = System.currentTimeMillis();

        if (getIntent().getIntExtra(TAG_TYPE,TYPE_SIGN_IN) == TYPE_SIGN_IN) {
            TYPE_TYPE = TYPE_SIGN_IN;
            tvReq.setText("我没迟到");
        } else {
            TYPE_TYPE = TYPE_SIGN_OUT;
            tvReq.setText("收工了哦");
        }

        tvDate.setText(DateUtil.format(timeNow,DateUtil.P13));

        tvBack.setOnClickListener(this);
        tvNote.setOnClickListener(this);
        tvReq.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tvBack:
                finish();
                break;
            case R.id.tvNote:
                DialogHelper.showEditDialog(this, "请输入说明", new SimpleDialogEditFragment.OnSimpleDialogEditListener() {
                    @Override
                    public void onOk(String content) {
                        doSign(content);
                    }
                });
                break;
            case R.id.tvReq:
                String msg = "确定签到?";
                if (getIntent().getIntExtra(TAG_TYPE,TYPE_SIGN_IN) == TYPE_SIGN_IN) {
                    msg = "确定签到?";
                } else {
                    msg = "确定签退?";
                }
                DialogHelper.showDialog(this, msg, new SimpleDialogFragment.OnSimpleDialogListener() {
                    @Override
                    public void onOk() {
                        doSign();
                    }
                });
                break;
        }
    }

    private void doSign(String content) {
        if (amapLocation == null || !PermissionsUtils.isLocation(getApplicationContext())) {
            ToastUtil.toast(this, "请开启手机定位");
            return;
        }

        final SignAddRequest request = new SignAddRequest();
        request.setSign_address(amapLocation.getAddress());
        request.setLat(amapLocation.getLatitude() + "");
        request.setLng(amapLocation.getLongitude() + "");
        request.setSign_type(TYPE_TYPE + "");
        request.setRemark(content);
        request.setUserid(App.getInstance().getUserInfo().id);
        progresser.showProgress();
        mPresenter.sign(request, new ResponseListener() {
            @Override
            public void onSuccess() {
                if (TYPE_TYPE == TYPE_SIGN_IN) {
                    ToastUtil.toast(MoveAttendanceSignInActivity.this,"签到成功");
                } else {
                    ToastUtil.toast(MoveAttendanceSignInActivity.this,"签退成功");
                }
                finish();
                progresser.showContent();
            }

            @Override
            public void onFaild(NetResponse response) {
                progresser.showError(response.getMsg());
            }
        });
    }

    private void doSign() {
        doSign("");
    }

    /*******MAP START******/
    private void initMap(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        if (aMap == null) {
            aMap = mapView.getMap();
            mUiSettings = aMap.getUiSettings();
        }
    }

    private void doLocation() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.setMyLocationEnabled(true);// 是否可触发定位并显示定位层
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                this.amapLocation = amapLocation;
                tvAddr.setText(amapLocation.getAddress());
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        if (mapView != null) {
            mapView.onDestroy();
            mapView = null;
        }
        mPresenter.unSubscribe();
        super.onDestroy();
    }

    /*******MAP end******/


    @Override
    protected int getViewModel() {
        return MODE_VIEW_02;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_move_attendance_sign_in;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void showError(String msg) {

    }




}
