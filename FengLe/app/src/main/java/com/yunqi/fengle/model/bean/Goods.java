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
    public int goods_units_num;
    public float goods_price;
    public String goods_warehouse;
    public String goods_plan;
    public String phone;
    public float freight;
    public String warehouse_code;


    public Goods(){
    }
}
