package com.yunqi.fengle.presenter;

import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.http.BaseHttpRsp;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.model.request.VisitingAddRequest;
import com.yunqi.fengle.model.response.CustomerWholeResponse;
import com.yunqi.fengle.model.response.DailyResponse;
import com.yunqi.fengle.presenter.contract.CustomerWholeContract;
import com.yunqi.fengle.presenter.contract.VisitingAddVisterContract;
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

public class CustomerWholePresenter extends RxPresenter<CustomerWholeContract.View> implements CustomerWholeContract.Presenter {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public CustomerWholePresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }


    @Override
    public void getData(String userId, String custom_code, final ResponseListener listener) {
        Subscription rxSubscription = mRetrofitHelper.getCustomerWhole(userId,custom_code)
                .compose(RxUtil.<CommonHttpRsp<CustomerWholeResponse>>rxSchedulerHelper())
                .subscribe(new ExSubscriber<CommonHttpRsp<CustomerWholeResponse>>(mView) {

                    @Override
                    protected void onSuccess(CommonHttpRsp<CustomerWholeResponse> listCommonHttpRsp) {
                        listener.onSuccess(new NetResponse(0,"success",listCommonHttpRsp.getData()));
                    }


                    @Override
                    public void onError(Throwable e) {
                        listener.onFaild(new NetResponse(-1,e.getMessage()));
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
