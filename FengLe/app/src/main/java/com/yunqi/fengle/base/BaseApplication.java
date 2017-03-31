package com.yunqi.fengle.base;


import android.app.Activity;

import com.yunqi.fengle.BuildConfig;
import com.yunqi.fengle.component.CrashHandlerApplication;
import com.yunqi.fengle.constants.Constants;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;


/**
 * Application基类
 *
 * @author ghcui
 * @version [版本号, 2017-1-11]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BaseApplication extends CrashHandlerApplication {

    private List<Activity> allActivities;
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        //加入内存泄露监控
        LeakCanary.install(this);
        if(!Constants.isDebug){
            //加入腾讯bugly
            CrashReport.initCrashReport(getApplicationContext(), Constants.TENCENT_BUGLY_APPID, false);
        }
    }

    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new ArrayList<>();
        }
        allActivities.add(act);
    }

    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    public void killAllActivities() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
    }

}
