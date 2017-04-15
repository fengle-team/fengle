package com.yunqi.fengle.presenter;

import android.text.TextUtils;

import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.http.BaseHttpRsp;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.model.request.ActivityAddPlanRequest;
import com.yunqi.fengle.model.request.TypeRequest;
import com.yunqi.fengle.model.response.RegionalRankingResponse;
import com.yunqi.fengle.model.response.SaleRankingResponse;
import com.yunqi.fengle.presenter.contract.ActivityNewPlanContract;
import com.yunqi.fengle.presenter.contract.RegionalRankeContract;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.ui.view.UnderLineEditTextEx;
import com.yunqi.fengle.util.RxUtil;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

/**
 * @Author: Huangweicai
 * @date 2017-02-21 09:30
 * @Description: {@link com.yunqi.fengle.ui.activity.ActivityNewPlanActivity}
 */

public class RegionalRankePresenter extends RxPresenter<RegionalRankeContract.View> implements RegionalRankeContract.Presenter{

    private RetrofitHelper mRetrofitHelper;


//    private int regionalPage = 1;//分页起始
//    private int regionalSize = 10;//分页大小
//
//    private int salePage = 1;
//    private int saleSize = 10;

    @Inject
    public RegionalRankePresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void getRegionalRanke(final int page, ResponseListener listener) {
        Subscription rxSubscription = mRetrofitHelper.queryRegional(page)
                .compose(RxUtil.<CommonHttpRsp<List<RegionalRankingResponse>>>rxSchedulerHelper())
                .compose(RxUtil.<List<RegionalRankingResponse>>handleResult())
                .subscribe(new ExSubscriber<List<RegionalRankingResponse>>(mView) {
                    @Override
                    protected void onSuccess(List<RegionalRankingResponse> listInvoiceApply) {
                        //加载第一页数据
                        if(page==1){
                            mView.showContentRegional(listInvoiceApply);
                        }
                        //加载更多数据
                        else{
                            mView.showMoreContentRegional(listInvoiceApply);
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getSaleRanke(final int page, ResponseListener listener) {
        Subscription rxSubscription = mRetrofitHelper.querySaleRanke(page)
                .compose(RxUtil.<CommonHttpRsp<List<SaleRankingResponse>>>rxSchedulerHelper())
                .compose(RxUtil.<List<SaleRankingResponse>>handleResult())
                .subscribe(new ExSubscriber<List<SaleRankingResponse>>(mView) {
                    @Override
                    protected void onSuccess(List<SaleRankingResponse> listInvoiceApply) {
                        //加载第一页数据
                        if(page==1){
                            mView.showContentSale(listInvoiceApply);
                        }
                        //加载更多数据
                        else{
                            mView.showMoreContentSale(listInvoiceApply);
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
