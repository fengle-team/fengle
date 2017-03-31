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

public class ActivityPlanAdapter extends BaseQuickAdapter<ActivityAddResponse,BaseViewHolder>{

    public ActivityPlanAdapter() {
        super(R.layout.item_activity_plan);
    }

    public ActivityPlanAdapter(List<ActivityAddResponse> data) {
        super(R.layout.item_activity_plan,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ActivityAddResponse item) {
        ((TextView)helper.getView(R.id.tvClientName)).setText(item.getClient_name());
        ((TextView)helper.getView(R.id.tvRegion)).setText(item.getRegion());
        ((TextView)helper.getView(R.id.tvApplyBudget)).setText(item.getApply_budget() + "");
        ((TextView)helper.getView(R.id.tvShopName)).setText(item.getShop_name());
        ((TextView)helper.getView(R.id.tvBaoxiaoBudget)).setText(item.getBaoxiao_budget() + "");
        ((TextView)helper.getView(R.id.tvStartTime)).setText(DateUtil.formatA(item.getStart_time()));
        ((TextView)helper.getView(R.id.tvEndTime)).setText(DateUtil.formatA(item.getEnd_time()));
    }
}
