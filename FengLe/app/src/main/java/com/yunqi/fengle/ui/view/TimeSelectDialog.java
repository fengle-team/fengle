package com.yunqi.fengle.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.ui.view.wheelview.OnWheelScrollListener;
import com.yunqi.fengle.ui.view.wheelview.WheelView;
import com.yunqi.fengle.ui.view.wheelview.adapter.NumericWheelAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间选择Dialog
 */

public class TimeSelectDialog extends Dialog {
    private Context context;
    private TimeSelectListener listener;
    private int mHour = 0;
    private int mMinute = 0;
    private int mYear = 0;
    private int mMonth = 0;
    private int mDay = 1;
    private WheelView dayWheelView;
    private long lTime;
    private String strTime;

    public TimeSelectDialog(Context context, TimeSelectListener listener) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.wheel_date_picker, null);
        Window dialogWindow = getWindow();
        setContentView(view);
        TextView txtCancel = (TextView) dialogWindow.findViewById(R.id.txt_cancel);
        TextView txtConfirm = (TextView) dialogWindow.findViewById(R.id.txt_confirm);
        txtConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                Date date = new Date(mYear - 1900, mMonth, mDay, mHour, mMinute);
                lTime = date.getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                strTime = formatter.format(date);
                listener.onTimeSelected(lTime, strTime);
            }
        });
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        dialogWindow.setWindowAnimations(R.style.dialogWindowAnim); //设置窗口弹出动画
        dialogWindow.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        setCanceledOnTouchOutside(true);
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = d.widthPixels; // 高度设置为屏幕的
        dialogWindow.setAttributes(lp);

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        int curYeah = mYear - 1970;
        int curMonth = mMonth + 1;
        int curDate = mDay;
        int curHour = mHour + 1;
        int curMin = mMinute;

        WheelView yeahWheelView = (WheelView) dialogWindow.findViewById(R.id.year);
        NumericWheelAdapter numericWheelAdapter1 = new NumericWheelAdapter(context, 1970, 2100, "%04d");
        numericWheelAdapter1.setLabel("年");
        yeahWheelView.setViewAdapter(numericWheelAdapter1);
        yeahWheelView.setCyclic(true);
        yeahWheelView.addScrollingListener(scrollListener);

        WheelView monthWheelView = (WheelView) dialogWindow.findViewById(R.id.month);
        NumericWheelAdapter numericWheelAdapter2 = new NumericWheelAdapter(context, 1, 12, "%02d");
        numericWheelAdapter2.setLabel("月");
        monthWheelView.setViewAdapter(numericWheelAdapter2);
        monthWheelView.setCyclic(true);
        monthWheelView.addScrollingListener(scrollListener);

        dayWheelView = (WheelView) dialogWindow.findViewById(R.id.day);
        NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(context, 1, getDay(mYear, mMonth + 1), "%02d");
        numericWheelAdapter.setLabel("日");
        dayWheelView.setViewAdapter(numericWheelAdapter);
        dayWheelView.setCyclic(true);
        dayWheelView.addScrollingListener(scrollListener);


        WheelView hourWheelView = (WheelView) dialogWindow.findViewById(R.id.hour);
        NumericWheelAdapter numericWheelAdapter3 = new NumericWheelAdapter(context, 0, 23, "%02d");
        numericWheelAdapter3.setCurrent(curHour);
        numericWheelAdapter3.setLabel("时");
        hourWheelView.setViewAdapter(numericWheelAdapter3);
        hourWheelView.setCyclic(true);
        hourWheelView.addScrollingListener(scrollListener);

        WheelView minWheelView = (WheelView) dialogWindow.findViewById(R.id.min);
        NumericWheelAdapter numericWheelAdapter4 = new NumericWheelAdapter(context, 0, 59, "%02d");
        numericWheelAdapter4.setLabel("分");
        minWheelView.setViewAdapter(numericWheelAdapter4);
        minWheelView.setCyclic(true);
        minWheelView.addScrollingListener(scrollListener);

        yeahWheelView.setVisibleItems(7);
        monthWheelView.setVisibleItems(7);
        dayWheelView.setVisibleItems(7);
        hourWheelView.setVisibleItems(7);
        minWheelView.setVisibleItems(7);

        yeahWheelView.setCurrentItem(curYeah);
        monthWheelView.setCurrentItem(curMonth - 1);
        dayWheelView.setCurrentItem(curDate - 1);
        hourWheelView.setCurrentItem(curHour);
        minWheelView.setCurrentItem(curMin);
    }


    private int getDay(int year, int month) {
        int day = 30;
        boolean flag = year % 4 == 0;

        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                day = flag ? 29 : 28;
                break;
            default:
                day = 30;
                break;
        }
        return day;
    }

    OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
        @Override
        public void onScrollingStarted(WheelView wheel) {

        }

        @Override
        public void onScrollingFinished(WheelView wheel) {
            switch (wheel.getId()) {
                case R.id.year: {
                    mYear = wheel.getCurrentItem()+1970;
                    //如果是2月，则重新计算天数
                    if (mMonth == 1) {
                        NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(context, 1, getDay(mYear, mMonth + 1), "%02d");
                        numericWheelAdapter.setLabel("日");
                        dayWheelView.setViewAdapter(numericWheelAdapter);
                    }
                }
                break;
                case R.id.month: {
                    mMonth = wheel.getCurrentItem();
                    NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(context, 1, getDay(mYear, mMonth + 1), "%02d");
                    numericWheelAdapter.setLabel("日");
                    dayWheelView.setViewAdapter(numericWheelAdapter);
                }
                break;
                case R.id.day:
                    mDay = wheel.getCurrentItem() + 1;//日
                    break;
                case R.id.hour:
                    mHour = wheel.getCurrentItem();//时
                    break;
                case R.id.min:
                    mMinute = wheel.getCurrentItem();//分
                    break;
                default:
                    break;
            }
        }
    };

    public interface TimeSelectListener {
        void onTimeSelected(long ltime, String strTime);
    }
}
