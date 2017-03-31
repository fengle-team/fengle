package com.yunqi.fengle.presenter;

import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.ADInfo;
import com.yunqi.fengle.model.http.BaseHttpRsp;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.model.request.DailySendRequest;
import com.yunqi.fengle.model.response.CustomersResponse;
import com.yunqi.fengle.presenter.contract.DailySendContract;
import com.yunqi.fengle.presenter.contract.MyCustomersContract;
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
 * @Description: {@link com.yunqi.fengle.ui.activity.DailySendActivity}
 */

public class MyCustomersPresenter extends RxPresenter<MyCustomersContract.View> implements MyCustomersContract.Presenter{
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public MyCustomersPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }


    @Override
    public void getMyCustomers(final ResponseListener listener) {
        Subscription rxSubscription = mRetrofitHelper.getCustomers(App.getInstance().getUserInfo().user_code)
                .compose(RxUtil.<CommonHttpRsp<List<CustomersResponse>>>rxSchedulerHelper())
                .compose(RxUtil.<List<CustomersResponse>>handleResult())
                .subscribe(new ExSubscriber<List<CustomersResponse>>(mView) {
                    @Override
                    protected void onSuccess(List<CustomersResponse> list) {
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
