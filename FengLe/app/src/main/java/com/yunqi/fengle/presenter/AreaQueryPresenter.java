package com.yunqi.fengle.presenter;


import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.Area;
import com.yunqi.fengle.model.bean.Customer;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.presenter.contract.AreaQueryContract;
import com.yunqi.fengle.presenter.contract.CustomerQueryContract;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class AreaQueryPresenter extends RxPresenter<AreaQueryContract.View> implements AreaQueryContract.Presenter {

    public String TAG = getClass().getName();
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public AreaQueryPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }






    @Override
    public void queryArea() {
        Subscription rxSubscription = mRetrofitHelper.queryArea()
                .compose(RxUtil.<CommonHttpRsp<List<Area>>>rxSchedulerHelper())
                .compose(RxUtil.<List<Area>>handleResult())
                .subscribe(new ExSubscriber<List<Area>>(mView) {
                    @Override
                    protected void onSuccess(List<Area> listArea) {
                        mView.showContent(listArea);
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
