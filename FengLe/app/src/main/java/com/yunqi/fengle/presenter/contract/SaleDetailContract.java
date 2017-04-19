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

    }
    interface Presenter extends BasePresenter<SaleDetailContract.View> {
        /**
         * 查询货物销售明细接口
         * @param startTime 开始时间
         * @param endTime  结束时间
         * @param ccuscode  客户编码
         * @param cpersoncode  业务员编码
         * @param ccdcode  客户地区编码
         * @return
         */
        void querySales(String startTime, String endTime, String ccuscode, String cpersoncode, String ccdcode);
    }
}
