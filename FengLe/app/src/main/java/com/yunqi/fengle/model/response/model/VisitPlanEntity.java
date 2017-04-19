package com.yunqi.fengle.model.response.model;

import com.yunqi.fengle.model.response.CustomerWholeResponse;

/**
 * @Author: Huangweicai
 * @date 2017-04-19 10:16
 * @Description:(这里用一句话描述这个类的作用)
 * /**
 * id : 22
 * client_name : （原药）杭州中隆化工科技有限公司——江媛
 * plan_time : 2017-04-18 00:00:00
 * start_time : null
 * end_time : null
 * responsible_name : qqqq
 * create_time : 2017-04-18 21:56:31
 * userid : 13
 * reason : qqqq
 * status : 1
 * client_code : 0106010022
 */

public class VisitPlanEntity {

    private LastVisitePlanEntity last_visite_plan;
    private int visite_plan_count;

    public LastVisitePlanEntity getLast_visite_plan() {
        return last_visite_plan;
    }

    public void setLast_visite_plan(LastVisitePlanEntity last_visite_plan) {
        this.last_visite_plan = last_visite_plan;
    }

    public int getVisite_plan_count() {
        return visite_plan_count;
    }

    public void setVisite_plan_count(int visite_plan_count) {
        this.visite_plan_count = visite_plan_count;
    }

    public static class LastVisitePlanEntity {
        private int id;
        private String client_name;
        private String plan_time;
        private Object start_time;
        private Object end_time;
        private String responsible_name;
        private String create_time;
        private int userid;
        private String reason;
        private int status;
        private String client_code;

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

        public String getClient_code() {
            return client_code;
        }

        public void setClient_code(String client_code) {
            this.client_code = client_code;
        }
    }
    
}
