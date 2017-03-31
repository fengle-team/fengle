package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.bean.Customer;
import com.yunqi.fengle.model.bean.Goods;

import java.util.List;

/**
 * @author ghcui
 * @time 2017/2/15
 */
public interface GoodsQueryContract {
    interface View extends BaseView {
        void showContent(List<Goods> listGoods);

        void showMoreContent(List<Goods> listGoodsMore);

    }
    interface Presenter extends BasePresenter<GoodsQueryContract.View> {
        /**
         * 客户查询
         * @param keyword 客户名或者编号
         * @param userid 用户id
         * @param warehouseId 用户id
         * @param page 页码
         * @return
         */
        void queryGoods(String keyword, String userid, String warehouseId,int page);

    }
}
