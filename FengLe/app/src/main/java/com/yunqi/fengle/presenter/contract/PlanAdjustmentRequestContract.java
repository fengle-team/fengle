package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.bean.PlanAdjustmentApply;

import java.util.List;

/**
 * @author ghcui
 * @time 2017/2/15
 */
public interface PlanAdjustmentRequestContract {
    interface View extends BaseView {
        void showContent(List<PlanAdjustmentApply> listPlanAdjustmentApply);

        void showMoreContent(List<PlanAdjustmentApply> listPlanAdjustmentApplyMore);

    }
    interface Presenter extends BasePresenter<PlanAdjustmentRequestContract.View> {
        /**
         * 发货单查询
         * @param userid 用户id
         * @param status 状态
         * @param startTime 开始时间
         * @param endTime 结束时间
         * @param page 页码
         * @return
         */
        void queryPlanAdjustmentApply(String userid,String keyword, int status, String startTime, String endTime, final int page);
    }
}
