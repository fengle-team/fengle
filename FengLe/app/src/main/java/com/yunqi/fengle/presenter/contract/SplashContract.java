package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.bean.SplashBean;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public interface SplashContract {

    interface View extends BaseView{
        void showContent(SplashBean splashBean);
        void jump2Main();
    }
    interface Presenter extends BasePresenter<View> {
        void getSplash();
    }
}
