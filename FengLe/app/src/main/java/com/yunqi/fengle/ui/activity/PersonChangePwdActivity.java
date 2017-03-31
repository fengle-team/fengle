package com.yunqi.fengle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.constants.Constants;
import com.yunqi.fengle.presenter.PersonPresenter;
import com.yunqi.fengle.presenter.contract.PersonContract;
import com.yunqi.fengle.util.PrefrenceUtils;
import com.yunqi.fengle.util.ToastUtil;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author: Huangweicai
 * @date 2017-02-15 18:00
 * @Description:个人中心---->修改密码
 */

public class PersonChangePwdActivity extends BaseActivity<PersonPresenter> implements PersonContract.View{

    @BindView(R.id.etConfirmPwd)
    EditText etConfirmPwd;
    @BindView(R.id.etNewPwd)
    EditText etNewPwd;
    @BindView(R.id.etOldPwd)
    EditText etOldPwd;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("修改密码");
        setTitleRight("提交");
    }

    @Override
    protected void onTitleRightClicked(View v) {
        progresser.showProgress();
        mPresenter.checkFormat(etOldPwd.getText().toString(), etNewPwd.getText().toString(), etConfirmPwd.getText().toString(), new ResponseListener() {
            @Override
            public void onSuccess() {
                progresser.showContent();
                ToastUtil.toast(mContext,"密码修改成功!");
                App.getInstance().killAllActivities();
                PrefrenceUtils.getInstance(PersonChangePwdActivity.this).saveLoginStatus(Constants.STATUS_UNLOGIN);
                Intent intent = new Intent(PersonChangePwdActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 注意本行的FLAG设置
                startActivity(intent);
                finish();
            }

            @Override
            public void onFaild(NetResponse response) {
                progresser.showContent();
                ToastUtil.toast(mContext,response.getMsg());
            }
        });
    }


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
        return R.layout.activity_person_change_pwd;
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
