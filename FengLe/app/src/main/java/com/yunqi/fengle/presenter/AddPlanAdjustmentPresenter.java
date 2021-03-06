package com.yunqi.fengle.presenter;


import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.http.BaseHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.model.request.BillAddRequest;
import com.yunqi.fengle.model.request.PlanAdjustmentAddRequest;
import com.yunqi.fengle.model.request.TransferAddRequest;
import com.yunqi.fengle.presenter.contract.AddPlanAdjustmentContract;
import com.yunqi.fengle.rx.BaseSubscriber;
import com.yunqi.fengle.util.RxUtil;

import javax.inject.Inject;

import rx.Subscription;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class AddPlanAdjustmentPresenter extends RxPresenter<AddPlanAdjustmentContract.View> implements AddPlanAdjustmentContract.Presenter {

    public String TAG = getClass().getName();
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public AddPlanAdjustmentPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }


    @Override
    public void addPlanAdjustment(PlanAdjustmentAddRequest request) {
        Subscription rxSubscription = mRetrofitHelper.addPlanAdjustment(request)
                .compose(RxUtil.<BaseHttpRsp>rxSchedulerHelper())
                .subscribe(new BaseSubscriber(mView) {
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
