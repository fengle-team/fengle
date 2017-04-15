package com.yunqi.fengle.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.request.DailySendRequest;
import com.yunqi.fengle.presenter.DailySendPresenter;
import com.yunqi.fengle.presenter.contract.DailySendContract;
import com.yunqi.fengle.util.LogEx;
import com.yunqi.fengle.util.ToastUtil;
import com.yunqi.fengle.util.map.LocationModel;
import com.yunqi.fengle.util.map.MapLocationMgr;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import butterknife.BindView;

/**
 * @Author: Huangweicai
 * @date 2017-03-09 14:00
 * @Description: 发日报
 *               {@link DailyActivity}
 */

public class DailySendActivity extends BaseActivity<DailySendPresenter> implements DailySendContract.View{

    @BindView(R.id.etContent)
    EditText etContent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("发日志");
        setTitleRight("提交");
    }

    @Override
    protected int getViewModel() {
        return MODE_VIEW_02;
    }

    @Override
    protected void onTitleRightClicked(View v) {
        if (TextUtils.isEmpty(etContent.getText().toString())) {
            ToastUtil.toast(this,"内容不能为空!");
            return;
        }


        final DailySendRequest request = new DailySendRequest();
        request.setUserid(App.getInstance().getUserInfo().id);
        request.setDaily(etContent.getText().toString());
        request.setAddress("address不能为空?");
        request.setLat("lat不能为空？");
        request.setLng("lng不能为空？");

        progresser.showProgress();
        mPresenter.doSendDaily(request, new ResponseListener() {
            @Override
            public void onSuccess() {
                progresser.showContent();
                ToastUtil.toast(mContext,"发送日报成功.");
                setResult(Activity.RESULT_OK);
                DailySendActivity.this.finish();
            }

            @Override
            public void onFaild(NetResponse response) {
                progresser.showContent();
                ToastUtil.toast(mContext,response.getMsg());
            }
        });
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_daily_send;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
