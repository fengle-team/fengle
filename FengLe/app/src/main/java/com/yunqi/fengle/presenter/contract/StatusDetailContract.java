package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.bean.Area;
import com.yunqi.fengle.model.bean.StatusInfo;

import java.util.List;

/**
 * @author ghcui
 * @time 2017/2/15
 */
public interface StatusDetailContract {
    interface View extends BaseView {
        void showContent(List<StatusInfo> listStatus);

    }
    interface Presenter extends BasePresenter<StatusDetailContract.View> {
        /**
         * 查询状态详情
         * @return
         */
        void queryStatusDetail(String order_code);
    }
}
