package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.bean.GoodsSaleDetail;
import com.yunqi.fengle.model.bean.UserBean;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public interface GoodsSaleDetailContract {

    interface View extends BaseView{
        void showContent(GoodsSaleDetail goodsSaleDetail);
    }
    interface Presenter extends BasePresenter<View> {
        void getGoodsSaleDetail(String sale_id);
    }
}
