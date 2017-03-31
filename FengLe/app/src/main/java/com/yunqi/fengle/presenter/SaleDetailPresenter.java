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
    public void querySales(String startTime, String endTime, String goods_id , final int page) {
        Subscription rxSubscription = mRetrofitHelper.querySales(startTime,endTime,goods_id,page)
                .compose(RxUtil.<CommonHttpRsp<List<SaleInfo>>>rxSchedulerHelper())
                .compose(RxUtil.<List<SaleInfo>>handleResult())
                .subscribe(new ExSubscriber<List<SaleInfo>>(mView) {
                    @Override
                    protected void onSuccess(List<SaleInfo> listSaleInfo) {
                        //加载第一页数据
                        if(page==1){
                            mView.showContent(listSaleInfo);
                        }
                        //加载更多数据
                        else{
                            mView.showMoreContent(listSaleInfo);
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
