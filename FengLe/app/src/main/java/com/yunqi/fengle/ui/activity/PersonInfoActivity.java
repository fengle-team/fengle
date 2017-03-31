package com.yunqi.fengle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.UserBean;
import com.yunqi.fengle.presenter.PersonPresenter;
import com.yunqi.fengle.presenter.contract.PersonContract;
import com.yunqi.fengle.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author: Huangweicai
 * @date 2017-02-15 18:00
 * @Description: {@link PersonActivity}
 */

public class PersonInfoActivity extends BaseActivity<PersonPresenter> implements PersonContract.View{
    @BindView(R.id.tvAcount)
    TextView tvAcount;
    @BindView(R.id.tvPosition)
    TextView tvPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("基本信息");
        initView();
    }

    private void initView() {
        UserBean user = App.getInstance().getUserInfo();
        tvAcount.setText(user.account);
        tvPosition.setText(user.position);
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
        return R.layout.activity_person_info;
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
