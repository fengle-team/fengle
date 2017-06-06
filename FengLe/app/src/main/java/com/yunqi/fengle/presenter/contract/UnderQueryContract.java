package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.bean.Area;
import com.yunqi.fengle.model.bean.UserBean;

import java.util.List;

/**
 * @author ghcui
 * @time 2017/2/15
 */
public interface UnderQueryContract {
    interface View extends BaseView {
        void showContent(List<UserBean> listUser);

    }
    interface Presenter extends BasePresenter<UnderQueryContract.View> {
        /**
         * 大区查询
         * @return
         */
        void queryUnder(String userid);
    }
}
