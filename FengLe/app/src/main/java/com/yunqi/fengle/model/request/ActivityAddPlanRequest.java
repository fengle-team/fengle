package com.yunqi.fengle.model.request;

import com.yunqi.fengle.ui.activity.ActivityNewPlanActivity;

/**
 * @Author: Huangweicai
 * @date 2017-02-21 13:26
 * @Description: 添加活动 {@link ActivityNewPlanActivity}
 */

public class ActivityAddPlanRequest {

    private String userid;
    private String region;//区域
    private String start_time;//开始时间
    private String end_time;//结束时间
    private String client_name;//客户名称
    private String shop_name;//门店名称
    private String action_type;//活动类型
    private String apply_reason;//申请原因
    private String action_plan;//活动计划
    private String apply_budget;//费用预算
    private String baoxiao_budget;//报销方式
    private String baoxiao_type;//报销类型
    private String other_support;//其他支持
    private String apply_name;//申请人姓名
    private String remark;//说明


    public static ActivityAddPlanRequest getTest() {
        ActivityAddPlanRequest request = new ActivityAddPlanRequest();
        request.setUserid("13");
        request.setRegion("测试区域");
        request.setStart_time("");
        request.setEnd_time("");
        request.setClient_name("测试客户名称");
        request.setShop_name("测试门店名称");
        request.setAction_type("测试活动类型");
        request.setApply_reason("测试申请原因");
        request.setAction_plan("测试活动计划");
        request.setApply_budget("测试申请预算");
        request.setBaoxiao_budget("测试报销方式");
        request.setBaoxiao_type("测试报销类型");
        request.setOther_support("测试其他支持");
        request.setApply_name("申请人姓名");
        request.setRemark("说明");
        return request;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public String getAction_type() {
        return action_type;
    }

    public void setAction_type(String action_type) {
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

    public String getApply_budget() {
        return apply_budget;
    }

    public void setApply_budget(String apply_budget) {
        this.apply_budget = apply_budget;
    }


    public String getBaoxiao_budget() {
        return baoxiao_budget;
    }

    public void setBaoxiao_budget(String baoxiao_budget) {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
