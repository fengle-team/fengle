package com.yunqi.fengle.ui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunqi.fengle.R;
import com.yunqi.fengle.model.response.ActivityAddResponse;
import com.yunqi.fengle.util.DateUtil;

import java.util.List;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 17:19
 * @Description: 活动计划
 *              {@link com.yunqi.fengle.ui.activity.ActivityPlanActivity}
 */

public class ActivityPlanManagerAdapter extends BaseQuickAdapter<ActivityAddResponse,BaseViewHolder>{

    public ActivityPlanManagerAdapter() {
        super(R.layout.item_activity_plan_manager);
    }

    public ActivityPlanManagerAdapter(List<ActivityAddResponse> data) {
        super(R.layout.item_activity_plan,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ActivityAddResponse item) {
        ((TextView)helper.getView(R.id.tvCompany)).setText(item.getClient_name());
        ((TextView)helper.getView(R.id.tvRegion)).setText(item.getRegion());
        ((TextView)helper.getView(R.id.tvApplyBudget)).setText(item.getApply_budget() + "");
        ((TextView)helper.getView(R.id.tvStartTime)).setText(DateUtil.formatA(item.getStart_time()));
        ((TextView)helper.getView(R.id.tvEndTime)).setText(DateUtil.formatA(item.getEnd_time()));
    }
}
