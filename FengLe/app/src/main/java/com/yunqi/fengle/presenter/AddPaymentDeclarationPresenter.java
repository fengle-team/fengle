package com.yunqi.fengle.presenter;


import android.text.TextUtils;

import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.InvoiceApply;
import com.yunqi.fengle.model.bean.Payment;
import com.yunqi.fengle.model.http.BaseHttpRsp;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.model.request.PaymentAddRequest;
import com.yunqi.fengle.presenter.contract.AddPaymentDeclarationContract;
import com.yunqi.fengle.presenter.contract.PaymentQueryContract;
import com.yunqi.fengle.rx.BaseSubscriber;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.RxUtil;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class AddPaymentDeclarationPresenter extends RxPresenter<AddPaymentDeclarationContract.View> implements AddPaymentDeclarationContract.Presenter {

    public String TAG = getClass().getName();
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public AddPaymentDeclarationPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }


    @Override
    public void addPayment(final PaymentAddRequest request,String  filePath) {

        Subscription rxSubscription = mRetrofitHelper.upload(new File(filePath))
        .flatMap(new Func1<CommonHttpRsp<String[]>, Observable<BaseHttpRsp>>() {
                    @Override
                    public Observable<BaseHttpRsp> call(CommonHttpRsp<String[]> httpRsp) {
                        request.images=httpRsp.getData()[0];
                        return mRetrofitHelper.addPayment(request);
                    }
                })
                .compose(RxUtil.<BaseHttpRsp>rxSchedulerHelper())
                .subscribe(new BaseSubscriber(mView) {
                    @Override
                    protected void onSuccess() {
                        mView.onSuccess();
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {

                    }
                });
        addSubscrebe(rxSubscription);
    }
}
