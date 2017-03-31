package com.yunqi.fengle.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 发货单申请
 * @author ghcui
 * @time 2017/2/15
 */
public class InvoiceApply implements Serializable{
    public int id;
    public String userid;
    public String order_code;
    public String client_name;
    public String create_time;
    public int status;
    public String remark;

    public List<DeliveryDetail> detail;

    public InvoiceApply() {
    }
}
