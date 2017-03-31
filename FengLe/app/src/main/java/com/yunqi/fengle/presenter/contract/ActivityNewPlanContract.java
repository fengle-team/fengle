package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.request.ActivityAddPlanRequest;
import com.yunqi.fengle.ui.view.UnderLineEditTextEx;
import com.yunqi.fengle.util.map.ResponseListener;

/**
 * @Author: Huangweicai
 * @date 2017-02-21 09:30
 * @Description:
 */

public interface ActivityNewPlanContract {
    interface View extends BaseView {
    }

    interface Presenter extends BasePresenter<ActivityNewPlanContract.View> {

        String checkEdit(UnderLineEditTextEx textEx);

        void activityNewPlan(ActivityAddPlanRequest request,final ResponseListener listener);

        void doSubmit(UnderLineEditTextEx... etList);

        void getData(ResponseListener listener);
    }
}
