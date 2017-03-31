package com.yunqi.fengle.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunqi.fengle.R;
import com.yunqi.fengle.model.response.CustomersResponse;

import java.util.List;

/**
 * @Author: Huangweicai
 * @date 2017-02-17 18:18
 * @Description: {@link com.yunqi.fengle.ui.activity.VistingAddVisteActivity}
 */

public class VisitingAddVisteAdapter extends BaseQuickAdapter<CustomersResponse,BaseViewHolder> {
    public VisitingAddVisteAdapter(List<CustomersResponse> data) {
        super(R.layout.item_visiting_add_visite,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CustomersResponse item) {


    }
}
