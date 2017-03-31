package com.yunqi.fengle.util;

import android.util.Log;

/**
 * @Author: Huangweicai
 * @date 2017-02-15 22:09
 * @Description:(这里用一句话描述这个类的作用)
 */

public class LogEx {
    private static final String LOG_TAG = "LogEx";

    public static void e(String log) {
        Log.e(LOG_TAG, log);
    }
    public static void i(String log) {
        Log.i(LOG_TAG, log);
    }
    public static void w(String log) {
        Log.w(LOG_TAG, log);
    }
}
