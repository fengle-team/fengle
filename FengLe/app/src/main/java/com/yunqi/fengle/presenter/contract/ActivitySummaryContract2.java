package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.bean.InvoiceApply;
import com.yunqi.fengle.model.response.ActivityAddResponse;
import com.yunqi.fengle.model.response.ActivitySummaryResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import java.util.List;

/**
 * @Author: Huangweicai
 * @date 2017-02-21 09:30
 * @Description:
 */

public interface ActivitySummaryContract2 {
    interface View extends BaseView {
        void showContent(List<ActivitySummaryResponse> listInvoiceApply);

        void showMoreContent(List<ActivitySummaryResponse> listInvoiceApplyMore);
    }

    interface Presenter extends BasePresenter<ActivitySummaryContract2.View> {
        void getSummaryData(String start_time,
                            String end_time,
                            String keyword,
                            String status,
                            int page);
//        void getData(ResponseListener listener);

    }
}
