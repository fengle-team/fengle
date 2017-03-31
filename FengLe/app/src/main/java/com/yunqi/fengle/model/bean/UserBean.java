package com.yunqi.fengle.model.bean;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public class UserBean extends RealmObject implements Serializable{
    public final static String ROLE_YWY="code_ywy";//业务员
    public final static String ROLE_ZXJL="code_zxjl";//中心经理
    public final static String ROLE_DQJL="code_dqjl";//大区经理
    public String id;
    public String account;
    public String password;
    public String user_code;
    public String real_name;//名字
    public String mobile;
    public String position = "";//职位
    public String role_code;


}
