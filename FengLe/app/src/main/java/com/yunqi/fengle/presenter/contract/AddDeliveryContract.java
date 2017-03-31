package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.request.BillAddRequest;


/**
 * @author ghcui
 * @time 2017/2/15
 */
public interface AddDeliveryContract {
    interface View extends BaseView {
        void onSuccess();
    }
    interface Presenter extends BasePresenter<AddDeliveryContract.View> {
        /**
         * 添加发货申请
         * @param request
         */
        void addDelivery(BillAddRequest request);
    }
}
