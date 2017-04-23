package com.yunqi.fengle.model.bean;

import java.io.Serializable;

/**
 * @author ghcui
 * @time 2017/2/15
 */
public class Goods implements Serializable{
    public int goods_id;
    public String goods_code;
    public String goods_name;
    public String goods_standard;
    public double goods_num;
    public float goods_units_num;
    public float goods_price;
    public String goods_warehouse;
    public int goods_plan_left;
    public int goods_plan;
    public String freight;
    public String warehouse_code;


    public Goods(){
    }
}
