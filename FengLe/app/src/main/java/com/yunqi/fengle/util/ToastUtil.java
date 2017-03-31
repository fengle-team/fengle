package com.yunqi.fengle.util;


import android.content.Context;
import android.widget.Toast;

/**
 * @author ghcui
 * @time 2017/1/18
 */
public class ToastUtil {

    /**
     * 错误弹窗
     *
     * @param context
     * @param msg
     */
    public static void showErrorToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 提示弹窗
     *
     * @param context
     * @param msg
     */
    public static void showNoticeToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 正确弹窗
     *
     * @param context
     * @param msg
     */
    public static void showHookToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 弹窗
     * @param context
     * @param msg
     */
    public static void toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
