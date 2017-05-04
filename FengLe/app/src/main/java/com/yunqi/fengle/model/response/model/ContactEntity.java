package com.yunqi.fengle.model.response.model;

/**
 * @Author: Huangweicai
 * @date 2017-05-04 09:49
 * @Description:(这里用一句话描述这个类的作用)
 */

public class ContactEntity {


    /**
     * id : 1
     * custom_code : 105070004
     * custom_name : 江西信丰黎民农化服务部-张黎明
     * linkman_name : xingming
     * linkman_dept : bumen
     * linkman_post : zhiwu
     * create_time : 2017-05-04 09:46:32
     */

    private int id;
    private String custom_code;
    private String custom_name;
    private String linkman_name;
    private String linkman_dept;
    private String linkman_post;
    private String create_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
