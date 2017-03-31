package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.bean.UserBean;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public interface LoginContract {

    interface View extends BaseView{
        void jump2Main(UserBean userBean);
    }
    interface Presenter extends BasePresenter<View> {
        void doLogin(String username,String password);
    }
}
