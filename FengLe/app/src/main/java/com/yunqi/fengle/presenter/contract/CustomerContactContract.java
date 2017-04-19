package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.bean.CustomerContactDetail;
import com.yunqi.fengle.model.bean.Goods;

import java.util.List;

/**
 * @author ghcui
 * @time 2017/2/15
 */
public interface CustomerContactContract {
    interface View extends BaseView {
        void showContent(CustomerContactDetail customerContactDetail);


    }
    interface Presenter extends BasePresenter<CustomerContactContract.View> {
        /**
         * 客户往来详情查询
         * @param user_code 用户id
         ** @param user_code 客户code
         * @return
         */
        void queryCustomerContact(String user_code,String custom_code);
    }
}
