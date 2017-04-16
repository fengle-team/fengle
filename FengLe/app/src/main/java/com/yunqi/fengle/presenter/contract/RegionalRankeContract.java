package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.request.ActivityAddPlanRequest;
import com.yunqi.fengle.model.response.RegionalRankingResponse;
import com.yunqi.fengle.model.response.SaleRankingResponse;
import com.yunqi.fengle.ui.view.UnderLineEditTextEx;
import com.yunqi.fengle.util.map.ResponseListener;

import java.util.List;

/**
 * @Author: Huangweicai
 * @date 2017-02-21 09:30
 * @Description:
 */

public interface RegionalRankeContract {
    interface View extends BaseView {
        void showContentRegional(List<RegionalRankingResponse> listRegional);
        void showMoreContentRegional(List<RegionalRankingResponse> listRegional);

        void showContentSale(List<SaleRankingResponse> listInvoiceApply);
        void showMoreContentSale(List<SaleRankingResponse> listInvoiceApplyMore);
    }

    interface Presenter extends BasePresenter<RegionalRankeContract.View> {
        void getRegionalRanke(int page,ResponseListener listener);

        void getSaleRanke(int page,ResponseListener listener);
    }
}
