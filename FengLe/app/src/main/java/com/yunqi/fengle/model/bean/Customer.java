package com.yunqi.fengle.model.bean;

import java.io.Serializable;

/**
 * @author ghcui
 * @time 2017/2/15
 */
public class Customer implements Serializable{
    public String id;
    public String company_name;
    public String name;
    public String phone;
    public String position;
    public String custom_code;
    public int type;
    public String userid;

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public Customer() {

    }
}
