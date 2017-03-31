package com.yunqi.fengle.presenter;


import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.presenter.contract.SplashContract;
import com.yunqi.fengle.util.RxUtil;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public class Splash2Presenter extends RxPresenter<SplashContract.View> implements SplashContract.Presenter {

    private RetrofitHelper mRetrofitHelper;
    @Inject
    public Splash2Presenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getSplash() {
        startCountDown();
    }

    private void startCountDown() {
        Subscription rxSubscription = Observable.timer(3000, TimeUnit.MILLISECONDS)
                .compose(RxUtil.<Long>rxSchedulerHelper())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mView.jump2Main();
                    }
                });
        addSubscrebe(rxSubscription);
    }


}
