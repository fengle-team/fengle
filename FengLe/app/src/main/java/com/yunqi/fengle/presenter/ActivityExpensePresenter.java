package com.yunqi.fengle.presenter;

import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.SpinnerBean;
import com.yunqi.fengle.model.http.BaseHttpRsp;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.model.request.ActivityExpenseRequest;
import com.yunqi.fengle.model.request.TypeRequest;
import com.yunqi.fengle.model.response.DailyResponse;
import com.yunqi.fengle.presenter.contract.ActivityExpenseContract;
import com.yunqi.fengle.presenter.contract.DailyContract;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.LogEx;
import com.yunqi.fengle.util.RxUtil;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

/**
 * @Author: Huangweicai
 * @date 2017-02-21 09:30
 * @Description: {@link com.yunqi.fengle.ui.activity.DailyActivity}
 */

public class ActivityExpensePresenter extends RxPresenter<ActivityExpenseContract.View> implements ActivityExpenseContract.Presenter{

    private RetrofitHelper mRetrofitHelper;

    public List<TypeRequest> spinnerAction ;//活动类型
    public List<TypeRequest> spinnerInvoice ;//发票类型
    public List<TypeRequest> spinnerReimburse ;//报账类型

    @Inject
    public ActivityExpensePresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void addExpense(ActivityExpenseRequest request, final ResponseListener listener) {
        Subscription rxSubscription = mRetrofitHelper.addReimburse(request)
                .compose(RxUtil.<BaseHttpRsp>rxSchedulerHelper())
                .subscribe(new ExSubscriber<BaseHttpRsp>(mView) {
                    @Override
                    protected void onSuccess(BaseHttpRsp bean) {
                        LogEx.i("success");
                        if (bean.getCode() != 200) {
                            listener.onFaild(new NetResponse(bean.getCode(),bean.getMessage()));
                        } else {
                            listener.onSuccess();
                        }
                    }


                    @Override
                    public void onError(Throwable e) {
                        listener.onFaild(new NetResponse(-1,e.getMessage()));
                    }
                });
        addSubscrebe(rxSubscription);
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
                        onNext(response);
                    }

                    @Override
                    public void onNext(List<TypeRequest> requests) {
                        spinnerReimburse = requests;
                        getInvoiceType(listener);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFaild(new NetResponse(-1,e.getMessage()));
                    }
                });
    }

    public void getInvoiceType(final ResponseListener listener) {
        mRetrofitHelper.getInvoiceType()
                .compose(RxUtil.<CommonHttpRsp<List<TypeRequest>>>rxSchedulerHelper())
                .compose(RxUtil.<List<TypeRequest>>handleResult())
                .subscribe(new ExSubscriber<List<TypeRequest>>(mView) {
                    @Override
                    protected void onSuccess(List<TypeRequest> response) {
                        spinnerInvoice = response;
                        listener.onSuccess();
//                        listener.onSuccess(new NetResponse(0,"success",response));
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFaild(new NetResponse(-1,e.getMessage()));
                    }
                });
    }



}
