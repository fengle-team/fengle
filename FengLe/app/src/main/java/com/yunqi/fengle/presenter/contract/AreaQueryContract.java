package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.bean.Area;
import com.yunqi.fengle.model.bean.Customer;

import java.util.List;

/**
 * @author ghcui
 * @time 2017/2/15
 */
public interface AreaQueryContract {
    interface View extends BaseView {
        void showContent(List<Area> listArea);

    }
    interface Presenter extends BasePresenter<AreaQueryContract.View> {
        /**
         * 大区查询
         * @return
         */
        void queryArea();
    }
}
