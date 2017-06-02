package com.yunqi.fengle.presenter;

import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.model.response.ActivityAddResponse;
import com.yunqi.fengle.model.response.ActivitySummaryResponse;
import com.yunqi.fengle.presenter.contract.ActivitySummaryContract;
import com.yunqi.fengle.presenter.contract.ActivitySummaryContract2;
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

public class ActivitySummaryPresenter2 extends RxPresenter<ActivitySummaryContract2.View> implements ActivitySummaryContract2.Presenter {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public ActivitySummaryPresenter2(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }


    @Override
    public void getSummaryData(String start_time,
                               String end_time,
                               String keyword,
                               String status,
                               final int page
                               ) {
        Subscription rxSubscription = mRetrofitHelper.queryActivitieSummary(App.getInstance().getUserInfo().id,start_time,end_time,keyword, status, page + "", 10 + "")
                .compose(RxUtil.<CommonHttpRsp<List<ActivitySummaryResponse>>>rxSchedulerHelper())
                .compose(RxUtil.<List<ActivitySummaryResponse>>handleResult())
                .subscribe(new ExSubscriber<List<ActivitySummaryResponse>>(mView) {
                    @Override
                    protected void onSuccess(List<ActivitySummaryResponse> data) {
                        //加载第一页数据
                        if(page==1){
                            mView.showContent(data);
                        }
                        //加载更多数据
                        else{
                            mView.showMoreContent(data);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
//                        listener.onFaild(new NetResponse(-1,e.getMessage()));
                    }
                });

        addSubscrebe(rxSubscription);
    }


}
