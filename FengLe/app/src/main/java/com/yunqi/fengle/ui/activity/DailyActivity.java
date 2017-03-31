package com.yunqi.fengle.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.response.DailyResponse;
import com.yunqi.fengle.presenter.DailyPresenter;
import com.yunqi.fengle.presenter.contract.DailyContract;
import com.yunqi.fengle.ui.adapter.DailyAdapter2;
import com.yunqi.fengle.ui.model.DayViewDecoratorEx;
import com.yunqi.fengle.ui.view.CalendarDecorator1;
import com.yunqi.fengle.util.DateUtil;
import com.yunqi.fengle.util.ToastUtil;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * @Author: Huangweicai
 * @date 2017-02-17 09:24
 * @Description:日报
 */

public class DailyActivity extends BaseActivity<DailyPresenter> implements DailyContract.View,OnDateSelectedListener {

    @BindView(R.id.calendarView)
    MaterialCalendarView widget;

    DailyAdapter2 adapter;

    @BindView(R.id.rvList)
    RecyclerView rvList;

    List<DailyResponse> dataList = new ArrayList<>();

    DayViewDecoratorEx decorator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("销售日志");
        setTitleRightImage(R.drawable.right_add);

        initView();
        initRecyclerView();

        initData();
    }

    private void initData() {
        progresser.showProgress();
        mPresenter.getDaily("", "", new ResponseListener() {
            @Override
            public void onSuccess(NetResponse response) {
                dealSuccess(response);
                updateView(new Date());
                progresser.showContent();
            }

            @Override
            public void onFaild(NetResponse response) {
                progresser.showContent();
                ToastUtil.toast(mContext,response.getMsg());
            }
        });
    }

    private void dealSuccess(NetResponse response) {
        dataList = (List<DailyResponse>) response.getResult();
        decorator = new DayViewDecoratorEx();
        decorator.setDailyList((List<DailyResponse>) response.getResult());
        widget.addDecorator(decorator);
    }

    private void initRecyclerView() {
        rvList.setLayoutManager(new LinearLayoutManager(this));


        adapter = new DailyAdapter2(dataList);
        rvList.setAdapter(adapter);
    }

    private void initView() {

        Calendar calendar = Calendar.getInstance();
        widget.setBackgroundColor(getResources().getColor(R.color.white));
        widget.addDecorator(new CalendarDecorator1());
        widget.setSelectedDate(calendar.getTime());
        widget.setOnDateChangedListener(this);

        widget.state().edit()
//                .setMinimumDate(instance1.getTime())
//                .setMaximumDate(instance2.getTime())
                .commit();
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        //If you change a decorate, you need to invalidate decorators
//        oneDayDecorator.setDate(date.getDate());
//        widget.invalidateDecorators();
        updateView(date.getDate());
    }

    private void updateView(Date date) {
        List<DailyResponse> selectedList = new ArrayList<>();

        for (DailyResponse bean : dataList) {
            String timeStr = bean.getCreate_time();
            if (TextUtils.isEmpty(timeStr)) {
                continue;
            }
            Date curDate = DateUtil.str2Date(timeStr);

            if (DateUtil.isSameDate(curDate, date)) {
                selectedList.add(bean);
            }
        }
        adapter.setNewData(selectedList);
    }

    @Override
    protected int getViewModel() {
        return MODE_VIEW_02;
    }

    @Override
    protected void onTitleRightClicked(View v) {
        Intent mIntent = new Intent();
        mIntent.setClass(this, DailySendActivity.class);
        startActivityForResult(mIntent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {//需要刷新界面

        }
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_daily;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void showError(String msg) {

    }
}
