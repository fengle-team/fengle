package com.yunqi.fengle.presenter;


import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.InvoiceApply;
import com.yunqi.fengle.model.bean.TransferApply;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.presenter.contract.DeliveryRequestContract;
import com.yunqi.fengle.presenter.contract.TransferRequestContract;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class TransferQueryPresenter extends RxPresenter<TransferRequestContract.View> implements TransferRequestContract.Presenter {

    public String TAG = getClass().getName();
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public TransferQueryPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }
    @Override
    public void queryTransferApply(String userid, String keyword,int status,String startTime, String endTime, final int page) {
        Subscription rxSubscription = mRetrofitHelper.queryTransferApply(userid, keyword,status, startTime, endTime, page)
                .compose(RxUtil.<CommonHttpRsp<List<TransferApply>>>rxSchedulerHelper())
                .compose(RxUtil.<List<TransferApply>>handleResult())
                .subscribe(new ExSubscriber<List<TransferApply>>(mView) {
                    @Override
                    protected void onSuccess(List<TransferApply> listTransferApply) {
                        //加载第一页数据
                        if(page==1){
                            mView.showContent(listTransferApply);
                        }
                        //加载更多数据
                        else{
                            mView.showMoreContent(listTransferApply);
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
