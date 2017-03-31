package com.yunqi.fengle.model.request;

import java.util.ArrayList;

/**
 * @Author: Huangweicai
 * @date 2017-02-22 21:06
 * @Description: 添加维护 {@link com.yunqi.fengle.ui.activity.AddMaintainActivity}
 */

public class AddMaintainRequest {

    private String userid;
    private String client_name;//客户民称
    private String start_time;
    private String end_time;
    private String client_receptionist;//客户接代员
    private String phone;
    private ArrayList images;//图片
    private String action_type;//活动类型
    private String address;//地址
    private String lat;//纬度
    private String lng;//经度
    private String feedback;//问题反馈
    private String type;//类型 1=一般问题 2=重要问题

    public static String TYPE_NORMAL = "1";//一般
    public static String TYPE_IMPORTANT = "2";//重要

    public static ArrayList<String> getTestImg() {
        ArrayList<String> img = new ArrayList<>();
        img.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487779845970&di=d967206ce6fce5325b93f4d0b7c2e823&imgtype=0&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Ff9dcd100baa1cd11dd1855cebd12c8fcc2ce2db5.jpg");
        return img;
    }

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

    public ArrayList getImages() {
        return images;
    }

    public void setImages(ArrayList images) {
        this.images = images;
    }

    public String getAction_type() {
        return action_type;
    }

    public void setAction_type(String action_type) {
        this.action_type = action_type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
