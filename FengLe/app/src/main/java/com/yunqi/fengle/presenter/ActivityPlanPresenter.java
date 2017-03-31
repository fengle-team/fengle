package com.yunqi.fengle.presenter;

import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.UserBean;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.model.response.ActivityAddResponse;
import com.yunqi.fengle.presenter.contract.ActivityPlanContract;
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

public class ActivityPlanPresenter extends RxPresenter<ActivityPlanContract.View> implements ActivityPlanContract.Presenter{

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public ActivityPlanPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }


    @Override
    public void showData(final ResponseListener listener) {

        Subscription rxSubscription = mRetrofitHelper.queryActivities(App.getInstance().getUserInfo().id)
                .compose(RxUtil.<CommonHttpRsp<List<ActivityAddResponse>>>rxSchedulerHelper())
                .compose(RxUtil.<List<ActivityAddResponse>>handleResult())
                .subscribe(new ExSubscriber<List<ActivityAddResponse>>(mView) {
                    @Override
                    protected void onSuccess(List<ActivityAddResponse> userBean) {
                        listener.onSuccess(new NetResponse(0,"success",userBean));
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        listener.onFaild(new NetResponse(-1,e.getMessage()));
                    }
                });

        addSubscrebe(rxSubscription);
    }
}
