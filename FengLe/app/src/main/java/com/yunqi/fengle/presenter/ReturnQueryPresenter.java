package com.yunqi.fengle.presenter;


import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.ReturnApply;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.presenter.contract.DeliveryRequestContract;
import com.yunqi.fengle.presenter.contract.ReturnRequestContract;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class ReturnQueryPresenter extends RxPresenter<ReturnRequestContract.View> implements ReturnRequestContract.Presenter {

    public String TAG = getClass().getName();
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public ReturnQueryPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }
    @Override
    public void queryReturnApply(String userid,String keyword, int status, String startTime, String endTime, final int page) {
        Subscription rxSubscription = mRetrofitHelper.queryReturnApply(userid, keyword,status, startTime, endTime, page)
                .compose(RxUtil.<CommonHttpRsp<List<ReturnApply>>>rxSchedulerHelper())
                .compose(RxUtil.<List<ReturnApply>>handleResult())
                .subscribe(new ExSubscriber<List<ReturnApply>>(mView) {
                    @Override
                    protected void onSuccess(List<ReturnApply> listReturnApply) {
                        //加载第一页数据
                        if(page==1){
                            mView.showContent(listReturnApply);
                        }
                        //加载更多数据
                        else{
                            mView.showMoreContent(listReturnApply);
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
