package com.yunqi.fengle.model.bean;

/**
 * @author ghcui
 * @time 2017/2/15
 */
public class CustomerAnalysis {
    public String name;
    public double amount;
    public int ranking;
    public int lastShipment;
    public int lift;

    public CustomerAnalysis(String name, double amount, int ranking, int lastShipment, int lift) {
        this.name = name;
        this.amount = amount;
        this.ranking = ranking;
        this.lastShipment = lastShipment;
        this.lift = lift;
    }

    public CustomerAnalysis(){
    }
}
