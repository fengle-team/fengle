package com.yunqi.fengle.presenter;


import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.Area;
import com.yunqi.fengle.model.bean.CustomerAnalysis;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.presenter.contract.AreaQueryContract;
import com.yunqi.fengle.presenter.contract.CustomerAnalysisContract;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class CustomerAnalysisPresenter extends RxPresenter<CustomerAnalysisContract.View> implements CustomerAnalysisContract.Presenter {

    public String TAG = getClass().getName();
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public CustomerAnalysisPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }
    @Override
    public void queryCustomerAnalysis(String user_code,int type,int order_type,int order_status) {
        Subscription rxSubscription = mRetrofitHelper.queryCustomerAnalysis(user_code,type,order_type,order_status)
                .compose(RxUtil.<CommonHttpRsp<List<CustomerAnalysis>>>rxSchedulerHelper())
                .compose(RxUtil.<List<CustomerAnalysis>>handleResult())
                .subscribe(new ExSubscriber<List<CustomerAnalysis>>(mView) {
                    @Override
                    protected void onSuccess(List<CustomerAnalysis> listCustomerAnalysis) {
                        mView.showContent(listCustomerAnalysis);
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
