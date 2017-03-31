package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.bean.Customer;
import com.yunqi.fengle.model.bean.Payment;

import java.util.List;

/**
 * @author ghcui
 * @time 2017/2/15
 */
public interface PaymentQueryContract {
    interface View extends BaseView {
        void showContent(List<Payment> listPayment);

        void showMoreContent(List<Payment> listPaymentMore);

        void onSuccess();

    }
    interface Presenter extends BasePresenter<PaymentQueryContract.View> {
        /**
         * 回款查询
         * @param page 页码
         * @return
         */
        void queryPayment(String userid,int status,String startTime,String endTime,int page);
        /**
         * 回款查询
         * @param page 页码
         * @return
         */
        void queryPayment(String userid,int status,int type,int page);
        /**
         * 删除回款
         * @param huikuan_id 回款id
         * @return
         */
        void deletePayment(int huikuan_id);
    }
}
