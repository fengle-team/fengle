package com.yunqi.fengle.ui.activity.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.UserBean;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.RxUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscription;

/**
 * @Author: Huangweicai
 * @date 2017-02-26 13:24
 * @Description:(这里用一句话描述这个类的作用)
 */

public class TestImageUploadActivity extends BaseActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        findViewById(R.id.btUpload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploader();
            }


        });
    }

    @Inject
    RetrofitHelper mRetrofitHelper;

    private void uploader() {
//        Map<String, RequestBody> params = new HashMap<>();
//        File file = new File("/storage/emulated/0/Download/test_img.jpg");
//        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
//        params.put("file\"; filename=\""+file.getName()+"\"", fileBody);
//        new RetrofitHelper(this).doUploader(params);
//
//        new RetrofitHelper(this).doUploader(params)
//                .compose(RxUtil.<CommonHttpRsp<Object>>rxSchedulerHelper())
//                .compose(RxUtil.<Object>handleResult())
//                .subscribe(new ExSubscriber<Object>(mView) {
//                    @Override
//                    protected void onSuccess(UserBean userBean) {
//                        mView.jump2Main();
//                    }
//                });
//        addSubscrebe(rxSubscription);
//
//        Subscription rxSubscription = mRetrofitHelper.doLogin(username, password)
//                .compose(RxUtil.<CommonHttpRsp<UserBean>>rxSchedulerHelper())
//                .compose(RxUtil.<UserBean>handleResult())
//                .subscribe(new ExSubscriber<UserBean>(mView) {
//                    @Override
//                    protected void onSuccess(UserBean userBean) {
//                        mView.jump2Main();
//                    }
//                });
//        addSubscrebe(rxSubscription);
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_test_img_uploader;
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
