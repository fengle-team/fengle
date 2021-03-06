package com.yunqi.fengle.presenter;


import android.text.TextUtils;

import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.BillingApply;
import com.yunqi.fengle.model.http.BaseHttpRsp;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.model.request.BillUpdateRequest;
import com.yunqi.fengle.presenter.contract.BillingDetailsContract;
import com.yunqi.fengle.rx.BaseSubscriber;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.RxUtil;

import javax.inject.Inject;

import rx.Subscription;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class BillingDetailsPresenter extends RxPresenter<BillingDetailsContract.View> implements BillingDetailsContract.Presenter {

    public String TAG = getClass().getName();
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public BillingDetailsPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }




    @Override
    public void getBillingDetails(int id) {
        Subscription rxSubscription = mRetrofitHelper.getBillingDetails(id)
                .compose(RxUtil.<CommonHttpRsp<BillingApply>>rxSchedulerHelper())
                .compose(RxUtil.<BillingApply>handleResult())
                .subscribe(new ExSubscriber<BillingApply>(mView) {
                    @Override
                    protected void onSuccess(BillingApply billingApply) {
                        mView.showContent(billingApply);
                    }
                });
        addSubscrebe(rxSubscription);
    }



    @Override
    public void updateStatus(int id, int status) {
        Subscription rxSubscription = mRetrofitHelper.updateBillingStatus(id,status)
                .compose(RxUtil.<BaseHttpRsp>rxSchedulerHelper())
                .subscribe(new ExSubscriber<BaseHttpRsp>(mView) {
                    @Override
                    protected void onSuccess(BaseHttpRsp httpRsp) {
                        //成功
                        if(httpRsp.getCode()==200){
                            mView.onSuccess(0);
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

    @Override
    public void updateStatus(BillUpdateRequest request) {
        Subscription rxSubscription = mRetrofitHelper.updateBillingStatus(request)
                .compose(RxUtil.<BaseHttpRsp>rxSchedulerHelper())
                .subscribe(new BaseSubscriber(mView) {
                    @Override
                    protected void onSuccess() {
                        mView.onSuccess(0);
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        mView.showError(msg);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void delete(int id) {
        Subscription rxSubscription = mRetrofitHelper.deleteBilling(id)
                .compose(RxUtil.<BaseHttpRsp>rxSchedulerHelper())
                .subscribe(new ExSubscriber<BaseHttpRsp>(mView) {
                    @Override
                    protected void onSuccess(BaseHttpRsp httpRsp) {
                        //成功
                        if(httpRsp.getCode()==200){
                            mView.onSuccess(1);
                        }
                        else{
                            String errorMsg=httpRsp.getMessage();
                            if(TextUtils.isEmpty(errorMsg)){
                                errorMsg="删除失败";
                            }
                            mView.showError(errorMsg);
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void delSelectedGoods(int id) {
        Subscription rxSubscription = mRetrofitHelper.delBillingSelectedGoods(id)
                .compose(RxUtil.<BaseHttpRsp>rxSchedulerHelper())
                .subscribe(new BaseSubscriber(mView) {
                    @Override
                    protected void onSuccess() {
                        mView.onSuccess(2);
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        mView.showError(msg);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void approval(String userid, String order_code, final int status) {
        Subscription rxSubscription = mRetrofitHelper.approvalBillingBill(userid,order_code,status)
                .compose(RxUtil.<BaseHttpRsp>rxSchedulerHelper())
                .subscribe(new BaseSubscriber(mView) {
                    @Override
                    protected void onSuccess() {
                        mView.onSuccess(status);
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        mView.showError(msg);
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
