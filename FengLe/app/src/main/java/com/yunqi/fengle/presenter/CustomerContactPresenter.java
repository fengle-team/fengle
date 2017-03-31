package com.yunqi.fengle.presenter;


import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.CustomerContactDetail;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.presenter.contract.CustomerContactContract;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.RxUtil;


import javax.inject.Inject;

import rx.Subscription;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class CustomerContactPresenter extends RxPresenter<CustomerContactContract.View> implements CustomerContactContract.Presenter {

    public String TAG = getClass().getName();
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public CustomerContactPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }


    @Override
    public void queryCustomerContact(String id) {
        Subscription rxSubscription = mRetrofitHelper.queryCustomerContact(id)
                .compose(RxUtil.<CommonHttpRsp<CustomerContactDetail>>rxSchedulerHelper())
                .compose(RxUtil.<CustomerContactDetail>handleResult())
                .subscribe(new ExSubscriber<CustomerContactDetail>(mView) {
                    @Override
                    protected void onSuccess(CustomerContactDetail customerContactDetail) {
                        mView.showContent(customerContactDetail);
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
