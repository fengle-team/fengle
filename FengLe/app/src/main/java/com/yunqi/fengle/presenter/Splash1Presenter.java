package com.yunqi.fengle.presenter;


import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.SplashBean;
import com.yunqi.fengle.model.http.BmobListResponse;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.presenter.contract.SplashContract;
import com.yunqi.fengle.rx.ErrorAction1;
import com.yunqi.fengle.rx.SuccessAction1;
import com.yunqi.fengle.util.RxUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public class Splash1Presenter extends RxPresenter<SplashContract.View> implements SplashContract.Presenter {

    private RetrofitHelper mRetrofitHelper;
    @Inject
    public Splash1Presenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getSplash() {
        Subscription rxSubscription = mRetrofitHelper.getSplashInfo()
                .compose(RxUtil.<BmobListResponse<List<SplashBean>>>rxSchedulerHelper())
                .subscribe(new SuccessAction1<BmobListResponse<List<SplashBean>>>(mView) {
                    @Override
                    public void nextCall(BmobListResponse<List<SplashBean>> t) {
                        if (t.getResults().size() > 0) {
                            mView.showContent(t.getResults().get(0));
                            startCountDown();
                        }
                    }
                }, new ErrorAction1(mView){
                    @Override
                    public void call(Throwable e) {
                        super.call(e);
                        mView.showError("");
                        mView.jump2Main();
                    }
                });
        addSubscrebe(rxSubscription);
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
