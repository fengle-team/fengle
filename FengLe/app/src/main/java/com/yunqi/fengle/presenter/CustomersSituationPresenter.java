package com.yunqi.fengle.presenter;

import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.model.response.CustomersResponse;
import com.yunqi.fengle.model.response.CustomersSituationResponse;
import com.yunqi.fengle.presenter.contract.CustomersSituationContract;
import com.yunqi.fengle.presenter.contract.VisitingAddCustomerContract;
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
 * @Description: {@link com.yunqi.fengle.ui.activity.VisitingAddCustomerActivity}
 */

public class CustomersSituationPresenter extends RxPresenter<CustomersSituationContract.View> implements CustomersSituationContract.Presenter{

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public CustomersSituationPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }


    @Override
    public void getMaintain(final ResponseListener listener) {

        Subscription rxSubscription = mRetrofitHelper.getMainTain(App.getInstance().getUserInfo().id)
                .compose(RxUtil.<CommonHttpRsp<List<CustomersSituationResponse>>>rxSchedulerHelper())
                .compose(RxUtil.<List<CustomersSituationResponse>>handleResult())
                .subscribe(new ExSubscriber<List<CustomersSituationResponse>>(mView) {
                    @Override
                    protected void onSuccess(List<CustomersSituationResponse> list) {
                        listener.onSuccess(new NetResponse(0,"success",list));
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFaild(new NetResponse(-1,e.getMessage()));
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
