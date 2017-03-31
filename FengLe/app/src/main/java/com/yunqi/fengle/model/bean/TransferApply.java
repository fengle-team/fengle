package com.yunqi.fengle.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 调货单申请
 * @author ghcui
 * @time 2017/2/15
 */
public class TransferApply implements Serializable{
    public int id;
    public String userid;
    public String order_code;
    public String client_name_to;
    public String client_name_from;
    public String client_code_from;
    public String client_code_to;
    public String create_time;
    public int status;
    public String remark;

    public List<TransferDetail> detail;

    public TransferApply() {
    }
}
