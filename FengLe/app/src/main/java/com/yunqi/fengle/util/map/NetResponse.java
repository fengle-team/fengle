package com.yunqi.fengle.util.map;

/**
 * @Author: Huangweicai
 * @date 2017-02-22 14:07
 * @Description:(这里用一句话描述这个类的作用)
 */

public class NetResponse {
    public int code;// 0:成功
    public String msg;
    public Object result;

    public NetResponse() {
    }

    public NetResponse(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public NetResponse(int code, String msg, Object result) {
        super();
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
