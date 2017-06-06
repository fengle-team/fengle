package com.yunqi.fengle.ui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunqi.fengle.R;
import com.yunqi.fengle.model.response.VisitingPlanResponse;
import com.yunqi.fengle.util.DateUtil;

import java.util.List;

/**
 * @Author: Huangweicai
 * @date 2017-02-17 18:18
 * @Description: {@link com.yunqi.fengle.ui.activity.VisitingPlanActivity}
 */

public class VisitingMainAdapter extends BaseQuickAdapter<VisitingPlanResponse,BaseViewHolder> {
    public VisitingMainAdapter(List<VisitingPlanResponse> data) {
        super(R.layout.item_visiting_plan,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VisitingPlanResponse item) {
        TextView tvPlanTime = helper.getView(R.id.tvPlanTime);//时间
        TextView tvReason = helper.getView(R.id.tvReason);//原因
        TextView tvResponsibleName = helper.getView(R.id.tvResponsibleName);//责任人
        TextView tvClientName = helper.getView(R.id.tvClientName);//客户名称
        TextView tvStatus = helper.getView(R.id.tvStatus);//状态
        tvClientName.setText(item.getClient_name());
        tvReason.setText(item.getReason());
        tvResponsibleName.setText(item.getResponsible_name());
        tvPlanTime.setText(DateUtil.formatB(item.getPlan_time()));
        tvStatus.setText(item.getStatusStr());

        if (item.getStatus() == VisitingPlanResponse.STATUS_CONFIRM_YES) {
            tvStatus.setTextColor(mContext.getResources().getColor(R.color.text_color3));
        } else {
            tvStatus.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
        }

    }
}
