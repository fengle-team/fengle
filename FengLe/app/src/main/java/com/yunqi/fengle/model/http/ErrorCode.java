package com.yunqi.fengle.model.http;



/**
 * @author ghcui
 * @time 2017/1/11
 */
public class ErrorCode {
    //登录相关错误码
    public static final int INCORRECT_USERNAME_OR_PASSWORD=101;//用户名或密码错误

    //注册相关错误码
    public static final int USERNAME_HAS_BEEN_USED=202;//用户名已被注册

    public static final int EMAIL_HAS_BEEN_USED=203;//邮箱已被注册

    public static final int PHONE_HAS_BEEN_USED=209;//手机号已被注册
}
