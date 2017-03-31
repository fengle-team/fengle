package com.yunqi.fengle.model.request;

/**
 * @Author: Huangweicai
 * @date 2017-03-22 18:03
 * @Description:(这里用一句话描述这个类的作用)
 */

public class VisitingAddRequest {

    /**
     * userid : 13
     * client_name : 客户名称
     * plan_time : 2017-01-03 11:22:40
     * responsible_name : 责任人姓名
     * reason : 理由
     */

    private String userid;
    private String client_name;//客户名称
    private String plan_time;//计划时间
    private String responsible_name;//责任人姓名
    private String reason;//理由
    private String status;//状态 1未完成 2已完成


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getPlan_time() {
        return plan_time;
    }

    public void setPlan_time(String plan_time) {
        this.plan_time = plan_time;
    }

    public String getResponsible_name() {
        return responsible_name;
    }

    public void setResponsible_name(String responsible_name) {
        this.responsible_name = responsible_name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
