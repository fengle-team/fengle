package com.yunqi.fengle.presenter;

import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.UserBean;
import com.yunqi.fengle.model.http.BaseHttpRsp;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.model.request.SignAddRequest;
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
 * @Description: {@link com.yunqi.fengle.ui.activity.MoveAttendanceActivity}
 */

public class MoveAttendancePresenter extends RxPresenter<MoveAttendanceContract.View> implements MoveAttendanceContract.Presenter{
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public MoveAttendancePresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }


    @Override
    public void sign(SignAddRequest request, final ResponseListener listener) {
        Subscription rxSubscription = mRetrofitHelper.doSign(request)
                .compose(RxUtil.<BaseHttpRsp>rxSchedulerHelper())
                .subscribe(new ExSubscriber<BaseHttpRsp>(mView) {
                    @Override
                    protected void onSuccess(BaseHttpRsp msg) {
                        if (msg.getCode() != 200) {
                            listener.onFaild(new NetResponse(msg.getCode(),msg.getMessage()));
                            return;
                        } else {
                            listener.onSuccess();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        listener.onFaild(new NetResponse(-1,e.getMessage()));
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void test(Map<String, RequestBody> params, ResponseListener listener) {
        Subscription rxSubscription = mRetrofitHelper.doUploader(params)
                .compose(RxUtil.<CommonHttpRsp<Object>>rxSchedulerHelper())
                .compose(RxUtil.<Object>handleResult())
                .subscribe(new ExSubscriber<Object>(mView) {
                    @Override
                    protected void onSuccess(Object o) {
                        LogEx.i("success" + o.toString());
                    }

                });
        addSubscrebe(rxSubscription);
    }


}
