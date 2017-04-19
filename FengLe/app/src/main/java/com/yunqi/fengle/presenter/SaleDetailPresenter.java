package com.yunqi.fengle.presenter;


import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.Goods;
import com.yunqi.fengle.model.bean.SaleInfo;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.presenter.contract.SaleDetailContract;
import com.yunqi.fengle.presenter.contract.StockQueryContract;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class SaleDetailPresenter extends RxPresenter<SaleDetailContract.View> implements SaleDetailContract.Presenter {

    public String TAG = getClass().getName();
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public SaleDetailPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void querySales(String startTime, String endTime, String ccuscode, String cpersoncode, String ccdcode) {
        Subscription rxSubscription = mRetrofitHelper.querySales(startTime, endTime, ccuscode,cpersoncode,ccdcode)
                .compose(RxUtil.<CommonHttpRsp<List<SaleInfo>>>rxSchedulerHelper())
                .compose(RxUtil.<List<SaleInfo>>handleResult())
                .subscribe(new ExSubscriber<List<SaleInfo>>(mView) {
                    @Override
                    protected void onSuccess(List<SaleInfo> listSaleInfo) {
                        mView.showContent(listSaleInfo);
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
