package com.yunqi.fengle.presenter;


import android.text.TextUtils;

import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.http.BaseHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.model.request.BillAddRequest;
import com.yunqi.fengle.presenter.contract.AddReturnContract;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.RxUtil;

import javax.inject.Inject;

import rx.Subscription;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class AddReturnPresenter extends RxPresenter<AddReturnContract.View> implements AddReturnContract.Presenter {

    public String TAG = getClass().getName();
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public AddReturnPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }


    @Override
    public void addReturn(BillAddRequest request) {
        Subscription rxSubscription = mRetrofitHelper.addReturn(request)
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
                                errorMsg="添加失败";
                            }
                            mView.showError(errorMsg);
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
