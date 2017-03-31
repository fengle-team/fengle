package com.yunqi.fengle.model.response;

/**
 * @Author: Huangweicai
 * @date 2017-03-15 16:26
 * @Description:(这里用一句话描述这个类的作用)
 */

public class DailyResponse {
    /**
     * id : 11
     * userid : 13
     * daily : 日志内容11
     * create_time : 2017-03-08 22:03:02
     * address : 无锡软件园11
     * lat : 111.11
     * lng : 1222.22
     * status : 1
     * reason :
     */

    private int id;
    private int userid;
    private String daily;
    private String create_time;
    private String address;
    private double lat;
    private double lng;
    private int status;
    private String reason;

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

    public String getDaily() {
        return daily;
    }

    public void setDaily(String daily) {
        this.daily = daily;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
