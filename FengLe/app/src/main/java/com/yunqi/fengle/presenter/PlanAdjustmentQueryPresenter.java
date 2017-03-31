package com.yunqi.fengle.presenter;


import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.PlanAdjustmentApply;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.presenter.contract.DeliveryRequestContract;
import com.yunqi.fengle.presenter.contract.PlanAdjustmentRequestContract;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class PlanAdjustmentQueryPresenter extends RxPresenter<PlanAdjustmentRequestContract.View> implements PlanAdjustmentRequestContract.Presenter {

    public String TAG = getClass().getName();
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public PlanAdjustmentQueryPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }
    @Override
    public void queryPlanAdjustmentApply(String userid,String keyword, int status, String startTime, String endTime, final int page) {
        Subscription rxSubscription = mRetrofitHelper.queryPlanAdjustmentApply(userid,keyword, status, startTime, endTime, page)
                .compose(RxUtil.<CommonHttpRsp<List<PlanAdjustmentApply>>>rxSchedulerHelper())
                .compose(RxUtil.<List<PlanAdjustmentApply>>handleResult())
                .subscribe(new ExSubscriber<List<PlanAdjustmentApply>>(mView) {
                    @Override
                    protected void onSuccess(List<PlanAdjustmentApply> listPlanAdjustmentApply) {
                        //加载第一页数据
                        if(page==1){
                            mView.showContent(listPlanAdjustmentApply);
                        }
                        //加载更多数据
                        else{
                            mView.showMoreContent(listPlanAdjustmentApply);
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
