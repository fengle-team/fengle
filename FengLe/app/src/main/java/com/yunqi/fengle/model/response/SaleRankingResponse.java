package com.yunqi.fengle.model.response;

/**
 * @Author: Huangweicai
 * @date 2017-04-15 11:19
 * @Description:业务员排名
 */

public class SaleRankingResponse {
    private String person_code;
    private int fh_amount_sum;
    private int huikuan_amount_sum;
    private int goal_num;
    private int completion_rate;
    private int rank;
    /**
     * id : 13
     * account : gytai
     * password : 123456
     * role_id : 5
     * u8_id : null
     * u8_dept_code : 10001
     * u8_name : null
     * mobile : 18656095251
     * region_id : 0502
     * cwhouses : null
     * is_disable : 0
     * is_delete : 0
     * client_id : null
     * leader_id : 14
     * create_time : 2017-02-16 16:10:36
     * position : 业务员
     * real_name : 邰广银
     * user_code : 2003211
     */

    private UserInfoEntity user_info;

    public String getPerson_code() {
        return person_code;
    }

    public void setPerson_code(String person_code) {
        this.person_code = person_code;
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

    public int getCompletion_rate() {
        return completion_rate;
    }

    public void setCompletion_rate(int completion_rate) {
        this.completion_rate = completion_rate;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public UserInfoEntity getUser_info() {
        return user_info;
    }

    public void setUser_info(UserInfoEntity user_info) {
        this.user_info = user_info;
    }

    public static class UserInfoEntity {
        private int id;
        private String account;
        private String password;
        private int role_id;
        private Object u8_id;
        private String u8_dept_code;
        private Object u8_name;
        private String mobile;
        private String region_id;
        private Object cwhouses;
        private int is_disable;
        private int is_delete;
        private Object client_id;
        private int leader_id;
        private String create_time;
        private String position;
        private String real_name;
        private String user_code;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getRole_id() {
            return role_id;
        }

        public void setRole_id(int role_id) {
            this.role_id = role_id;
        }

        public Object getU8_id() {
            return u8_id;
        }

        public void setU8_id(Object u8_id) {
            this.u8_id = u8_id;
        }

        public String getU8_dept_code() {
            return u8_dept_code;
        }

        public void setU8_dept_code(String u8_dept_code) {
            this.u8_dept_code = u8_dept_code;
        }

        public Object getU8_name() {
            return u8_name;
        }

        public void setU8_name(Object u8_name) {
            this.u8_name = u8_name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getRegion_id() {
            return region_id;
        }

        public void setRegion_id(String region_id) {
            this.region_id = region_id;
        }

        public Object getCwhouses() {
            return cwhouses;
        }

        public void setCwhouses(Object cwhouses) {
            this.cwhouses = cwhouses;
        }

        public int getIs_disable() {
            return is_disable;
        }

        public void setIs_disable(int is_disable) {
            this.is_disable = is_disable;
        }

        public int getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(int is_delete) {
            this.is_delete = is_delete;
        }

        public Object getClient_id() {
            return client_id;
        }

        public void setClient_id(Object client_id) {
            this.client_id = client_id;
        }

        public int getLeader_id() {
            return leader_id;
        }

        public void setLeader_id(int leader_id) {
            this.leader_id = leader_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getUser_code() {
            return user_code;
        }

        public void setUser_code(String user_code) {
            this.user_code = user_code;
        }
    }

}
