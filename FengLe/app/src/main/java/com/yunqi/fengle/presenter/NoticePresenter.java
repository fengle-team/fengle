package com.yunqi.fengle.presenter;

import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.http.BaseHttpRsp;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.model.response.ActivityAddResponse;
import com.yunqi.fengle.model.response.NoticeResponse;
import com.yunqi.fengle.presenter.contract.ActivityPlanContract;
import com.yunqi.fengle.presenter.contract.NoticeContract;
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
 * @Description: {@link com.yunqi.fengle.ui.activity.NoticeActivity}
 */

public class NoticePresenter extends RxPresenter<NoticeContract.View> implements NoticeContract.Presenter{

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public NoticePresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }


    @Override
    public void getNotice(final ResponseListener listener) {
        Subscription rxSubscription = mRetrofitHelper.getNotice(App.getInstance().getUserInfo().id)
                .compose(RxUtil.<CommonHttpRsp<List<NoticeResponse>>>rxSchedulerHelper())
                .subscribe(new ExSubscriber<CommonHttpRsp<List<NoticeResponse>>>(mView) {

                    @Override
                    protected void onSuccess(CommonHttpRsp<List<NoticeResponse>> listCommonHttpRsp) {
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
