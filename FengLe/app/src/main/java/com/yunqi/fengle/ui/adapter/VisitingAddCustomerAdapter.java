package com.yunqi.fengle.ui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunqi.fengle.R;
import com.yunqi.fengle.model.response.CustomersResponse;
import com.yunqi.fengle.model.response.VisitingPlanResponse;

import java.util.List;

/**
 * @Author: Huangweicai
 * @date 2017-02-17 18:18
 * @Description: {@link com.yunqi.fengle.ui.activity.VisitingPlanActivity}
 */

public class VisitingAddCustomerAdapter extends BaseQuickAdapter<CustomersResponse,BaseViewHolder> {
    public VisitingAddCustomerAdapter(List<CustomersResponse> data) {
        super(R.layout.item_visiting_add_customer,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CustomersResponse item) {
        TextView tvClientName = helper.getView(R.id.tvClientName);//客户名称
        TextView tvCompany = helper.getView(R.id.tvCompany);//公司
        TextView tvPosition = helper.getView(R.id.tvPosition);//职位
        tvClientName.setText(item.getName());
        tvCompany.setText(item.getCompany_name());
        tvPosition.setText(item.getPosition());
    }
}
