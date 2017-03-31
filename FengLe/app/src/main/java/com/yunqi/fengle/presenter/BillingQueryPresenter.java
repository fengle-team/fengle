package com.yunqi.fengle.presenter;


import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.BillingApply;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.presenter.contract.BillingRequestContract;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class BillingQueryPresenter extends RxPresenter<BillingRequestContract.View> implements BillingRequestContract.Presenter {

    public String TAG = getClass().getName();
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public BillingQueryPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }
    @Override
    public void queryBillingApply(String userid, String keyword,int status, String startTime, String endTime, final int page) {
        Subscription rxSubscription = mRetrofitHelper.queryBillingApply(userid,keyword, status, startTime, endTime, page)
                .compose(RxUtil.<CommonHttpRsp<List<BillingApply>>>rxSchedulerHelper())
                .compose(RxUtil.<List<BillingApply>>handleResult())
                .subscribe(new ExSubscriber<List<BillingApply>>(mView) {
                    @Override
                    protected void onSuccess(List<BillingApply> listBillingApply) {
                        //加载第一页数据
                        if(page==1){
                            mView.showContent(listBillingApply);
                        }
                        //加载更多数据
                        else{
                            mView.showMoreContent(listBillingApply);
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
