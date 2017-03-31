package com.yunqi.fengle.model.prefrence;

/**
 * @Author: Huangweicai
 * @date 2017-02-22 10:50
 * @Description: 签到 签退 {@link com.yunqi.fengle.ui.activity.MoveAttendanceSignInActivity}
 */

public class SignAddRequest {
    private String userid;
    private String sign_type;//签到类型 1=签到 2=签退
    private String sign_address;//签到签退地址
    private String lng;//经度
    private String lat;//纬度
    private String remark;//备注

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getSign_address() {
        return sign_address;
    }

    public void setSign_address(String sign_address) {
        this.sign_address = sign_address;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
