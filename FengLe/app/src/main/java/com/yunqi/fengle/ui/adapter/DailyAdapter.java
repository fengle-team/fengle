package com.yunqi.fengle.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.yunqi.fengle.R;
import com.yunqi.fengle.ui.activity.CustomerWholeActivity;
import com.yunqi.fengle.ui.activity.MyCustomersActivity;
import com.yunqi.fengle.ui.view.CalendarDecorator1;
import com.yunqi.fengle.ui.view.RecycleViewDivider;
import com.yunqi.fengle.util.LogEx;
import com.yunqi.fengle.util.ToastUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 00:28
 * @Description:客户全貌Adapter
 *              {@link com.yunqi.fengle.ui.activity.CustomerWholeActivity}
 */

public class DailyAdapter extends BaseMultiItemQuickAdapter<DailyMultiItem, BaseViewHolder> {

    private Context mContext;


    public static final int TYPE_DAILY = 0x01;//日历
    public static final int TYPE_CONTENT = 0x02;//日志

    public DailyAdapter(Context context,List<DailyMultiItem> data) {
        super(data);
        this.mContext = context;
        addItemType(DailyAdapter.TYPE_DAILY, R.layout.item_daily);
        addItemType(DailyAdapter.TYPE_CONTENT, R.layout.item_daily2);
    }


    @Override
    protected void convert(BaseViewHolder helper, DailyMultiItem item) {
        if (item.getItemType() == TYPE_DAILY) {
            initDaily(helper,item);
        } else if (item.getItemType() == TYPE_CONTENT) {
            initRecyclerView(helper,item);
        }
    }

    private void initRecyclerView(BaseViewHolder helper, DailyMultiItem item) {
        RecyclerView rvList = helper.getView(R.id.rvList);

        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        rvList.addItemDecoration(new RecycleViewDivider(mContext,RecycleViewDivider.VERTICAL_LIST));


        List<Object> testData = new ArrayList<>();
        testData.add(11);
        testData.add(11);
        testData.add(11);
        testData.add(11);
        testData.add(11);
        testData.add(11);
        testData.add(11);
        testData.add(11);
//        rvList.setAdapter(new DailyAdapter2(testData));
    }

    private void initDaily(BaseViewHolder helper, DailyMultiItem item) {
        MaterialCalendarView widget = helper.getView(R.id.calendarView);

        Calendar calendar = Calendar.getInstance();
        widget.addDecorator(new CalendarDecorator1());
        widget.setSelectedDate(calendar.getTime());

        widget.state().edit()
                .commit();
    }


}
