package com.yunqi.fengle.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.constants.Constants;
import com.yunqi.fengle.model.bean.UserBean;
import com.yunqi.fengle.presenter.LoginPresenter;
import com.yunqi.fengle.presenter.contract.LoginContract;
import com.yunqi.fengle.util.PrefrenceUtils;
import com.yunqi.fengle.util.ToastUtil;

import butterknife.BindView;

public class SplashActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    @BindView(R.id.iv_splash_bg)
    ImageView ivSplashBg;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_splash1;
    }

    @Override
    protected void initEventAndData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkAutoLogin();
            }
        },1000);

    }


    private void checkAutoLogin(){
        String loginName = PrefrenceUtils.getInstance(this).getLoginName();
        String password = PrefrenceUtils.getInstance(this).getPassword();
        int loginStatus = PrefrenceUtils.getInstance(this).getLoginStatus();
        //登陆为已登陆状态，并且用户名和密码都不为空时才执行自动登陆功能
        if (loginStatus == Constants.STATUS_LOGINED && !TextUtils.isEmpty(loginName) && !TextUtils.isEmpty(password)) {
            mPresenter.doLogin(loginName, password);
        } else {
            jump2Login();
        }
    }

    @Override
    public void jump2Main(UserBean userBean) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void showLoading() {
        Log.d(TAG, "----------自动登陆中----------------");
    }

    @Override
    public void cancelLoading() {
        Log.d(TAG, "----------cancelLoading---------------");
    }

    @Override
    public void showError(String msg) {
        //一旦自动登陆出错则跳转到登陆界面
        ToastUtil.showErrorToast(this, msg);
        jump2Login();
    }

    private void jump2Login() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}
