package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.bean.CustomerContactDetail;
import com.yunqi.fengle.model.bean.SaleInfo;

import java.util.List;

/**
 * @author ghcui
 * @time 2017/2/15
 */
public interface SaleDetailContract {
    interface View extends BaseView {
        void showContent(List<SaleInfo> listSaleInfo);

        void showMoreContent(List<SaleInfo> listMoreSaleInfo);
    }
    interface Presenter extends BasePresenter<SaleDetailContract.View> {
        /**
         * 货物销售列表查询
         * @param goods_id 货物id
         * @return
         */
        void querySales(String startTime, String endTime, String goods_id , int page);
    }
}
