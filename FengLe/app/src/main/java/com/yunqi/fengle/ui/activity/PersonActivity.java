package com.yunqi.fengle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;

import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.constants.Constants;
import com.yunqi.fengle.presenter.PersonPresenter;
import com.yunqi.fengle.presenter.StockQueryPresenter;
import com.yunqi.fengle.presenter.contract.PersonContract;
import com.yunqi.fengle.presenter.contract.StockQueryContract;
import com.yunqi.fengle.util.LogEx;
import com.yunqi.fengle.util.PrefrenceUtils;
import com.yunqi.fengle.util.ToastUtil;

import butterknife.OnClick;

/**
 * @Author: Huangweicai
 * @date 2017-02-15 18:00
 * @Description:个人中心
 */

public class PersonActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        LogEx.e("onCreate");
        setTitleText("个人信息");
//        setTitleRightImage(R.drawable.right_add);
//        setTitleRight("新增");
    }

//    @Override
//    protected void onTitleRightClicked(View v) {
//        ToastUtil.toast(this,"点击");
//    }


    @OnClick(R.id.tvChangePwd)
    public void onChangePwd() {
        Intent mIntent = new Intent();
        mIntent.setClass(this, PersonChangePwdActivity.class);
        startActivity(mIntent);
    }
    @OnClick(R.id.tvLogout)
    public void onLogout() {
        App.getInstance().killAllActivities();
        PrefrenceUtils.getInstance(this).saveLoginStatus(Constants.STATUS_UNLOGIN);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 注意本行的FLAG设置
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.llBasicInfo)
    public void onBasicInfo() {
        Intent mIntent = new Intent();
        mIntent.setClass(this, PersonInfoActivity.class);
        startActivity(mIntent);
    }

    @Override
    protected int getViewModel() {
        return MODE_VIEW_02;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_person;
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
