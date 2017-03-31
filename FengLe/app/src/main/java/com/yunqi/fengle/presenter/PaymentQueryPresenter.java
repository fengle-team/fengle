package com.yunqi.fengle.presenter;


import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.Payment;
import com.yunqi.fengle.model.http.BaseHttpRsp;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.presenter.contract.PaymentQueryContract;
import com.yunqi.fengle.rx.BaseSubscriber;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class PaymentQueryPresenter extends RxPresenter<PaymentQueryContract.View> implements PaymentQueryContract.Presenter {

    public String TAG = getClass().getName();
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public PaymentQueryPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }
    @Override
    public void queryPayment(String userid, int status, String startTime, String endTime, final int page) {
        Subscription rxSubscription = mRetrofitHelper.queryPayment(userid, status, startTime, endTime, page)
                .compose(RxUtil.<CommonHttpRsp<List<Payment>>>rxSchedulerHelper())
                .compose(RxUtil.<List<Payment>>handleResult())
                .subscribe(new ExSubscriber<List<Payment>>(mView) {
                    @Override
                    protected void onSuccess(List<Payment> paymentList) {
                        //加载第一页数据
                        if(page==1){
                            mView.showContent(paymentList);
                        }
                        //加载更多数据
                        else{
                            mView.showMoreContent(paymentList);
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void queryPayment(String userid, int status, int type, final int page) {
        Subscription rxSubscription = mRetrofitHelper.queryPayment(userid, status, type, page)
                .compose(RxUtil.<CommonHttpRsp<List<Payment>>>rxSchedulerHelper())
                .compose(RxUtil.<List<Payment>>handleResult())
                .subscribe(new ExSubscriber<List<Payment>>(mView) {
                    @Override
                    protected void onSuccess(List<Payment> paymentList) {
                        //加载第一页数据
                        if(page==1){
                            mView.showContent(paymentList);
                        }
                        //加载更多数据
                        else{
                            mView.showMoreContent(paymentList);
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void deletePayment(int huikuan_id) {
        Subscription rxSubscription = mRetrofitHelper.deletePayment(huikuan_id)
                .compose(RxUtil.<BaseHttpRsp>rxSchedulerHelper())
                .subscribe(new BaseSubscriber(mView,3) {
                    @Override
                    protected void onSuccess() {
                        mView.onSuccess();
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        mView.showError(msg);
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
