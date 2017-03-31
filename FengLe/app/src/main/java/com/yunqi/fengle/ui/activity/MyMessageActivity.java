package com.yunqi.fengle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseActivity;

import butterknife.BindView;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 11:42
 * @Description:我的消息
 */
public class MyMessageActivity extends BaseActivity implements View.OnClickListener{

//    @BindView(R.id.rlNotice)
//    RelativeLayout rlNotice;
//    @BindView(R.id.rlMessage)
//    RelativeLayout rlMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("我的消息");

        initView();
    }

    private void initView() {
        this.findViewById(R.id.rlNotice).setOnClickListener(this);
        this.findViewById(R.id.rlMessage).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent mIntent = new Intent();
        switch (v.getId()) {
            case R.id.rlNotice:
                mIntent.setClass(MyMessageActivity.this, NoticeActivity.class);
                startActivity(mIntent);
                break;
            case R.id.rlMessage:
                mIntent.setClass(MyMessageActivity.this, MessageActivity.class);
                startActivity(mIntent);
                break;
        }

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
        return R.layout.activity_my_message;
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
