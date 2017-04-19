package com.yunqi.fengle.model.response.model;

/**
 * @Author: Huangweicai
 * @date 2017-04-19 10:14
 * @Description:(这里用一句话描述这个类的作用)
 * /**
 * hk_id : 91
 * client_code : 0106010022
 * huikuan_time : 2017-04-18 00:00:00
 * huikuan_type : 农药款
 * client_name : （原药）杭州中隆化工科技有限公司——江媛
 * huikuan_amount : 1111
 * remark : 11111
 * images : null
 * create_time : 2017-04-18 21:41:44
 * userid : 13
 * status : 2
 * person_code : 2003211
 * huikuan_name : 1111
 * pay_type : 现金结算
 * huikuan_type_code : 1
 * pay_type_code : 1
 * confirm_amount : 1111
 * bank_caption_code : 10020101
 */

public class HuikuanInfoEntity {


    private LastHuikuanEntity last_huikuan;
    /**
     * last_huikuan : {"hk_id":91,"client_code":"0106010022","huikuan_time":"2017-04-18 00:00:00","huikuan_type":"农药款","client_name":"（原药）杭州中隆化工科技有限公司\u2014\u2014江媛","huikuan_amount":1111,"remark":"11111","images":null,"create_time":"2017-04-18 21:41:44","userid":13,"status":2,"person_code":"2003211","huikuan_name":"1111","pay_type":"现金结算","huikuan_type_code":"1","pay_type_code":"1","confirm_amount":1111,"bank_caption_code":"10020101"}
     * huikuan_count : 7
     */

    private int huikuan_count;

    public LastHuikuanEntity getLast_huikuan() {
        return last_huikuan;
    }

    public void setLast_huikuan(LastHuikuanEntity last_huikuan) {
        this.last_huikuan = last_huikuan;
    }

    public int getHuikuan_count() {
        return huikuan_count;
    }

    public void setHuikuan_count(int huikuan_count) {
        this.huikuan_count = huikuan_count;
    }

    public static class LastHuikuanEntity {
        private int hk_id;
        private String client_code;
        private String huikuan_time;
        private String huikuan_type;
        private String client_name;
        private int huikuan_amount;
        private String remark;
        private Object images;
        private String create_time;
        private int userid;
        private int status;
        private String person_code;
        private String huikuan_name;
        private String pay_type;
        private String huikuan_type_code;
        private String pay_type_code;
        private int confirm_amount;
        private String bank_caption_code;

        public int getHk_id() {
            return hk_id;
        }

        public void setHk_id(int hk_id) {
            this.hk_id = hk_id;
        }

        public String getClient_code() {
            return client_code;
        }

        public void setClient_code(String client_code) {
            this.client_code = client_code;
        }

        public String getHuikuan_time() {
            return huikuan_time;
        }

        public void setHuikuan_time(String huikuan_time) {
            this.huikuan_time = huikuan_time;
        }

        public String getHuikuan_type() {
            return huikuan_type;
        }

        public void setHuikuan_type(String huikuan_type) {
            this.huikuan_type = huikuan_type;
        }

        public String getClient_name() {
            return client_name;
        }

        public void setClient_name(String client_name) {
            this.client_name = client_name;
        }

        public int getHuikuan_amount() {
            return huikuan_amount;
        }

        public void setHuikuan_amount(int huikuan_amount) {
            this.huikuan_amount = huikuan_amount;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public Object getImages() {
            return images;
        }

        public void setImages(Object images) {
            this.images = images;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getPerson_code() {
            return person_code;
        }

        public void setPerson_code(String person_code) {
            this.person_code = person_code;
        }

        public String getHuikuan_name() {
            return huikuan_name;
        }

        public void setHuikuan_name(String huikuan_name) {
            this.huikuan_name = huikuan_name;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getHuikuan_type_code() {
            return huikuan_type_code;
        }

        public void setHuikuan_type_code(String huikuan_type_code) {
            this.huikuan_type_code = huikuan_type_code;
        }

        public String getPay_type_code() {
            return pay_type_code;
        }

        public void setPay_type_code(String pay_type_code) {
            this.pay_type_code = pay_type_code;
        }

        public int getConfirm_amount() {
            return confirm_amount;
        }

        public void setConfirm_amount(int confirm_amount) {
            this.confirm_amount = confirm_amount;
        }

        public String getBank_caption_code() {
            return bank_caption_code;
        }

        public void setBank_caption_code(String bank_caption_code) {
            this.bank_caption_code = bank_caption_code;
        }
    }
}
