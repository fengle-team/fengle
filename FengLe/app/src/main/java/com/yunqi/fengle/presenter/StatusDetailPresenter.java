package com.yunqi.fengle.presenter;


import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.BillingApply;
import com.yunqi.fengle.model.bean.StatusInfo;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.presenter.contract.BillingRequestContract;
import com.yunqi.fengle.presenter.contract.StatusDetailContract;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class StatusDetailPresenter extends RxPresenter<StatusDetailContract.View> implements StatusDetailContract.Presenter {

    public String TAG = getClass().getName();
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public StatusDetailPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void queryStatusDetail(String order_code) {
        Subscription rxSubscription = mRetrofitHelper.queryStatusDetail(order_code)
                .compose(RxUtil.<CommonHttpRsp<List<StatusInfo>>>rxSchedulerHelper())
                .compose(RxUtil.<List<StatusInfo>>handleResult())
                .subscribe(new ExSubscriber<List<StatusInfo>>(mView) {
                    @Override
                    protected void onSuccess(List<StatusInfo> listStatus) {
                        mView.showContent(listStatus);
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
