package com.yunqi.fengle.ui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunqi.fengle.R;
import com.yunqi.fengle.model.bean.Payment;

import java.util.List;

/**
 * @Author: Huangweicai
 * @date 2017-02-17 23:55
 * @Description: 我的客户 adapter
 */

public class PlaymentAdapter extends BaseQuickAdapter<Payment,BaseViewHolder> {
    public PlaymentAdapter(List<Payment> data) {
        super(R.layout.item_playment,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Payment item) {
        TextView txtCustomerName=helper.getView(R.id.customer_name);
        TextView txtPaymentAmount=helper.getView(R.id.payment_amount);
        TextView txtTime=helper.getView(R.id.time);
        txtCustomerName.setText(item.huikuan_name);
        txtPaymentAmount.setText(item.huikuan_amount+"");
        txtTime.setText(item.huikuan_time);
    }
}
