package com.yunqi.fengle.presenter;


import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.Area;
import com.yunqi.fengle.model.bean.Customer;
import com.yunqi.fengle.model.bean.Warehouse;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.presenter.contract.AreaQueryContract;
import com.yunqi.fengle.presenter.contract.CustomerQueryContract;
import com.yunqi.fengle.presenter.contract.WarehouseQueryContract;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class WarehouseQueryPresenter extends RxPresenter<WarehouseQueryContract.View> implements WarehouseQueryContract.Presenter {

    public String TAG = getClass().getName();
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public WarehouseQueryPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }






    @Override
    public void queryWarehouse() {
        Subscription rxSubscription = mRetrofitHelper.queryWarehouse()
                .compose(RxUtil.<CommonHttpRsp<List<Warehouse>>>rxSchedulerHelper())
                .compose(RxUtil.<List<Warehouse>>handleResult())
                .subscribe(new ExSubscriber<List<Warehouse>>(mView) {
                    @Override
                    protected void onSuccess(List<Warehouse> listWarehouse) {
                        mView.showContent(listWarehouse);
                    }
                });
        addSubscrebe(rxSubscription);
    }


}
