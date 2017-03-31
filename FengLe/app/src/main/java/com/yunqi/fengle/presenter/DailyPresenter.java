package com.yunqi.fengle.presenter;

import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.model.response.DailyResponse;
import com.yunqi.fengle.model.response.MessageResponse;
import com.yunqi.fengle.presenter.contract.DailyContract;
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
 * @Description: {@link com.yunqi.fengle.ui.activity.DailyActivity}
 */

public class DailyPresenter extends RxPresenter<DailyContract.View> implements DailyContract.Presenter{

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public DailyPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getDaily(String startTime, String endTime, final ResponseListener listener) {
        Subscription rxSubscription = mRetrofitHelper.getDaily(App.getInstance().getUserInfo().id,startTime,endTime)
                .compose(RxUtil.<CommonHttpRsp<List<DailyResponse>>>rxSchedulerHelper())
                .subscribe(new ExSubscriber<CommonHttpRsp<List<DailyResponse>>>(mView) {

                    @Override
                    protected void onSuccess(CommonHttpRsp<List<DailyResponse>> listCommonHttpRsp) {
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
