package com.yunqi.fengle.presenter;


import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.Goods;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
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
public class StockQueryPresenter extends RxPresenter<StockQueryContract.View> implements StockQueryContract.Presenter {

    public String TAG = getClass().getName();
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public StockQueryPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }


    @Override
    public void queryStock(String warehouse_code,String area_code,String keywords,final int page) {
        Subscription rxSubscription = mRetrofitHelper.queryStock(warehouse_code,area_code, keywords, page)
                .compose(RxUtil.<CommonHttpRsp<List<Goods>>>rxSchedulerHelper())
                .compose(RxUtil.<List<Goods>>handleResult())
                .subscribe(new ExSubscriber<List<Goods>>(mView) {
                    @Override
                    protected void onSuccess(List<Goods> goodsList) {
                        //加载第一页数据
                        if(page==1){
                            mView.showContent(goodsList);
                        }
                        //加载更多数据
                        else{
                            mView.showMoreContent(goodsList);
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
