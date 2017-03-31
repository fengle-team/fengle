package com.yunqi.fengle.rx;


import android.text.TextUtils;

import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.http.BaseHttpRsp;
import com.yunqi.fengle.util.map.NetResponse;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;


/**
 * @author ghcui
 * @time 2017/1/13
 */
public abstract class ExSubscriber<T> extends Subscriber<T> {

    public static final String TAG = "ExSubscriber";
    private BaseView view;
    private int opraterType = 0;//查询 1：添加 2:更新 3：删除

    public ExSubscriber(BaseView view) {
        this(view,0);
    }
    public ExSubscriber(BaseView view,int opraterType) {
        this.view = view;
        this.opraterType = opraterType;
    }

    @Override
    public void onStart() {
        showLoading();

    }

    @Override
    public void onCompleted() {
        cancelLoading();
    }

    @Override
    public void onError(Throwable e) {
        cancelLoading();
        e.printStackTrace();
        if (e instanceof HttpException) {
            this.view.showError("网络断开,请检查网络!");
        } else if (e instanceof ConnectException) {
            this.view.showError("网络断开,请检查网络!");
        } else if (e instanceof SocketTimeoutException) {
            this.view.showError("网络连接超时!");
        } else {
            String errormsg = e.getMessage();
            if (TextUtils.isEmpty(errormsg)) {
                errormsg = "服务器异常!";
            }
            this.view.showError(errormsg);
        }
    }


    @Override
    public void onNext(T t) {
        if (t == null) {
            cancelLoading();
            this.view.showError("请求失败!");
            return;
        }
        if (isNeedVerify()) {
            if (t instanceof BaseHttpRsp) {
                BaseHttpRsp rsp = (BaseHttpRsp) t;
                if (rsp.getCode() != 200) {
//                    onFailed(new NetResponse(rsp.getCode(),rsp.getMessage()));
                    return;
                }
            }
        }
        onSuccess(t);
    }

    public void cancelLoading(){
        if (opraterType == 0) {
            this.view.cancelLoading();
        } else {
            this.view.cancelLoading(opraterType);
        }
    }
    public void showLoading(){
        if (opraterType == 0) {
            this.view.showLoading();
        } else {
            this.view.showLoading(opraterType);
        }
    }

    boolean isNeedVerify() {
        return false;
    }


    protected abstract void onSuccess(T t);


}
