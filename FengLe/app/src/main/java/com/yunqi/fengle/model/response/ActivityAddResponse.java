package com.yunqi.fengle.model.response;

/**
 * @Author: Huangweicai
 * @date 2017-02-20 22:05
 * @Description:添加活动
 */

public class ActivityAddResponse {

    private int id;
    private String region;//区域
    private String start_time;//开始时间
    private String end_time;//结束时间
    private String client_name;//客户名称
    private String shop_name;//门店名称
    private int action_type;//活动类型
    private String apply_reason;//申请原因
    private String action_plan;//活动计划
    private int apply_budget;//申请预算
    private int check_budget;//批复预算
    private int baoxiao_budget;//报销方式
    private String baoxiao_type;//报销类型
    private String other_support;//其他支持
    private String apply_name;//申请人姓名
    private int userid;
    private String create_time;
    private String update_time;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public int getAction_type() {
        return action_type;
    }

    public void setAction_type(int action_type) {
        this.action_type = action_type;
    }

    public String getApply_reason() {
        return apply_reason;
    }

    public void setApply_reason(String apply_reason) {
        this.apply_reason = apply_reason;
    }

    public String getAction_plan() {
        return action_plan;
    }

    public void setAction_plan(String action_plan) {
        this.action_plan = action_plan;
    }

    public int getApply_budget() {
        return apply_budget;
    }

    public void setApply_budget(int apply_budget) {
        this.apply_budget = apply_budget;
    }

    public int getCheck_budget() {
        return check_budget;
    }

    public void setCheck_budget(int check_budget) {
        this.check_budget = check_budget;
    }

    public int getBaoxiao_budget() {
        return baoxiao_budget;
    }

    public void setBaoxiao_budget(int baoxiao_budget) {
        this.baoxiao_budget = baoxiao_budget;
    }

    public String getBaoxiao_type() {
        return baoxiao_type;
    }

    public void setBaoxiao_type(String baoxiao_type) {
        this.baoxiao_type = baoxiao_type;
    }

    public String getOther_support() {
        return other_support;
    }

    public void setOther_support(String other_support) {
        this.other_support = other_support;
    }

    public String getApply_name() {
        return apply_name;
    }

    public void setApply_name(String apply_name) {
        this.apply_name = apply_name;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
