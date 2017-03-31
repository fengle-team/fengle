package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.bean.FukuanType;

import java.util.List;

/**
 * @author ghcui
 * @time 2017/2/15
 */
public interface FukuanTypeQueryContract {
    interface View extends BaseView {
        void showContent(List<FukuanType> listFukuanType);

    }
    interface Presenter extends BasePresenter<FukuanTypeQueryContract.View> {
        /**
         * 回款类型查询
         * @return
         */
        void queryHuikuanType();
    }
}
