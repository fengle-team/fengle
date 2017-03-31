package com.yunqi.fengle.rx;

import com.yunqi.fengle.base.BaseView;

import rx.functions.Action1;


/**
 * @author ghcui
 * @time 2017/1/13
 */
public abstract class SuccessAction1<T> implements Action1<T> {
    private BaseView view;
    public SuccessAction1(BaseView view){
        this.view=view;
    }
    @Override
    public void call(T t) {
        if (t == null) {
            view.showError("请求数据失败!");
            return;
        }
        nextCall(t);
    }
    protected void nextCall(T t){

    }


}
