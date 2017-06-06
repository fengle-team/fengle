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
import android.widget.RadioButton;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.UserBean;
import com.yunqi.fengle.model.response.DailyResponse;
import com.yunqi.fengle.presenter.DailyPresenter;
import com.yunqi.fengle.presenter.contract.DailyContract;
import com.yunqi.fengle.ui.adapter.DailyAdapter2;
import com.yunqi.fengle.ui.model.DayViewDecoratorEx;
import com.yunqi.fengle.ui.view.CalendarDecorator1;
import com.yunqi.fengle.util.DateUtil;
import com.yunqi.fengle.util.TimeUtil;
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

public class DailyActivity extends BaseActivity<DailyPresenter> implements DailyContract.View,OnDateSelectedListener,View.OnFocusChangeListener {

    @BindView(R.id.calendarView)
    MaterialCalendarView widget;

    DailyAdapter2 adapter;

    @BindView(R.id.rvList)
    RecyclerView rvList;
    @BindView(R.id.radioBtn1)
    RadioButton radioBtn1;
    @BindView(R.id.radioBtn2)
    RadioButton radioBtn2;
    @BindView(R.id.radioBtn3)
    RadioButton radioBtn3;
    @BindView(R.id.radioBtn4)
    RadioButton radioBtn4;

    List<DailyResponse> dataList = new ArrayList<>();

    DayViewDecoratorEx decorator;
    UserBean underUser;

    private String startTime="";
    private String endTime="";
    private String userid="";
    private int type=1;
    private String dailyTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("销售日志");
        setTitleRightImage(R.drawable.right_add);

        initView();
        initRecyclerView();

        initData();
        setWidgetListener();
    }

    private void setWidgetListener() {
        radioBtn1.setOnFocusChangeListener(this);
        radioBtn2.setOnFocusChangeListener(this);
        radioBtn3.setOnFocusChangeListener(this);
        radioBtn4.setOnFocusChangeListener(this);
    }

    private void initData() {
        dailyTime= TimeUtil.getCurrentTime("yyyy-MM-dd");
        dataList = new ArrayList<>();
        progresser.showProgress();

        radioBtn1.requestFocus();
        if(UserBean.ROLE_YWY.equals(App.getInstance().getUserInfo().role_code)){
            radioBtn3.setVisibility(View.GONE);
        }
        type=1;
        userid=App.getInstance().getUserInfo().id;
        loadDailyData();
    }

    private void loadDailyData(){
        mPresenter.getDaily(startTime, endTime,userid,type, new ResponseListener() {
            @Override
            public void onSuccess(NetResponse response) {
                dealSuccess(response);
//                updateView(new Date());
                adapter.setNewData(dataList);
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
        widget.removeDecorators();
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
        dailyTime=TimeUtil.format(date.getDate(),"yyyy-MM-dd");
        startTime= TimeUtil.converTime("yyyy-MM-dd","yyyy-MM-dd HH:mm",dailyTime);
        endTime= dailyTime+" 23:59";
        loadDailyData();
//        updateView(date.getDate());
    }

    private void resetData(){
        underUser=null;
        dailyTime= TimeUtil.getCurrentTime("yyyy-MM-dd");
        startTime="";
        endTime="";
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
        adapter.setNewData(dataList);
    }

    @Override
    protected int getViewModel() {
        return MODE_VIEW_02;
    }

    @Override
    protected void onTitleRightClicked(View v) {
        Intent mIntent = new Intent();
        mIntent.setClass(this, DailySendActivity.class);
        mIntent.putExtra("DailyTime",dailyTime);
        startActivityForResult(mIntent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {//需要刷新界面
            initData();
        }
        else if(requestCode==1&&resultCode==1){
            //查询下级的日志
            underUser= (UserBean) data.getSerializableExtra("UnderUser");
            userid=underUser.id;
            type=2;
            loadDailyData();
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

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if(hasFocus){
            resetData();
            switch (view.getId()){
                //查询全部日志
                case R.id.radioBtn1:
                    userid=App.getInstance().getUserInfo().id;
                    type=1;
                    loadDailyData();
                    break;
                //查询自己的日志
                case R.id.radioBtn2:
                    userid=App.getInstance().getUserInfo().id;
                    type=2;
                    loadDailyData();
                    break;
                case R.id.radioBtn3:
                    Intent intent=new Intent(this,UnderQueryActivity.class);
                    startActivityForResult(intent,1);
                    break;
                case R.id.radioBtn4:
                    break;
            }
        }
    }



}
