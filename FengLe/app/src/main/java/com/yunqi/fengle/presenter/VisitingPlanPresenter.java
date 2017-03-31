package com.yunqi.fengle.presenter;

import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.model.response.ActivityAddResponse;
import com.yunqi.fengle.model.response.NoticeResponse;
import com.yunqi.fengle.model.response.VisitingPlanResponse;
import com.yunqi.fengle.presenter.contract.VisitingPlanContract;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.RxUtil;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

/**
 * @Author: Huangweicai
 * @date 2017-02-21 09:30
 * @Description: {@link com.yunqi.fengle.ui.activity.VisitingPlanActivity}
 */

public class VisitingPlanPresenter extends RxPresenter<VisitingPlanContract.View> implements VisitingPlanContract.Presenter{

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public VisitingPlanPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }


    @Override
    public void getVisitingPlanList(final ResponseListener listener) {
        Subscription rxSubscription = mRetrofitHelper.getVisitePlanList(App.getInstance().getUserInfo().id)
                .compose(RxUtil.<CommonHttpRsp<List<VisitingPlanResponse>>>rxSchedulerHelper())
                .compose(RxUtil.<List<VisitingPlanResponse>>handleResult())
                .subscribe(new ExSubscriber<List<VisitingPlanResponse>>(mView) {

                    @Override
                    protected void onSuccess(List<VisitingPlanResponse> listCommonHttpRsp) {
                        listener.onSuccess(new NetResponse(0,"success",listCommonHttpRsp));
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFaild(new NetResponse(-1,e.getMessage()));
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
