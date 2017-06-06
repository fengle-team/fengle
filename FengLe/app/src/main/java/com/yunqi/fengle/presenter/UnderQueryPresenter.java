package com.yunqi.fengle.presenter;


import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.Area;
import com.yunqi.fengle.model.bean.UserBean;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.presenter.contract.AreaQueryContract;
import com.yunqi.fengle.presenter.contract.UnderQueryContract;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class UnderQueryPresenter extends RxPresenter<UnderQueryContract.View> implements UnderQueryContract.Presenter {

    public String TAG = getClass().getName();
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public UnderQueryPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void queryUnder(String userid) {
        Subscription rxSubscription = mRetrofitHelper.queryUnder(userid)
                .compose(RxUtil.<CommonHttpRsp<List<UserBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<UserBean>>handleResult())
                .subscribe(new ExSubscriber<List<UserBean>>(mView) {
                    @Override
                    protected void onSuccess(List<UserBean> listUser) {
                        mView.showContent(listUser);
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
