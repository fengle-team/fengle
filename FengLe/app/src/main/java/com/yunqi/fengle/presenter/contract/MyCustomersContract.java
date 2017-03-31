package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.request.DailySendRequest;
import com.yunqi.fengle.util.map.ResponseListener;

/**
 * @Author: Huangweicai
 * @date 2017-02-21 09:30
 * @Description: {@link com.yunqi.fengle.presenter.DailySendPresenter}
 */

public interface MyCustomersContract {
    interface View extends BaseView {

//        void signSuccess();
    }

    interface Presenter extends BasePresenter<MyCustomersContract.View> {
        void getMyCustomers(ResponseListener listener);
    }
}
