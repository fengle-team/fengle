package com.yunqi.fengle.presenter;


import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.Area;
import com.yunqi.fengle.model.bean.PaymentType;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.presenter.contract.AreaQueryContract;
import com.yunqi.fengle.presenter.contract.PaymentTypeQueryContract;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class PaymentTypeQueryPresenter extends RxPresenter<PaymentTypeQueryContract.View> implements PaymentTypeQueryContract.Presenter {

    public String TAG = getClass().getName();
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public PaymentTypeQueryPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void queryPaymentType() {
        Subscription rxSubscription = mRetrofitHelper.queryPaymentType()
                .compose(RxUtil.<CommonHttpRsp<List<PaymentType>>>rxSchedulerHelper())
                .compose(RxUtil.<List<PaymentType>>handleResult())
                .subscribe(new ExSubscriber<List<PaymentType>>(mView) {
                    @Override
                    protected void onSuccess(List<PaymentType> listPaymentType) {
                        mView.showContent(listPaymentType);
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
