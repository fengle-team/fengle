package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.bean.BillingApply;

import java.util.List;

/**
 * @author ghcui
 * @time 2017/2/15
 */
public interface BillingRequestContract {
    interface View extends BaseView {
        void showContent(List<BillingApply> listBillingApply);

        void showMoreContent(List<BillingApply> listBillingApplyMore);

    }
    interface Presenter extends BasePresenter<BillingRequestContract.View> {
        /**
         * 开票查询
         * @param userid 用户id
         * @param status 状态
         * @param startTime 开始时间
         * @param endTime 结束时间
         * @param page 页码
         * @return
         */
        void queryBillingApply(String userid,String keyword, int status, String startTime, String endTime, final int page);
    }
}
