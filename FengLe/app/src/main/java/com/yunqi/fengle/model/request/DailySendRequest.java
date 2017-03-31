package com.yunqi.fengle.model.request;

/**
 * @Author: Huangweicai
 * @date 2017-03-09 14:16
 * @Description: {@link com.yunqi.fengle.ui.activity.DailySendActivity}
 */

public class DailySendRequest {

    private String userid;
    private String daily;//日志内容
    private String address;//地址
    private String lat;//经度
    private String lng;//纬度

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDaily() {
        return daily;
    }

    public void setDaily(String daily) {
        this.daily = daily;
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
}
