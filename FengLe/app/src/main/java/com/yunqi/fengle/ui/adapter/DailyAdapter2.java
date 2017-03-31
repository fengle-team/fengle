package com.yunqi.fengle.ui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunqi.fengle.R;
import com.yunqi.fengle.model.response.DailyResponse;

import java.util.List;

/**
 * @Author: Huangweicai
 * @date 2017-02-17 23:55
 */

public class DailyAdapter2 extends BaseQuickAdapter<DailyResponse,BaseViewHolder> {
    public DailyAdapter2(List<DailyResponse> data) {
        super(R.layout.item_daily_content,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DailyResponse item) {
        TextView tvName = helper.getView(R.id.tvName);
        TextView tvDaily = helper.getView(R.id.tvDaily);
        TextView tvTime = helper.getView(R.id.tvTime);
        TextView tvReason = helper.getView(R.id.tvReason);
        tvName.setText(item.getUserid() + "");
        tvDaily.setText(item.getDaily());
        tvTime.setText(item.getCreate_time());
        tvReason.setText(item.getReason());
    }
}
