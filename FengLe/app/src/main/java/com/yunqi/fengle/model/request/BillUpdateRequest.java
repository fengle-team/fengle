package com.yunqi.fengle.model.request;

import com.yunqi.fengle.model.bean.Goods;

import java.util.List;

/**
 * @Author: ghcui
 * @date 2017-02-22 10:50
 * @Description: 单据更新Request
 */

public class BillUpdateRequest {
    public int id;
    public String order_code;

    public List<Goods> goods_array;

    public int status;

}
