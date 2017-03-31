package com.yunqi.fengle.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.di.component.ActivityComponent;
import com.yunqi.fengle.di.component.DaggerActivityComponent;
import com.yunqi.fengle.di.module.ActivityModule;
import com.yunqi.fengle.model.bean.UserBean;
import com.yunqi.fengle.ui.activity.LoginActivity;
import com.yunqi.fengle.ui.activity.SplashActivity;
import com.yunqi.fengle.ui.view.progress.BaseViewStateListener;
import com.yunqi.fengle.ui.view.progress.ProgressLayout;
import com.yunqi.fengle.util.ToastUtil;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by codeest on 2016/8/2.
 * MVP activity基类
 */
public abstract class BaseActivity<T extends BasePresenter> extends SupportActivity implements BaseView, View.OnClickListener, BaseViewStateListener {
    protected String TAG = getClass().getName();
    @Inject
    protected T mPresenter;
    protected Activity mContext;
    private Unbinder mUnBinder;

    protected final int MODE_VIEW_01 = 0x01;
    protected final int MODE_VIEW_02 = 0x02;

    protected Toolbar toolbar;
    private TextView tvTitle;
    private ImageView ivRight;
    protected TextView tvRight;
    private RelativeLayout rlMain, rlRight;
    protected ProgressLayout progresser;
    protected SweetAlertDialog loadingDialog;
    protected UserBean userBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(!checkLogined()){
//            return;
//        }
        if (MODE_VIEW_01 == getViewModel()) {
            setContentView(getLayout());
        } else if (MODE_VIEW_02 == MODE_VIEW_02) {
            setContentView(R.layout.activity_base);
            initToolbar();
            rlMain = (RelativeLayout) findViewById(R.id.rlMain);
            progresser = (ProgressLayout) findViewById(R.id.progress);
            progresser.setBaseViewStateListener(this);
            setMainView(getLayout());
        }
        init();
    }


    private boolean checkLogined() {
        if (TAG.equals(LoginActivity.class.getName()) || TAG.equals(SplashActivity.class.getName())) {
            return true;
        }
        userBean = App.getInstance().getUserInfo();
        if (userBean == null) {
            App.getInstance().killAllActivities();
            ToastUtil.showNoticeToast(this, "系统内存不足，重新登录！");
            startActivity(new Intent(this,LoginActivity.class));
            return false;
        }
        return true;
    }

    @Override
    public void cancelLoading() {
        if (loadingDialog != null) {
            loadingDialog.cancel();
        }
    }

    @Override
    public void showLoading() {
        this.showLoading("加载中...");
    }

    @Override
    public void showError(String msg) {
        ToastUtil.showErrorToast(this, msg);
    }

    protected void showLoading(String msg) {
        loadingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(msg);
        loadingDialog.show();
        loadingDialog.setCancelable(false);
    }

    protected int getViewModel() {
        return MODE_VIEW_01;
    }


    /**
     * {@link #MODE_VIEW_02} start
     */
    public void showTitleRightText() {
        if (tvRight.getVisibility() != View.VISIBLE | ivRight.getVisibility() != View.VISIBLE) {
//            tvRight.setVisibility(View.VISIBLE);
            rlRight.setVisibility(View.VISIBLE);
        }
    }

    public void hideTitleRightText() {
        if (tvRight.getVisibility() == View.VISIBLE || ivRight.getVisibility() == View.VISIBLE) {
//            tvRight.setVisibility(View.INVISIBLE);
//            ivRight.setVisibility(View.INVISIBLE);
            rlRight.setVisibility(View.INVISIBLE);
        }
    }

    protected void setMainView(int layoutId) {
        View mainView = getLayoutInflater().inflate(layoutId, null);
        setMainView(mainView);
    }

    protected void setMainView(View view) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        rlMain.addView(view, params);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle = (TextView) toolbar.findViewById(R.id.tvToolbarTitle);
        tvRight = (TextView) toolbar.findViewById(R.id.tvToolbarRight);
        ivRight = (ImageView) toolbar.findViewById(R.id.ivToolbarRight);
        rlRight = (RelativeLayout) toolbar.findViewById(R.id.rlToolbarRight);
        setSupportActionBar(toolbar);
    }

    /**
     * 显示标题栏左侧返回按钮
     */
    protected void showTitleBack() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onTitleBackClikced();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 点击返回按钮
     */
    protected void onTitleBackClikced() {
        this.finish();
    }

    /**
     * 设置标题栏文字
     *
     * @param title
     */
    protected void setTitleText(String title) {
        tvTitle.setText(title);
        showToolbar();
    }

    private void showToolbar() {
        if (toolbar.getVisibility() != View.VISIBLE) {
            toolbar.setVisibility(View.VISIBLE);
        }
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    /**
     * 设置标题栏右侧文字并添加监听
     *
     * @param title
     */
    protected void setTitleRight(CharSequence title) {
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(title);
//        tvRight.setOnClickListener(this);
        rlRight.setOnClickListener(this);
        setTitleRightVisible(true);
    }

    protected void setTitleRightVisible(boolean flag) {
        if (flag) {
//            tvRight.setVisibility(View.VISIBLE);
            rlRight.setVisibility(View.VISIBLE);
        } else {
//            tvRight.setVisibility(View.GONE);
            rlRight.setVisibility(View.GONE);
        }
    }

    protected void setTitleRightImage(int title) {
//        tvRight.setBackgroundResource(title);
//        ivRight.setBackgroundColor(title);
        ivRight.setVisibility(View.VISIBLE);
        rlRight.setOnClickListener(this);
        setTitleRightVisible(true);
    }

    protected void onTitleRightClicked(View v) {
    }

    /**
     * {@link #MODE_VIEW_02} end
     */


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvToolbarRight:
                rlRight.performClick();
                break;
            case R.id.rlToolbarRight:
//                RxHelper.click(rlRight, new RxHelper.RxClickCallback() {
//                    @Override
//                    public void onCall() {
                onTitleRightClicked(rlRight);
//                    }
//                });
                break;
            default:
                break;
        }
    }

    private void init() {
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        initInject();
        if (mPresenter != null)
            mPresenter.attachView(this);
        App.getInstance().addActivity(this);
        initEventAndData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 设置ToolBar（包含左边返回按钮、中间标题、右边按钮）
     *
     * @param toolbar
     * @param title
     */
    protected void setToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle("");//使用处于中间位置自定义Title
        TextView titleCenter = (TextView) toolbar.findViewById(R.id.title_center);
        titleCenter.setText(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressedSupport();
            }
        });
    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
        mUnBinder.unbind();
        App.getInstance().removeActivity(this);
    }

    @Override
    public void useNightMode(boolean isNight) {
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        }
        recreate();
    }

    @Override
    public void showLoading(int type) {
        this.showLoading();
    }

    @Override
    public void cancelLoading(int type) {
        this.cancelLoading();
    }

    protected abstract void initInject();

    protected abstract int getLayout();

    protected abstract void initEventAndData();
}