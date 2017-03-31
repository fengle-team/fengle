package com.yunqi.fengle.presenter;

import android.text.TextUtils;

import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.http.BaseHttpRsp;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.model.request.VisitingAddRequest;
import com.yunqi.fengle.model.response.VisitingPlanResponse;
import com.yunqi.fengle.presenter.contract.VisitingAddVisterContract;
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
 * @Description: {@link com.yunqi.fengle.ui.activity.VistingAddVisteActivity}
 */

public class VisitingAddVistePresenter extends RxPresenter<VisitingAddVisterContract.View> implements VisitingAddVisterContract.Presenter {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public VisitingAddVistePresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void addVistePlan(VisitingAddRequest request, final ResponseListener listener) {
        Subscription rxSubscription = mRetrofitHelper.addViste(request)
                .compose(RxUtil.<BaseHttpRsp>rxSchedulerHelper())
                .subscribe(new ExSubscriber<BaseHttpRsp>(mView) {
                    @Override
                    protected void onSuccess(BaseHttpRsp httpRsp) {
                        //成功
                        if (httpRsp.getCode() == 200) {
                            listener.onSuccess();
                        } else {
                            listener.onFaild(new NetResponse(httpRsp.getCode(),httpRsp.getMessage()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        listener.onFaild(new NetResponse(-1, e.getMessage()));
                    }

                });
        addSubscrebe(rxSubscription);
    }


//    @Override
//    public void getVisitingPlanList(final ResponseListener listener) {
//        Subscription rxSubscription = mRetrofitHelper.getVisitePlanList(App.getInstance().getUserInfo().id)
//                .compose(RxUtil.<CommonHttpRsp<List<VisitingPlanResponse>>>rxSchedulerHelper())
//                .compose(RxUtil.<List<VisitingPlanResponse>>handleResult())
//                .subscribe(new ExSubscriber<List<VisitingPlanResponse>>(mView) {
//
//                    @Override
//                    protected void onSuccess(List<VisitingPlanResponse> listCommonHttpRsp) {
//                        listener.onSuccess(new NetResponse(0,"success",listCommonHttpRsp));
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        listener.onFaild(new NetResponse(-1,e.getMessage()));
//                    }
//                });
//        addSubscrebe(rxSubscription);
//    }
}
