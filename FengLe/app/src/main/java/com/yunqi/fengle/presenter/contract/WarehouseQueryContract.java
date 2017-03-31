package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.bean.Area;
import com.yunqi.fengle.model.bean.Customer;
import com.yunqi.fengle.model.bean.Warehouse;

import java.util.List;

/**
 * @author ghcui
 * @time 2017/2/15
 */
public interface WarehouseQueryContract {
    interface View extends BaseView {
        void showContent(List<Warehouse> listWarehouse);

    }
    interface Presenter extends BasePresenter<WarehouseQueryContract.View> {
        /**
         * 仓库查询
         * @return
         */
        void queryWarehouse();
    }
}
