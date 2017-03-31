package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.bean.Goods;

import java.util.List;

/**
 * @author ghcui
 * @time 2017/2/15
 */
public interface StockQueryContract {
    interface View extends BaseView {
        void showContent(List<Goods> listGoods);

        void showMoreContent(List<Goods> listGoodsMore);

    }
    interface Presenter extends BasePresenter<StockQueryContract.View> {
        /**
         * 库存查询
         * @param warehouse_code 仓库code
         * @param area_code 货物名或者编号
         * @param keywords 货物名或者编号
         * @param page 页码
         * @return
         */
        void queryStock(String warehouse_code,String area_code,String keywords,int page);
    }
}
