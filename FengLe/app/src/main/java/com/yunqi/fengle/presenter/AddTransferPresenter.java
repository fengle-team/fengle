package com.yunqi.fengle.presenter;


import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.http.BaseHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.model.request.TransferAddRequest;
import com.yunqi.fengle.presenter.contract.AddTransferContract;
import com.yunqi.fengle.rx.BaseSubscriber;
import com.yunqi.fengle.util.RxUtil;

import javax.inject.Inject;

import rx.Subscription;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class AddTransferPresenter extends RxPresenter<AddTransferContract.View> implements AddTransferContract.Presenter {

    public String TAG = getClass().getName();
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public AddTransferPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }


    @Override
    public void addTransfer(TransferAddRequest request) {
        Subscription rxSubscription = mRetrofitHelper.addTransfer(request)
                .compose(RxUtil.<BaseHttpRsp>rxSchedulerHelper())
                .subscribe(new BaseSubscriber(mView) {
                    @Override
                    protected void onSuccess() {
                        mView.onSuccess();
                    }

                    @Override
                    protected void onFailure(int errorCode, String msg) {
                        mView.showError(msg);
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
