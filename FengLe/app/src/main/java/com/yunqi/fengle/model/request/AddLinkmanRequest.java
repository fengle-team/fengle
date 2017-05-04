package com.yunqi.fengle.model.request;

/**
 * @Author: Huangweicai
 * @date 2017-05-04 09:21
 * @Description:(这里用一句话描述这个类的作用)
 */

public class AddLinkmanRequest {

    private String custom_code;
    private String custom_name;
    private String linkman_name;
    private String linkman_dept;
    private String linkman_post;

    public String getCustom_code() {
        return custom_code;
    }

    public void setCustom_code(String custom_code) {
        this.custom_code = custom_code;
    }

    public String getCustom_name() {
        return custom_name;
    }

    public void setCustom_name(String custom_name) {
        this.custom_name = custom_name;
    }

    public String getLinkman_name() {
        return linkman_name;
    }

    public void setLinkman_name(String linkman_name) {
        this.linkman_name = linkman_name;
    }

    public String getLinkman_dept() {
        return linkman_dept;
    }

    public void setLinkman_dept(String linkman_dept) {
        this.linkman_dept = linkman_dept;
    }

    public String getLinkman_post() {
        return linkman_post;
    }

    public void setLinkman_post(String linkman_post) {
        this.linkman_post = linkman_post;
    }
}
