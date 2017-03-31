package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.request.PaymentAddRequest;


/**
 * @author ghcui
 * @time 2017/2/15
 */
public interface AddPaymentDeclarationContract {
    interface View extends BaseView {
        void onSuccess();
    }
    interface Presenter extends BasePresenter<AddPaymentDeclarationContract.View> {
        /**
         * 添加回款申报
         * @param request
         */
        void addPayment(PaymentAddRequest request,String filePath);
    }
}
