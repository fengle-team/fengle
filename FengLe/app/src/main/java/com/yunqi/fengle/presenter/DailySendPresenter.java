package com.yunqi.fengle.presenter;

import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.http.BaseHttpRsp;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.model.request.DailySendRequest;
import com.yunqi.fengle.model.request.SignAddRequest;
import com.yunqi.fengle.presenter.contract.DailySendContract;
import com.yunqi.fengle.presenter.contract.MoveAttendanceContract;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.LogEx;
import com.yunqi.fengle.util.RxUtil;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import java.util.Map;

import javax.inject.Inject;

import okhttp3.RequestBody;
import rx.Subscription;

/**
 * @Author: Huangweicai
 * @date 2017-02-21 09:30
 * @Description: {@link com.yunqi.fengle.ui.activity.DailySendActivity}
 */

public class DailySendPresenter extends RxPresenter<DailySendContract.View> implements DailySendContract.Presenter{
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public DailySendPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }


    @Override
    public void doSendDaily(DailySendRequest request, final ResponseListener listener) {
        Subscription rxSubscription = mRetrofitHelper.sendDaily(request)
                .compose(RxUtil.<BaseHttpRsp>rxSchedulerHelper())
                .subscribe(new ExSubscriber<BaseHttpRsp>(mView) {
                    @Override
                    protected void onSuccess(BaseHttpRsp httpRsp) {
                        if (httpRsp.getCode() != 200) {
                            listener.onFaild(new NetResponse(httpRsp.getCode(),httpRsp.getMessage()));
                        }else {
                            listener.onSuccess();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFaild(new NetResponse(-1,e.getMessage()));
                    }

                    @Override
                    public void onCompleted() {
                        listener.onSuccess();
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
