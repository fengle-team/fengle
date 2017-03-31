package com.yunqi.fengle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.ui.view.RoundedLetterView;

import butterknife.BindView;

/**
 * @Author: Huangweicai
 * @date 2017-02-17 15:32
 * @Description:移动考勤
 */

public class MoveAttendanceActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.rvSignIn)
    RoundedLetterView rvSignIn;
    @BindView(R.id.rvSignOut)
    RoundedLetterView rvSignOut;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("移动考勤");
        initView();
    }

    private void initView() {
        rvSignIn.setOnClickListener(this);
        rvSignOut.setOnClickListener(this);
    }

    @Override
    protected int getViewModel() {
        return MODE_VIEW_02;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_move_attendance;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent mIntent = new Intent();
        mIntent.setClass(this, MoveAttendanceSignInActivity.class);
        int type = MoveAttendanceSignInActivity.TYPE_SIGN_IN;
        switch (v.getId()) {
            case R.id.rvSignIn://签到
                type = MoveAttendanceSignInActivity.TYPE_SIGN_IN;
                break;
            case R.id.rvSignOut://签退
                type = MoveAttendanceSignInActivity.TYPE_SIGN_OUT;
                break;
        }
        mIntent.putExtra(MoveAttendanceSignInActivity.TAG_TYPE, type);
        startActivity(mIntent);
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

    @Override
    protected void initInject() {

    }

    @Override
    protected void initEventAndData() {

    }
}
