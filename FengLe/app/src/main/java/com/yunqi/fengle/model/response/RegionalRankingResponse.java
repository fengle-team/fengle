package com.yunqi.fengle.model.response;

/**
 * @Author: Huangweicai
 * @date 2017-04-15 11:19
 * @Description:大区排名
 */

public class RegionalRankingResponse {

    /**
     * area_code : 0502
     * fh_amount_sum : 1000
     * huikuan_amount_sum : 3000
     * goal_num : 2000
     * completion_rate : null
     * rank : 1
     * area_info : {"id":4,"name":"安徽区","area_code":"0502"}
     */

    private String area_code;
    private int fh_amount_sum;
    private int huikuan_amount_sum;
    private int goal_num;
    private Object completion_rate;
    private int rank;
    /**
     * id : 4
     * name : 安徽区
     * area_code : 0502
     */

    private AreaInfoEntity area_info;

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public int getFh_amount_sum() {
        return fh_amount_sum;
    }

    public void setFh_amount_sum(int fh_amount_sum) {
        this.fh_amount_sum = fh_amount_sum;
    }

    public int getHuikuan_amount_sum() {
        return huikuan_amount_sum;
    }

    public void setHuikuan_amount_sum(int huikuan_amount_sum) {
        this.huikuan_amount_sum = huikuan_amount_sum;
    }

    public int getGoal_num() {
        return goal_num;
    }

    public void setGoal_num(int goal_num) {
        this.goal_num = goal_num;
    }

    public Object getCompletion_rate() {
        return completion_rate;
    }

    public void setCompletion_rate(Object completion_rate) {
        this.completion_rate = completion_rate;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public AreaInfoEntity getArea_info() {
        return area_info;
    }

    public void setArea_info(AreaInfoEntity area_info) {
        this.area_info = area_info;
    }

    public static class AreaInfoEntity {
        private int id;
        private String name;
        private String area_code;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getArea_code() {
            return area_code;
        }

        public void setArea_code(String area_code) {
            this.area_code = area_code;
        }
    }
}
