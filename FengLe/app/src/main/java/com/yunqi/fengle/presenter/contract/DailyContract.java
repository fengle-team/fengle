package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.util.map.ResponseListener;

/**
 * @Author: Huangweicai
 * @date 2017-02-21 09:30
 * @Description:
 */

public interface DailyContract {
    interface View extends BaseView {
    }

    interface Presenter extends BasePresenter<DailyContract.View> {

        void getDaily(String startTime, String endTime, ResponseListener listener);

//        void getDeatil();

    }
}
