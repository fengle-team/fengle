package com.yunqi.fengle.ui.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunqi.fengle.R;
import com.yunqi.fengle.model.bean.Area;
import com.yunqi.fengle.model.bean.Payment;

import java.util.List;

/**
 * @Author: Huangweicai
 * @date 2017-02-17 23:55
 * @Description: 我的客户 adapter
 */

public class AreaAdapter extends BaseQuickAdapter<Area,BaseViewHolder> {
    private int selectAreaId;
    public AreaAdapter(List<Area> data,int selectAreaId) {
        super(R.layout.item_area_or_warehouse,data);
        this.selectAreaId=selectAreaId;
    }

    public void setSelectAreaId(int selectAreaId){
        this.selectAreaId=selectAreaId;
    }

    @Override
    protected void convert(BaseViewHolder helper, Area item) {
        TextView txtName=helper.getView(R.id.txt_name);
        ImageView imgCheck=helper.getView(R.id.img_check);
        txtName.setText(item.name);
        if(item.id==selectAreaId){
            imgCheck.setImageResource(R.drawable.checked);
        }
        else{
            imgCheck.setImageResource(R.drawable.uncheck);
        }
    }
}
