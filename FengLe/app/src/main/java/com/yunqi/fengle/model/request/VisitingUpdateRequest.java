package com.yunqi.fengle.model.request;

/**
 * @Author: Huangweicai
 * @date 2017-03-25 15:54
 * @Description: {@link com.yunqi.fengle.ui.activity.ActivityPlanDetailActivity}
 */

public class VisitingUpdateRequest {

    private String id;
    private String status;//状态 1=未完成 2=已完成
    private String start_time;//拜访开始时间
    private String end_time;//拜访结束时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEndtime() {
        return end_time;
    }

    public void setEndtime(String endtime) {
        this.end_time = endtime;
    }
}
