package com.yunqi.fengle.util.map;

/**
 * @Author: Huangweicai
 * @date 2017-02-22 14:09
 * @Description:(这里用一句话描述这个类的作用)
 */

public class LocationModel {

    private double latitude;//经度
    private double longitude;//纬度
    private String address;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
