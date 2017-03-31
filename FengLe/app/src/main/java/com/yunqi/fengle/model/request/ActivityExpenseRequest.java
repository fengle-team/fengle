package com.yunqi.fengle.model.request;

/**
 * @Author: Huangweicai
 * @date 2017-03-16 19:48
 * @Description: {@link com.yunqi.fengle.ui.activity.ActivityExpenseActivity}
 */

public class ActivityExpenseRequest {
    private String userid;//用户ID
    private String add_time;//填报时间
    private String action_time;//活动时间
    private String client_name;//客户名称
    private String reply_amount;//批复费用
    private String reimburse_amount;//报销费用
    private String action_type;//活动类型
    private String invoice_type;//发票类型
    private String reimburse_type;//报账类型

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getAction_time() {
        return action_time;
    }

    public void setAction_time(String action_time) {
        this.action_time = action_time;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getReply_amount() {
        return reply_amount;
    }

    public void setReply_amount(String reply_amount) {
        this.reply_amount = reply_amount;
    }

    public String getReimburse_amount() {
        return reimburse_amount;
    }

    public void setReimburse_amount(String reimburse_amount) {
        this.reimburse_amount = reimburse_amount;
    }

    public String getAction_type() {
        return action_type;
    }

    public void setAction_type(String action_type) {
        this.action_type = action_type;
    }

    public String getInvoice_type() {
        return invoice_type;
    }

    public void setInvoice_type(String invoice_type) {
        this.invoice_type = invoice_type;
    }

    public String getReimburse_type() {
        return reimburse_type;
    }

    public void setReimburse_type(String reimburse_type) {
        this.reimburse_type = reimburse_type;
    }
}
