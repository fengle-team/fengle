package com.yunqi.fengle.model.response;

import java.io.Serializable;

/**
 * @Author: Huangweicai
 * @date 2017-03-16 17:38
 * @Description:(这里用一句话描述这个类的作用)
 */

public class CustomersResponse implements Serializable{

//    类型 1=潜在客户 2=意向客户局 3=成交客户 4=流失客户

    public final static int TYPE_POTENTIAL = 1;//潜在客户
    public final static int TYPE_INTENTION = 2;//意向客户
    public final static int TYPE_DEAL = 3;//成交客户
    public final static int TYPE_LOSS = 4;//流失客户

    /**
     * id : 1
     * name : 邰广银
     * company_name : 科大讯飞公司
     * phone : 1865609521
     * position : 技术经理
     * type : 1
     * userid : 13
     */

    private int id;
    private String name;
    private String company_name;
    private String phone;
    private String position;
    private int type;
    private int userid;

    private boolean isChecked = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getLev() {
        switch (type) {
            case TYPE_POTENTIAL:
                return "潜在客户";
            case TYPE_INTENTION:
                return "意向客户";
            case TYPE_DEAL:
                return "成交客户";
            case TYPE_LOSS:
                return "流失客户";

        }
        return "";
    }
}
