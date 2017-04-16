package com.yunqi.fengle.ui.adapter;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    protected void convert(BaseViewHolder helper, final CustomersResponse item) {
        TextView tvName = helper.getView(R.id.tvName);
        TextView tvLevName = helper.getView(R.id.tvLevName);
        ImageView ivIcon = helper.getView(R.id.ivIcon);

        tvName.setText(item.getCompany_name());


        ivIcon.setBackgroundResource(item.getResource());



        tvLevName.setTextColor(mContext.getResources().getColor(item.getTxtColor()));
        tvLevName.setText(item.getLev());


//        if (item.isNeedUp()) {
            helper.addOnClickListener(R.id.llRight);
//        }
    }




}
