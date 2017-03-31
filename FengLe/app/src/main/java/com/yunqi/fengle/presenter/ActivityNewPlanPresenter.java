package com.yunqi.fengle.presenter;

import android.text.TextUtils;

import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.UserBean;
import com.yunqi.fengle.model.http.BaseHttpRsp;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.model.request.ActivityAddPlanRequest;
import com.yunqi.fengle.model.request.TypeRequest;
import com.yunqi.fengle.presenter.contract.ActivityNewPlanContract;
import com.yunqi.fengle.presenter.contract.DailyContract;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.ui.view.UnderLineEditTextEx;
import com.yunqi.fengle.util.RxUtil;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

/**
 * @Author: Huangweicai
 * @date 2017-02-21 09:30
 * @Description: {@link com.yunqi.fengle.ui.activity.ActivityNewPlanActivity}
 */

public class ActivityNewPlanPresenter extends RxPresenter<ActivityNewPlanContract.View> implements ActivityNewPlanContract.Presenter{

    private RetrofitHelper mRetrofitHelper;

    public List<TypeRequest> spinnerAction ;//活动类型
    public List<TypeRequest> spinnerReimburse ;//报账类型

    @Inject
    public ActivityNewPlanPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public String checkEdit(UnderLineEditTextEx textEx) {
        if (textEx == null ||TextUtils.isEmpty(textEx.getText().toString())) {
            return "";
        }
        return textEx.getText().toString();
    }

    @Override
    public void activityNewPlan(ActivityAddPlanRequest request, final ResponseListener listener) {
        Subscription rxSubscription = mRetrofitHelper.activityNewPlan(request)
                .compose(RxUtil.<BaseHttpRsp>rxSchedulerHelper())
                .subscribe(new ExSubscriber<BaseHttpRsp>(mView) {
                    @Override
                    protected void onSuccess(BaseHttpRsp obj) {
                        if (obj.getCode() != 200) {
                            listener.onFaild(new NetResponse(obj.getCode(), obj.getMessage()));
                            return;
                        }
                        listener.onSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFaild(new NetResponse(-1,e.getMessage()));
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void doSubmit(UnderLineEditTextEx... etList) {

    }

    @Override
    public void getData(final ResponseListener listener) {
        mRetrofitHelper.getActionType()
                .compose(RxUtil.<CommonHttpRsp<List<TypeRequest>>>rxSchedulerHelper())
                .compose(RxUtil.<List<TypeRequest>>handleResult())
                .subscribe(new ExSubscriber<List<TypeRequest>>(mView) {
                    @Override
                    protected void onSuccess(List<TypeRequest> response) {
//                        listener.onSuccess(new NetResponse(0,"success",response));
                        onNext(response);
                    }

                    @Override
                    public void onNext(List<TypeRequest> requests) {
                        spinnerAction = requests;
                        getReimburseType(listener);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFaild(new NetResponse(-1,e.getMessage()));
                    }
                });
    }

    public void getReimburseType(final ResponseListener listener) {
        mRetrofitHelper.getReimburseType()
                .compose(RxUtil.<CommonHttpRsp<List<TypeRequest>>>rxSchedulerHelper())
                .compose(RxUtil.<List<TypeRequest>>handleResult())
                .subscribe(new ExSubscriber<List<TypeRequest>>(mView) {
                    @Override
                    protected void onSuccess(List<TypeRequest> response) {
                        spinnerReimburse = response;
                        listener.onSuccess();
                    }


                    @Override
                    public void onError(Throwable e) {
                        listener.onFaild(new NetResponse(-1,e.getMessage()));
                    }
                });
    }

}
