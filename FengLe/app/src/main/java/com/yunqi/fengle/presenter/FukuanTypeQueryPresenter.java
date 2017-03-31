package com.yunqi.fengle.presenter;


import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.FukuanType;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.presenter.contract.FukuanTypeQueryContract;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class FukuanTypeQueryPresenter extends RxPresenter<FukuanTypeQueryContract.View> implements FukuanTypeQueryContract.Presenter {

    public String TAG = getClass().getName();
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public FukuanTypeQueryPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void queryHuikuanType() {
        Subscription rxSubscription = mRetrofitHelper.queryFukuanType()
                .compose(RxUtil.<CommonHttpRsp<List<FukuanType>>>rxSchedulerHelper())
                .compose(RxUtil.<List<FukuanType>>handleResult())
                .subscribe(new ExSubscriber<List<FukuanType>>(mView) {
                    @Override
                    protected void onSuccess(List<FukuanType> listFukuanType) {
                        mView.showContent(listFukuanType);
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
