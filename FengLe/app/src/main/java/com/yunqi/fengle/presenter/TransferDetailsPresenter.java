package com.yunqi.fengle.presenter;


import android.text.TextUtils;

import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.TransferApply;
import com.yunqi.fengle.model.http.BaseHttpRsp;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.model.request.BillUpdateRequest;
import com.yunqi.fengle.presenter.contract.TransferDetailsContract;
import com.yunqi.fengle.presenter.contract.TransferDetailsContract;
import com.yunqi.fengle.rx.BaseSubscriber;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.RxUtil;

import javax.inject.Inject;

import rx.Subscription;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class TransferDetailsPresenter extends RxPresenter<TransferDetailsContract.View> implements TransferDetailsContract.Presenter {

    public String TAG = getClass().getName();
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public TransferDetailsPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }




    @Override
    public void getTransferDetails(int id) {
        Subscription rxSubscription = mRetrofitHelper.getTransferDetails(id)
                .compose(RxUtil.<CommonHttpRsp<TransferApply>>rxSchedulerHelper())
                .compose(RxUtil.<TransferApply>handleResult())
                .subscribe(new ExSubscriber<TransferApply>(mView) {
                    @Override
                    protected void onSuccess(TransferApply transferApply) {
                        mView.showContent(transferApply);
                    }
                });
        addSubscrebe(rxSubscription);
    }



    @Override
    public void updateStatus(int id, int status) {
        Subscription rxSubscription = mRetrofitHelper.updateTransferStatus(id,status)
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
    public void updateStatus(BillUpdateRequest request) {
        Subscription rxSubscription = mRetrofitHelper.updateTransferStatus(request)
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
        Subscription rxSubscription = mRetrofitHelper.deleteTransfer(id)
                .compose(RxUtil.<BaseHttpRsp>rxSchedulerHelper())
                .subscribe(new BaseSubscriber(mView) {
                    @Override
                    protected void onSuccess() {
                        mView.onSuccess(1);
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        mView.showError(msg);
                    }
                });
        addSubscrebe(rxSubscription);
    }

    @Override
    public void delSelectedGoods(int id) {
        Subscription rxSubscription = mRetrofitHelper.delTransferSelectedGoods(id)
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
        Subscription rxSubscription = mRetrofitHelper.approvalTransferBill(userid,order_code,status)
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
