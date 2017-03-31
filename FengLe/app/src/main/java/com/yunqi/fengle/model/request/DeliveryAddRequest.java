package com.yunqi.fengle.model.request;

import com.yunqi.fengle.model.bean.Goods;
import com.yunqi.fengle.model.bean.GoodsAndWarehouse;

import java.util.List;

/**
 * @Author: Huangweicai
 * @date 2017-02-22 10:50
 * @Description: 签到 签退 {@link com.yunqi.fengle.ui.activity.MoveAttendanceSignInActivity}
 */

public class DeliveryAddRequest {
    public String userid;

    public List<Goods> goods_array;

    public int status;

    public String client_id;

    public String client_name;

    public String remark;

}
