package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.bean.PlanAdjustmentApply;
import com.yunqi.fengle.model.request.BillUpdateRequest;


/**
 * @author ghcui
 * @time 2017/2/15
 */
public interface PlanAdjustmentDetailsContract {
    interface View extends BaseView {
        void showContent(PlanAdjustmentApply planAdjustmentApply);

        /**
         *
         * @param opraterType 操作类型 0:更新 1：删除单据 2：删除所选货物 3：审核通过 4：审核驳回
         */
        void onSuccess(int opraterType);
    }
    interface Presenter extends BasePresenter<PlanAdjustmentDetailsContract.View> {
        /**
         * 获取计划调剂详情
         * @param id
         */
        void getPlanAdjustmentDetails(int id);
        /**
         * 更新状态
         * @param id
         * @param status
         */
        void updateStatus(int id, int status);
        /**
         * 更新状态
         * @param request
         */
        void updateStatus(BillUpdateRequest request);

        /**
         * 删除单据
         * @param id
         */
        void delete(int id);

        /**
         * 删除已选的货物
         * @param goodsId
         */
        void delSelectedGoods(int goodsId);

        /**
         * 审批
         * @param userid
         * @param order_code
         * @param status
         */
        void approval(String userid, String order_code, int status);
    }
}
