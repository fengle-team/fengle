package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.util.map.ResponseListener;

/**
 * @Author: Huangweicai
 * @date 2017-02-21 09:30
 * @Description:
 */

public interface ActivityPlanUpdateContract {
    interface View extends BaseView {
    }

    interface Presenter extends BasePresenter<ActivityPlanUpdateContract.View> {

        void updatePlanUpdateStatus(String userid,String order_code,String status,ResponseListener listener);
        void deleteActivity(String id ,ResponseListener listener);


    }
}
