package com.yunqi.fengle.ui.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunqi.fengle.R;
import com.yunqi.fengle.model.bean.Area;
import com.yunqi.fengle.model.bean.Payment;
import com.yunqi.fengle.model.bean.Warehouse;

import java.util.List;

/**
 * @Author: Huangweicai
 * @date 2017-02-17 23:55
 * @Description: 我的客户 adapter
 */

public class WarehouseAdapter extends BaseQuickAdapter<Warehouse,BaseViewHolder> {
    private int selectWarehouseId;
    public WarehouseAdapter(List<Warehouse> data,int selectWarehouseId) {
        super(R.layout.item_area_or_warehouse,data);
        this.selectWarehouseId=selectWarehouseId;
    }

    public void setSelectId(int selectWarehouseId){
        this.selectWarehouseId=selectWarehouseId;
    }

    @Override
    protected void convert(BaseViewHolder helper, Warehouse item) {
        TextView txtName=helper.getView(R.id.txt_name);
        ImageView imgCheck=helper.getView(R.id.img_check);
        txtName.setText(item.name);
        if(item.ck_id==selectWarehouseId){
            imgCheck.setImageResource(R.drawable.checked);
        }
        else{
            imgCheck.setImageResource(R.drawable.uncheck);
        }
    }
}
