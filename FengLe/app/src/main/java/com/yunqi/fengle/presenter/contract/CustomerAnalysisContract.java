package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.bean.Area;
import com.yunqi.fengle.model.bean.CustomerAnalysis;

import java.util.List;

/**
 * @author ghcui
 * @time 2017/2/15
 */
public interface CustomerAnalysisContract {
    interface View extends BaseView {
        void showContent(List<CustomerAnalysis> listCustomerAnalysis);

    }
    interface Presenter extends BasePresenter<CustomerAnalysisContract.View> {
        /**
         * 客户分析查询
         * @return
         */
        void queryCustomerAnalysis(String user_code,int type,int order_type,int order_status);
    }
}
