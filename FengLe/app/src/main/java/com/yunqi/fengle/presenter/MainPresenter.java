package com.yunqi.fengle.presenter;


import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.ADInfo;
import com.yunqi.fengle.model.bean.Module;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.presenter.contract.MainContract;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public MainPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }


    @Override
    public void getAdInfos() {
        Subscription rxSubscription = mRetrofitHelper.queryAdInfo()
                .compose(RxUtil.<CommonHttpRsp<List<ADInfo>>>rxSchedulerHelper())
                .compose(RxUtil.<List<ADInfo>>handleResult())
                .subscribe(new ExSubscriber<List<ADInfo>>(mView) {
                    @Override
                    protected void onSuccess(List<ADInfo> adInfoList) {
                        mView.showAdInfos(adInfoList);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void authModule(String userid,final List<Module> xmlModules) {
        mView.showContent(xmlModules);
    }
}
