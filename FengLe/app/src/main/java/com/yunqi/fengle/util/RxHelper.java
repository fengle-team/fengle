package com.yunqi.fengle.util;

import android.text.TextUtils;
import android.view.View;

import com.jakewharton.rxbinding.view.RxView;
import com.yunqi.fengle.ui.activity.LoginActivity;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 * @Author: Huangweicai
 * @date 2017-02-17 17:35
 * @Description:(这里用一句话描述这个类的作用)
 */

public class RxHelper {

    public static void click(View v, final RxClickCallback callback) {
        RxView.clicks(v)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        callback.onCall();
                    }
                });
    }

    public interface RxClickCallback{
        void onCall();
    }

}
