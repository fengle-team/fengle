package com.yunqi.fengle.presenter;

import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.InvoiceApply;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.model.response.RegionalRankingResponse;
import com.yunqi.fengle.presenter.contract.AchievementManagerContract;
import com.yunqi.fengle.presenter.contract.LoginContract;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.RxUtil;
import com.yunqi.fengle.util.map.ResponseListener;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

/**
 * @Author: Huangweicai
 * @date 2017-02-21 09:30
 * @Description: {@link com.yunqi.fengle.ui.activity.AchievementManagerActivity}
 */

public class AchievementManagerPresenter extends RxPresenter<AchievementManagerContract.View> implements AchievementManagerContract.Presenter{
    private RetrofitHelper mRetrofitHelper;

    private int regionalPage = 1;//分页起始
    private int regionalSize = 10;//分页大小

    private int salePage = 1;
    private int saleSize = 10;

    @Inject
    public AchievementManagerPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }


    @Override
    public void getRegionalRanke(ResponseListener listener) {
        Subscription rxSubscription = mRetrofitHelper.queryRegional(regionalPage)
                .compose(RxUtil.<CommonHttpRsp<List<RegionalRankingResponse>>>rxSchedulerHelper())
                .compose(RxUtil.<List<RegionalRankingResponse>>handleResult())
                .subscribe(new ExSubscriber<List<RegionalRankingResponse>>(mView) {
                    @Override
                    protected void onSuccess(List<RegionalRankingResponse> listInvoiceApply) {
                        //加载第一页数据
                        if(regionalPage==1){
                            mView.showContentRegional(listInvoiceApply);
                        }
                        //加载更多数据
                        else{
                            mView.showContentRegional(listInvoiceApply);
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getSaleRanke(ResponseListener listener) {

    }
}
