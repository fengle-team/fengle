package com.yunqi.fengle.ui.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunqi.fengle.R;
import com.yunqi.fengle.model.bean.BankCaption;
import com.yunqi.fengle.model.bean.PaymentType;

import java.util.List;

/**
 * @Author: Huangweicai
 * @date 2017-02-17 23:55
 * @Description: 我的客户 adapter
 */

public class BankCaptionAdapter extends BaseQuickAdapter<BankCaption,BaseViewHolder> {
    private String selectBankCode;
    public BankCaptionAdapter(List<BankCaption> data, String selectBankCode) {
        super(R.layout.item_area_or_warehouse,data);
        this.selectBankCode=selectBankCode;
    }

    public void setSelectBankCode(String selectBankCode){
        this.selectBankCode=selectBankCode;
    }

    @Override
    protected void convert(BaseViewHolder helper, BankCaption item) {
        TextView txtName=helper.getView(R.id.txt_name);
        ImageView imgCheck=helper.getView(R.id.img_check);
        txtName.setText(item.bank_name);
        if(item.bank_code.equals(selectBankCode)){
            imgCheck.setImageResource(R.drawable.checked);
        }
        else{
            imgCheck.setImageResource(R.drawable.uncheck);
        }
    }
}
