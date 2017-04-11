package com.yunqi.fengle.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 计划调剂申请
 * @author ghcui
 * @time 2017/2/15
 */
public class PlanAdjustmentApply implements Serializable{



    public List<PlanAdjustmentDetail> detail;
    public int id;
    public String from_area_code;
    public String from_area_name;
    public String to_area_code;
    public String to_area_name;
    public String create_time;
    public int status;
    public int userid;
    public String order_code;
    public String remark;

    public PlanAdjustmentApply() {
    }
}
