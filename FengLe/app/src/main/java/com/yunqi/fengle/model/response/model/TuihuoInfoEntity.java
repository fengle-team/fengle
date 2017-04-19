package com.yunqi.fengle.model.response.model;

import com.yunqi.fengle.model.response.CustomerWholeResponse;

/**
 * @Author: Huangweicai
 * @date 2017-04-19 10:17
 * @Description:(这里用一句话描述这个类的作用)
 * * id : 5
 * userid : 13
 * create_time : 2017-04-12 09:01:06
 * order_code : FLNH201704120901062886
 * status : 2
 * client_name : （原药）杭州中隆化工科技有限公司——江媛
 * remark : null
 * client_code : 0106010022
 * order_type : THSQ
 * deal_depth : 1
 */

public class TuihuoInfoEntity {

    private LastTuihuoEntity last_tuihuo;
    private int tuihuo_count;

    public LastTuihuoEntity getLast_tuihuo() {
        return last_tuihuo;
    }

    public void setLast_tuihuo(LastTuihuoEntity last_tuihuo) {
        this.last_tuihuo = last_tuihuo;
    }

    public int getTuihuo_count() {
        return tuihuo_count;
    }

    public void setTuihuo_count(int tuihuo_count) {
        this.tuihuo_count = tuihuo_count;
    }

    public static class LastTuihuoEntity {
        private int id;
        private int userid;
        private String create_time;
        private String order_code;
        private int status;
        private String client_name;
        private String remark;
        private String client_code;
        private String order_type;
        private int deal_depth;

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

        public String getCreate_time() {
            if (create_time == null) {
                create_time = "";
            }
            return create_time;
        }

        public void setCreate_time(String create_time) {
            if (create_time == null) {
                this.create_time = "";
                return;
            }
            this.create_time = create_time;
        }

        public String getOrder_code() {
            if (order_code == null) {
                order_code = "";
            }
            return order_code;
        }

        public void setOrder_code(String order_code) {
            if (order_code == null) {
                this.order_code = "";
                return;
            }
            this.order_code = order_code;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getClient_name() {
            return client_name;
        }

        public void setClient_name(String client_name) {
            this.client_name = client_name;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getClient_code() {
            return client_code;
        }

        public void setClient_code(String client_code) {
            this.client_code = client_code;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }

        public int getDeal_depth() {
            return deal_depth;
        }

        public void setDeal_depth(int deal_depth) {
            this.deal_depth = deal_depth;
        }
    }
    
}
