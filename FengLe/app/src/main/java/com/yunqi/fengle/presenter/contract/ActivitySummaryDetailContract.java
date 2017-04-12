package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.request.ActivitySummaryRequest;
import com.yunqi.fengle.util.map.ResponseListener;

/**
 * @Author: Huangweicai
 * @date 2017-02-21 09:30
 * @Description:
 */

public interface ActivitySummaryDetailContract {
    interface View extends BaseView {
    }

    interface Presenter extends BasePresenter<ActivitySummaryDetailContract.View> {
        void addSummary(ActivitySummaryRequest request, ResponseListener listener);

        void getData(ResponseListener listener);
    }
}
