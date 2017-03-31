package com.yunqi.fengle.model.response;

import com.yunqi.fengle.util.DateUtil;

import java.io.Serializable;

/**
 * @Author: Huangweicai
 * @date 2017-03-13 22:50
 * @Description: {@link com.yunqi.fengle.ui.activity.VisitingPlanActivity}
 */

public class VisitingPlanResponse implements Serializable{

    /**
     * id : 3
     * client_name : 客户名称
     * plan_time : 2017-04-03 11:22:40
     * start_time : null
     * end_time : null
     * responsible_name : 责任人姓名
     * create_time : 2017-03-09 19:51:55
     * userid : 13
     * reason : 理由
     * status : 1
     */

    public static final int STATUS_CONFIRM_NO = 1;//待确认
    public static final int STATUS_CONFIRM_YES = 2;//已确认

    private int id;
    private String client_name;//客户名称
    private String plan_time;//计划时间
    private Object start_time;
    private Object end_time;
    private String responsible_name;//责任人姓名
    private String create_time;
    private int userid;//用户id
    private String reason;//理由
    private int status;//状态

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getPlan_time() {
        return plan_time;
    }

    public void setPlan_time(String plan_time) {
//        this.plan_time = DateUtil.formatB(plan_time);
        this.plan_time = plan_time;
    }

    public Object getStart_time() {
        return start_time;
    }

    public void setStart_time(Object start_time) {
        this.start_time = start_time;
    }

    public Object getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Object end_time) {
        this.end_time = end_time;
    }

    public String getResponsible_name() {
        return responsible_name;
    }

    public void setResponsible_name(String responsible_name) {
        this.responsible_name = responsible_name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusStr() {
        if (this.status == 1) {
            return "待确认";
        } else if (this.status == 2) {
            return "已确认";
        } else {
            return "未知状态";
        }
    }
}
