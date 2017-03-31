package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.request.BillAddRequest;
import com.yunqi.fengle.model.request.TransferAddRequest;


/**
 * @author ghcui
 * @time 2017/2/15
 */
public interface AddPlanAdjustmentContract {
    interface View extends BaseView {
        void onSuccess();
    }
    interface Presenter extends BasePresenter<AddPlanAdjustmentContract.View> {
        /**
         * 添加计划调剂申请
         * @param request
         */
        void addPlanAdjustment(TransferAddRequest request);
    }
}
