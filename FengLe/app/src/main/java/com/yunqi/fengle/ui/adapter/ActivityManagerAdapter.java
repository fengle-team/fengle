package com.yunqi.fengle.ui.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunqi.fengle.R;
import com.yunqi.fengle.model.bean.ActivityMgrBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 16:44
 * @Description:(这里用一句话描述这个类的作用)
 */

public class ActivityManagerAdapter extends BaseQuickAdapter<ActivityMgrBean,BaseViewHolder> {

    public ActivityManagerAdapter(List<ActivityMgrBean> data) {
        super(R.layout.module_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ActivityMgrBean item) {

        ImageView iv = helper.getView(R.id.icon);
        iv.setBackgroundResource(item.getIcon());
        TextView tv = helper.getView(R.id.text);
        tv.setText(item.getText());
    }

    public static List<ActivityMgrBean> getBeanList() {
        List<ActivityMgrBean> returnList = new ArrayList<>();
        returnList.add(new ActivityMgrBean(R.drawable.icon_plan,"活动计划"));
        returnList.add(new ActivityMgrBean(R.drawable.icon_activity,"活动总结"));
        returnList.add(new ActivityMgrBean(R.drawable.icon_expense,"费用报销"));
        return returnList;
    }
}
