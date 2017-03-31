package com.yunqi.fengle.ui.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunqi.fengle.R;
import com.yunqi.fengle.model.bean.PaymentType;

import java.util.List;

/**
 * @Author: Huangweicai
 * @date 2017-02-17 23:55
 * @Description: 我的客户 adapter
 */

public class PaymentTypeAdapter extends BaseQuickAdapter<PaymentType,BaseViewHolder> {
    private int selectPaymentId;
    public PaymentTypeAdapter(List<PaymentType> data, int selectPaymentId) {
        super(R.layout.item_area_or_warehouse,data);
        this.selectPaymentId=selectPaymentId;
    }

    public void setSelectPaymentTypeId(int selectPaymentId){
        this.selectPaymentId=selectPaymentId;
    }

    @Override
    protected void convert(BaseViewHolder helper, PaymentType item) {
        TextView txtName=helper.getView(R.id.txt_name);
        ImageView imgCheck=helper.getView(R.id.img_check);
        txtName.setText(item.name);
        if(item.id==selectPaymentId){
            imgCheck.setImageResource(R.drawable.checked);
        }
        else{
            imgCheck.setImageResource(R.drawable.uncheck);
        }
    }
}
