package com.yunqi.fengle.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.format.DateUtils;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 22:35
 * @Description:(这里用一句话描述这个类的作用)
 */

public class CalendarDecorator1 implements DayViewDecorator {

    private static final int color = Color.parseColor("#fffcb058");
    private final Drawable drawable;

    public CalendarDecorator1() {
        drawable = new ColorDrawable(color);
//        highlightDrawable = context.getResources().getDrawable(R.drawable.logo);
    }
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        if (DateUtils.isToday(day.getDate().getTime())) {
            return true;
        }
        return false;
    }

    @Override
    public void decorate(DayViewFacade dayViewFacade) {
//        dayViewFacade.setBackgroundDrawable(drawable);
    }
}
