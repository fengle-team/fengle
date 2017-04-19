package com.yunqi.fengle.presenter;

import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.http.BaseHttpRsp;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.model.response.ActivityAddResponse;
import com.yunqi.fengle.presenter.contract.ActivityPlanContract;
import com.yunqi.fengle.presenter.contract.ActivityPlanUpdateContract;
import com.yunqi.fengle.rx.BaseSubscriber;
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
 * @Description: {@link com.yunqi.fengle.ui.activity.ActivityPlanActivity}
 */

public class ActivityPlanUpdatePresenter extends RxPresenter<ActivityPlanUpdateContract.View> implements ActivityPlanUpdateContract.Presenter{

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public ActivityPlanUpdatePresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }


    @Override
    public void updatePlanUpdateStatus(String userid, String order_code, String status, final ResponseListener listener) {
        Subscription rxSubscription = mRetrofitHelper.updatePlanUpdateStatus(userid,order_code,status)
                .compose(RxUtil.<BaseHttpRsp>rxSchedulerHelper())
                .subscribe(new BaseSubscriber(mView) {
                    @Override
                    protected void onSuccess() {
                        listener.onSuccess();
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        listener.onFaild(new NetResponse(errorCode,msg));
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
