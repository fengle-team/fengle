package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.util.map.ResponseListener;

/**
 * @Author: Huangweicai
 * @date 2017-02-15 18:02
 * @Description:(这里用一句话描述这个类的作用)
 */

public interface PersonContract {
    public interface View extends BaseView {


    }

    interface Presenter extends BasePresenter<PersonContract.View> {
        void checkFormat(String oldPwd, String newPwd, String newConfirmPwd, ResponseListener listener);
    }


}
