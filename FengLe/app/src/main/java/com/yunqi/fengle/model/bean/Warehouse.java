package com.yunqi.fengle.model.bean;

import java.io.Serializable;

/**
 * @author ghcui
 * @time 2017/2/15
 */
public class Warehouse implements Serializable{
    public int ck_id;
    public String name;
    public String address;
    public String warehouse_code;
    public String area_code;



    public Warehouse(int ck_id, String name,String address){
        this.ck_id=ck_id;
        this.name=name;
        this.address=address;
    }
}
