package com.yunqi.fengle.presenter;

import android.text.TextUtils;

import com.tencent.bugly.crashreport.biz.UserInfoBean;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.UserBean;
import com.yunqi.fengle.model.http.BaseHttpRsp;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.presenter.contract.PersonContract;
import com.yunqi.fengle.presenter.contract.StockQueryContract;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.RxUtil;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import javax.inject.Inject;

import rx.Subscription;

/**
 * @Author: Huangweicai
 * @date 2017-02-15 18:01
 * @Description:(这里用一句话描述这个类的作用)
 */
public class PersonPresenter extends RxPresenter<PersonContract.View> implements PersonContract.Presenter{
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public PersonPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void checkFormat(String oldPwd, String newPwd, String newConfirmPwd, final ResponseListener listener) {
        if (TextUtils.isEmpty(oldPwd) || TextUtils.isEmpty(newPwd) || TextUtils.isEmpty(newConfirmPwd)) {
            listener.onFaild(new NetResponse(-1, "密码不能为空."));
            return;
        }
        if (!newPwd.equals(newConfirmPwd)) {
            listener.onFaild(new NetResponse(-1, "新密码不一致."));
            return;
        }
        UserBean user = App.getInstance().getUserInfo();

        if (user == null || TextUtils.isEmpty(user.password)) {
            listener.onFaild(new NetResponse(-1,"getUser failed"));
            return;
        }


        Subscription rxSubscription = mRetrofitHelper.updatePwd(user.account,oldPwd,newPwd)
                .compose(RxUtil.<BaseHttpRsp>rxSchedulerHelper())
                .subscribe(new ExSubscriber<BaseHttpRsp>(mView) {
                    @Override
                    protected void onSuccess(BaseHttpRsp httpRsp) {
                        if (httpRsp.getCode() != 200) {
                            listener.onFaild(new NetResponse(httpRsp.getCode(), httpRsp.getMessage()));
                            return;
                        } else {
                            listener.onSuccess();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFaild(new NetResponse(-1,e.getMessage()));
                    }

                });
        addSubscrebe(rxSubscription);


    }
}
