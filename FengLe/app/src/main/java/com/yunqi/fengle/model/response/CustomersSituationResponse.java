package com.yunqi.fengle.model.response;

/**
 * @Author: Huangweicai
 * @date 2017-03-26 11:20
 * @Description:(这里用一句话描述这个类的作用)
 */

public class CustomersSituationResponse {


    /**
     * id : 16
     * userid : 13
     * client_name : 客户
     * start_time : 2017-03-25 00:00:00
     * end_time : 2017-03-26 00:00:00
     * client_receptionist : 客户接待人
     * phone : 18502519033
     * action_type : 1
     * images : /upload/hl9tgxHpx0JNQNDQ_R_Mb7an.png
     * address : 江苏省无锡市滨湖区明芳路靠近无锡杰峰建筑有限公司
     * lat : 31.485477
     * lng : 120.353983
     * feedback : 问题
     * type : 2
     * status : 1
     * create_time : 2017-03-25 01:20:03
     * visit_plan_id : null
     */

    private int id;
    private int userid;
    private String client_name;
    private String start_time;
    private String end_time;
    private String client_receptionist;
    private String phone;
    private String action_type;
    private String images;
    private String address;
    private double lat;
    private double lng;
    private String feedback;
    private int type;
    private int status;
    private String create_time;
    private Object visit_plan_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
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

    public String getClient_receptionist() {
        return client_receptionist;
    }

    public void setClient_receptionist(String client_receptionist) {
        this.client_receptionist = client_receptionist;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAction_type() {
        return action_type;
    }

    public void setAction_type(String action_type) {
        this.action_type = action_type;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public Object getVisit_plan_id() {
        return visit_plan_id;
    }

    public void setVisit_plan_id(Object visit_plan_id) {
        this.visit_plan_id = visit_plan_id;
    }
}
