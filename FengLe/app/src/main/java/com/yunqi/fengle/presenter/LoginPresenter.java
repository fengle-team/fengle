package com.yunqi.fengle.presenter;


import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.UserBean;
import com.yunqi.fengle.model.db.RealmHelper;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.presenter.contract.LoginContract;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.RxUtil;



import javax.inject.Inject;

import rx.Subscription;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {

    private RetrofitHelper mRetrofitHelper;
    private RealmHelper realmHelper;

    @Inject
    public LoginPresenter(RetrofitHelper retrofitHelper, RealmHelper realmHelper) {
        this.mRetrofitHelper = retrofitHelper;
        this.realmHelper = realmHelper;
    }

    @Override
    public void doLogin(final String username, final String password) {
        Subscription rxSubscription = mRetrofitHelper.doLogin(username, password)
                .compose(RxUtil.<CommonHttpRsp<UserBean>>rxSchedulerHelper())
                .compose(RxUtil.<UserBean>handleResult())
                .subscribe(new ExSubscriber<UserBean>(mView) {
                    @Override
                    protected void onSuccess(UserBean userBean) {
                        App.getInstance().saveUserInfo(userBean);
                        realmHelper.addUser(userBean);
                        mView.jump2Main(userBean);
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
