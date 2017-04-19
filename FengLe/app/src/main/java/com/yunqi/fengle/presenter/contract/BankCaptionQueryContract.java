package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.bean.BankCaption;
import com.yunqi.fengle.model.bean.PaymentType;

import java.util.List;

/**
 * @author ghcui
 * @time 2017/2/15
 */
public interface BankCaptionQueryContract {
    interface View extends BaseView {
        void showContent(List<BankCaption> listBankCaption);

    }
    interface Presenter extends BasePresenter<BankCaptionQueryContract.View> {
        /**
         * 获取会计科目
         */
        void queryBankCaption();
    }
}
