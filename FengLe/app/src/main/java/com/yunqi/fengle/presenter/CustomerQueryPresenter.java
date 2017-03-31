package com.yunqi.fengle.presenter;


import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.Customer;
import com.yunqi.fengle.model.bean.Goods;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.presenter.contract.CustomerQueryContract;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class CustomerQueryPresenter extends RxPresenter<CustomerQueryContract.View> implements CustomerQueryContract.Presenter {

    public String TAG = getClass().getName();
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public CustomerQueryPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }




    @Override
    public void queryCustomer(String customerName,String user_code, final int page) {
        Subscription rxSubscription = mRetrofitHelper.queryCustomer(customerName,user_code,page)
                .compose(RxUtil.<CommonHttpRsp<List<Customer>>>rxSchedulerHelper())
                .compose(RxUtil.<List<Customer>>handleResult())
                .subscribe(new ExSubscriber<List<Customer>>(mView) {
                    @Override
                    protected void onSuccess(List<Customer> customerList) {
                        //加载第一页数据
                        if(page==1){
                            mView.showContent(customerList);
                        }
                        //加载更多数据
                        else{
                            mView.showMoreContent(customerList);
                        }
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
