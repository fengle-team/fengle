package com.yunqi.fengle.presenter;


import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.GoodsSaleDetail;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.presenter.contract.GoodsSaleDetailContract;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class GoodsSaleDetailPresenter extends RxPresenter<GoodsSaleDetailContract.View> implements GoodsSaleDetailContract.Presenter {

    public String TAG = getClass().getName();
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public GoodsSaleDetailPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }
    @Override
    public void getGoodsSaleDetail(String sale_id) {
        Subscription rxSubscription = mRetrofitHelper.queryGoodsSaleDetail(sale_id)
                .compose(RxUtil.<CommonHttpRsp<GoodsSaleDetail>>rxSchedulerHelper())
                .compose(RxUtil.<GoodsSaleDetail>handleResult())
                .subscribe(new ExSubscriber<GoodsSaleDetail>(mView) {
                    @Override
                    protected void onSuccess(GoodsSaleDetail goodsSaleDetail) {
                        mView.showContent(goodsSaleDetail);
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
