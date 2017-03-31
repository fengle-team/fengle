package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.bean.Customer;
import com.yunqi.fengle.model.bean.InvoiceApply;

import java.util.List;

/**
 * @author ghcui
 * @time 2017/2/15
 */
public interface DeliveryRequestContract {
    interface View extends BaseView {
        void showContent(List<InvoiceApply> listInvoiceApply);

        void showMoreContent(List<InvoiceApply> listInvoiceApplyMore);

    }
    interface Presenter extends BasePresenter<DeliveryRequestContract.View> {
        /**
         * 发货单查询
         * @param userid 用户id
         * @param status 状态
         * @param startTime 开始时间
         * @param endTime 结束时间
         * @param page 页码
         * @return
         */
        void queryInvoiceApply(String userid,String keyword,  int status, String startTime, String endTime, final int page);
    }
}
