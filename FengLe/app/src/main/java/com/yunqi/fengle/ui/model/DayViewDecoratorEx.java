package com.yunqi.fengle.ui.model;

import android.graphics.Color;
import android.text.TextUtils;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;
import com.yunqi.fengle.model.response.DailyResponse;
import com.yunqi.fengle.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @Author: Huangweicai
 * @date 2017-03-15 16:47
 * @Description:(这里用一句话描述这个类的作用)
 */

public class DayViewDecoratorEx implements DayViewDecorator {
    private List<Date> dateList = new ArrayList<>();

    private int color = Color.RED;

    @Override
    public boolean shouldDecorate(CalendarDay calendarDay) {
        Date date = calendarDay.getDate();

        for (Date bean : dateList) {
            if (DateUtil.isSameDate(bean, date)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void decorate(DayViewFacade dayViewFacade) {
        dayViewFacade.addSpan(new DotSpan(5, color));
//        dayViewFacade.setShowLogo(true);
    }

    public void setDailyList(List<DailyResponse> dailyResponseList) {
        for (DailyResponse bean : dailyResponseList) {
            String timeStr = bean.getCreate_time();
            if (TextUtils.isEmpty(timeStr)) {
                continue;
            }
            Date date = DateUtil.str2Date(timeStr);
            if (date != null) {
                dateList.add(date);
            }
        }
    }

}
