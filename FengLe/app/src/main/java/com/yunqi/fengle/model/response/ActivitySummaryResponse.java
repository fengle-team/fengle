package com.yunqi.fengle.model.response;

import java.io.Serializable;

/**
 * @Author: Huangweicai
 * @date 2017-04-20 21:46
 * @Description:(这里用一句话描述这个类的作用)
 */

public class ActivitySummaryResponse implements Serializable{


    /**
     * id : 6
     * region : 西北大区
     * start_time : 2017-04-05 00:00:00
     * end_time : 2017-04-07 00:00:00
     * client_name : （原药）合肥市个体户-王小欢
     * shop_name : jdjdjd
     * client_id : 16414
     * action_type : 2
     * summary : ndjfjfjsnscnn
     * userid : 13
     * action_submit : jdjfnc
     * create_time : 2017-04-05 22:58:23
     */

    private int id;
    private String region;
    private String start_time;
    private String end_time;
    private String client_name;
    private String shop_name;
    private int client_id;
    private String action_type;
    private String summary;
    private String image_urls;
    private int userid;
    private String action_submit;
    private String create_time;

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

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
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

    public String getImage_urls() {
        return image_urls;
    }

    public void setImage_urls(String image_urls) {
        this.image_urls = image_urls;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getAction_submit() {
        return action_submit;
    }

    public void setAction_submit(String action_submit) {
        this.action_submit = action_submit;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
