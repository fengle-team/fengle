package com.yunqi.fengle.base;

/**
 * Created by codeest on 2016/8/2.
 * View基类
 */
public interface BaseView {

    void showLoading();

    void cancelLoading();

    void showLoading(int type);

    void cancelLoading(int type);

    void showError(String msg);

    void useNightMode(boolean isNight);

}
