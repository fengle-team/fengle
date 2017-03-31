package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.bean.Area;
import com.yunqi.fengle.model.bean.PaymentType;

import java.util.List;

/**
 * @author ghcui
 * @time 2017/2/15
 */
public interface PaymentTypeQueryContract {
    interface View extends BaseView {
        void showContent(List<PaymentType> listPaymentType);

    }
    interface Presenter extends BasePresenter<PaymentTypeQueryContract.View> {
        /**
         * 回款类型查询
         * @return
         */
        void queryPaymentType();
    }
}
