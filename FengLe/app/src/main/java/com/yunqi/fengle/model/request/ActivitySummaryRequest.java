package com.yunqi.fengle.model.request;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Huangweicai
 * @date 2017-03-23 22:12
 * @Description:(这里用一句话描述这个类的作用)
 */

public class ActivitySummaryRequest {

    private String userid;
    private String region;//区域
    private String start_time;//开始时间
    private String end_time;//结束时间
    private String client_name;//客户名称
    private String shop_name;//门店名称
    private String action_type;//活动类型
    private String summary;//活动总结
    private String client_id;//客户ID
    private ArrayList<String> image_urls;//图
    private String action_submit;//填报人

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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public ArrayList<String> getImage_urls() {
        return image_urls;
    }

    public void setImage_urls(ArrayList<String> image_urls) {
        this.image_urls = image_urls;
    }

    public String getAction_submit() {
        return action_submit;
    }

    public void setAction_submit(String action_submit) {
        this.action_submit = action_submit;
    }
}
