package com.yunqi.fengle.ui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunqi.fengle.R;
import com.yunqi.fengle.model.response.CustomersResponse;

import java.util.List;

/**
 * @Author: Huangweicai
 * @date 2017-02-17 23:55
 * @Description: 我的客户 adapter
 *              {@link com.yunqi.fengle.ui.activity.MyCustomersActivity}
 */

public class MyCustomersAdapter extends BaseQuickAdapter<CustomersResponse,BaseViewHolder> {
    public MyCustomersAdapter(List<CustomersResponse> data) {
        super(R.layout.item_my_customers,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CustomersResponse item) {
        TextView tvName = helper.getView(R.id.tvName);
        TextView tvAddr = helper.getView(R.id.tvAddr);
        TextView tvLevName = helper.getView(R.id.tvLevName);

        tvName.setText(item.getCompany_name());
        tvLevName.setText(item.getLev());

    }
}
