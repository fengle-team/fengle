package com.yunqi.fengle.ui.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunqi.fengle.R;
import com.yunqi.fengle.model.bean.PaymentType;
import com.yunqi.fengle.model.bean.StatusInfo;

import java.util.List;

/**
 * @Author: ghcui
 * @date 2017-05-10 23:55
 * @Description: 状态详情
 */

public class StatusAdapter extends BaseQuickAdapter<StatusInfo,BaseViewHolder> {
    public StatusAdapter(List<StatusInfo> data) {
        super(R.layout.item_status,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, StatusInfo item) {
        TextView txtTime=helper.getView(R.id.txt_time);
        TextView txtStatus=helper.getView(R.id.txt_status);
        txtTime.setText(item.create_time);
        txtStatus.setText(item.record);
    }
}
