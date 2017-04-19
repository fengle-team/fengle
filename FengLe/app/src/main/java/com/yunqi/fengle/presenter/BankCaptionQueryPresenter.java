package com.yunqi.fengle.presenter;


import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.BankCaption;
import com.yunqi.fengle.model.bean.PaymentType;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.presenter.contract.BankCaptionQueryContract;
import com.yunqi.fengle.presenter.contract.PaymentTypeQueryContract;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class BankCaptionQueryPresenter extends RxPresenter<BankCaptionQueryContract.View> implements BankCaptionQueryContract.Presenter {

    public String TAG = getClass().getName();
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public BankCaptionQueryPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void queryBankCaption() {
        Subscription rxSubscription = mRetrofitHelper.queryBankCaption()
                .compose(RxUtil.<CommonHttpRsp<List<BankCaption>>>rxSchedulerHelper())
                .compose(RxUtil.<List<BankCaption>>handleResult())
                .subscribe(new ExSubscriber<List<BankCaption>>(mView) {
                    @Override
                    protected void onSuccess(List<BankCaption> listBankCaption) {
                        mView.showContent(listBankCaption);
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
