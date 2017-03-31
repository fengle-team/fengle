package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.request.TransferAddRequest;


/**
 * @author ghcui
 * @time 2017/2/15
 */
public interface AddTransferContract {
    interface View extends BaseView {
        void onSuccess();
    }
    interface Presenter extends BasePresenter<AddTransferContract.View> {
        /**
         * 添加调货申请
         * @param request
         */
        void addTransfer(TransferAddRequest request);
    }
}
