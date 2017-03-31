package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.bean.Payment;



/**
 * @author ghcui
 * @time 2017/2/15
 */
public interface PaymentDeclarationDetailsContract {
    interface View extends BaseView {
        void showContent(Payment payment);
        void onSuccess();
    }
    interface Presenter extends BasePresenter<PaymentDeclarationDetailsContract.View> {
        /**
         * 获取回款申报详情
         * @param id
         */
        void getPaymentDeclarationDetails(int id);
        /**
         * 更新状态
         * @param id
         * @param status
         */
        void updateStatus(int id,int status);
    }
}
