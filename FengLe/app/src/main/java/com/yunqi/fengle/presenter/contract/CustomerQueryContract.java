package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.bean.Customer;

import java.util.List;

/**
 * @author ghcui
 * @time 2017/2/15
 */
public interface CustomerQueryContract {
    interface View extends BaseView {
        void showContent(List<Customer> listCustomer);

        void showMoreContent(List<Customer> listCustomerMore);

    }
    interface Presenter extends BasePresenter<CustomerQueryContract.View> {
        /**
         * 客户查询
         * @param keyword 客户名或者编号
         * @param userid 用户id
         * @param page 页码
         * @return
         */
        void queryCustomer(String keyword,String userid, int page);
    }
}
