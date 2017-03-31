package com.yunqi.fengle.util;

import android.support.v4.app.FragmentActivity;

import com.yunqi.fengle.model.bean.SpinnerBean;
import com.yunqi.fengle.ui.fragment.dialog.SimpleDialogEditFragment;
import com.yunqi.fengle.ui.fragment.dialog.SimpleDialogFragment;
import com.yunqi.fengle.ui.fragment.dialog.SpinnerDialogFragment;

/**
 * @Author: Huangweicai
 * @date 2017-02-21 23:01
 * @Description:(这里用一句话描述这个类的作用)
 */

public class DialogHelper {
    public static void showDialog(FragmentActivity activity, String msg, SimpleDialogFragment.OnSimpleDialogListener listener) {
        showDialog(activity,msg,"","",listener,null);
    }
    public static void showDialog(FragmentActivity activity, String msg, String okStr, String backStr, SimpleDialogFragment.OnSimpleDialogListener listener, SimpleDialogFragment.OnBackDialogListener backListener) {
        SimpleDialogFragment fragment = SimpleDialogFragment.newInstance(msg,okStr,backStr);
        fragment.setOnSimpleDialogListener(listener);
        fragment.setOnBackDialogListener(backListener);
        fragment.show(activity.getSupportFragmentManager(), msg);
    }

    public static void showEditDialog(FragmentActivity activity, String msg, SimpleDialogEditFragment.OnSimpleDialogEditListener listener) {
        SimpleDialogEditFragment fragment = SimpleDialogEditFragment.newInstance(msg);
        fragment.setOnSimpleDialogEditListener(listener);
        fragment.show(activity.getSupportFragmentManager(), msg);
    }

    public static void showSpinnerDialog(FragmentActivity activity, SpinnerBean spinner, SpinnerDialogFragment.OnSpinnerDialogListener listener) {
        SpinnerDialogFragment fragment = SpinnerDialogFragment.newInstance(spinner);
        fragment.setListener(listener);
        fragment.show(activity.getSupportFragmentManager(),"msg");
    }




}
