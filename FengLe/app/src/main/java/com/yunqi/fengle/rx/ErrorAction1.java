package com.yunqi.fengle.rx;

import android.text.TextUtils;

import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.http.BmobErrorResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okio.BufferedSource;
import retrofit2.adapter.rxjava.HttpException;
import rx.functions.Action1;


public abstract class ErrorAction1<T extends BaseView> implements Action1<Throwable> {
    private T mView;

    public ErrorAction1(T view){
        this.mView=view;
    }
    @Override
    public void call(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            if (code == 500 || code == 404) {
                BufferedSource source=httpException.response().errorBody().source();
                String error=source.buffer().toString();
                BmobErrorResponse errorResponse=parseErrorJson(error);
                if(errorResponse!=null){
                    if(errorResponse.code==101){
                        mView.showError("用户名或密码错误!");
                    }
                    else{
                        mView.showError("服务器异常!");
                    }
                }
                else {
                    mView.showError("服务器异常!");
                }
            }
        } else if (e instanceof ConnectException) {
            mView.showError("网络断开,请检查网络!");
        } else if (e instanceof SocketTimeoutException) {
            mView.showError("网络连接超时!");
        } else {
            mView.showError("发生未知错误" + e.getMessage());
        }
    }



    private BmobErrorResponse parseErrorJson(String json){
        if(TextUtils.isEmpty(json)){
            return null;
        }
        if(json.length()<3){
            return null;
        }
        json=json.substring(1,json.length()-1);
        String[]strArr=json.split("=");
        if (strArr.length<=1){
            return null;
        }
        json=strArr[1];
        try {
            BmobErrorResponse errorResponse=new BmobErrorResponse();
            JSONObject jsonObject=new JSONObject(json);
            errorResponse.code=jsonObject.getInt("code");
            errorResponse.error=jsonObject.getString("error");
            return errorResponse;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}