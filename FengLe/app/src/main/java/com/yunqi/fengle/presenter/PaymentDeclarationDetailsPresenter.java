package com.yunqi.fengle.presenter;



import android.text.TextUtils;

import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.Payment;
import com.yunqi.fengle.model.http.BaseHttpRsp;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.presenter.contract.PaymentDeclarationDetailsContract;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.RxUtil;


import javax.inject.Inject;

import rx.Subscription;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class PaymentDeclarationDetailsPresenter extends RxPresenter<PaymentDeclarationDetailsContract.View> implements PaymentDeclarationDetailsContract.Presenter {

    public String TAG = getClass().getName();
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public PaymentDeclarationDetailsPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }




    @Override
    public void getPaymentDeclarationDetails(int id) {
        Subscription rxSubscription = mRetrofitHelper.getPaymentDeclarationDetails(id)
                .compose(RxUtil.<CommonHttpRsp<Payment[]>>rxSchedulerHelper())
                .compose(RxUtil.<Payment[]>handleResult())
                .subscribe(new ExSubscriber<Payment[]>(mView) {
                    @Override
                    protected void onSuccess(Payment[] payment) {
                        mView.showContent(payment[0]);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void updateStatus(int id, int status) {
        Subscription rxSubscription = mRetrofitHelper.updatePaymentStatus(id,status)
                .compose(RxUtil.<BaseHttpRsp>rxSchedulerHelper())
                .subscribe(new ExSubscriber<BaseHttpRsp>(mView) {
                    @Override
                    protected void onSuccess(BaseHttpRsp httpRsp) {
                        //成功
                        if(httpRsp.getCode()==200){
                            mView.onSuccess();
                        }
                        else{
                            String errorMsg=httpRsp.getMessage();
                            if(TextUtils.isEmpty(errorMsg)){
                                errorMsg="更新失败";
                            }
                            mView.showError(errorMsg);
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
