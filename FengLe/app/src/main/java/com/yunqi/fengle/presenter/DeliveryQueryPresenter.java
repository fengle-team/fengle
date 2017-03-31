package com.yunqi.fengle.presenter;


import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.InvoiceApply;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.presenter.contract.DeliveryRequestContract;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class DeliveryQueryPresenter extends RxPresenter<DeliveryRequestContract.View> implements DeliveryRequestContract.Presenter {

    public String TAG = getClass().getName();
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public DeliveryQueryPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }
    @Override
    public void queryInvoiceApply(String userid, String keyword, int status, String startTime, String endTime, final int page) {
        Subscription rxSubscription = mRetrofitHelper.queryInvoiceApply(userid,keyword, status, startTime, endTime, page)
                .compose(RxUtil.<CommonHttpRsp<List<InvoiceApply>>>rxSchedulerHelper())
                .compose(RxUtil.<List<InvoiceApply>>handleResult())
                .subscribe(new ExSubscriber<List<InvoiceApply>>(mView) {
                    @Override
                    protected void onSuccess(List<InvoiceApply> listInvoiceApply) {
                        //加载第一页数据
                        if(page==1){
                            mView.showContent(listInvoiceApply);
                        }
                        //加载更多数据
                        else{
                            mView.showMoreContent(listInvoiceApply);
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
