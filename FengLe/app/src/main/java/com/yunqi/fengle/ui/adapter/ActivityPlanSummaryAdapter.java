package com.yunqi.fengle.ui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunqi.fengle.R;
import com.yunqi.fengle.model.response.ActivityAddResponse;
import com.yunqi.fengle.model.response.ActivitySummaryResponse;
import com.yunqi.fengle.util.DateUtil;

import java.util.List;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 17:19
 * @Description: 活动总结
 */

public class ActivityPlanSummaryAdapter extends BaseQuickAdapter<ActivitySummaryResponse,BaseViewHolder>{

    private int selectStatus = 1;

    public ActivityPlanSummaryAdapter() {
        super(R.layout.item_activity_summary);
    }

    public ActivityPlanSummaryAdapter(List<ActivitySummaryResponse> data) {
        super(R.layout.item_activity_plan,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ActivitySummaryResponse item) {
        ((TextView)helper.getView(R.id.tvClientName)).setText(item.getClient_name());
        ((TextView)helper.getView(R.id.tvRegion)).setText(item.getRegion());
        ((TextView)helper.getView(R.id.tvStartTime)).setText(DateUtil.formatB(item.getStart_time()));
        ((TextView)helper.getView(R.id.tvEndTime)).setText(DateUtil.formatB(item.getEnd_time()));
        ((TextView)helper.getView(R.id.tvActionSubmit)).setText(item.getAction_submit());
    }

    public void setSelectStatus(int selectStatus) {
        this.selectStatus = selectStatus;
    }
}
