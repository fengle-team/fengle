package com.yunqi.fengle.model.response;

/**
 * @Author: Huangweicai
 * @date 2017-03-13 22:29
 * @Description:公告
 */
public class NoticeResponse {

    /**
     * id : 2
     * type : 1
     * create_time : 2017-03-08 20:55:30
     * creator_id : admin
     * msg_content : 这是你的消息
     * post_to : 13
     */

    private int id;
    private int type;
    private String create_time;
    private String creator_id;
    private String msg_content;
    private String post_to;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(String creator_id) {
        this.creator_id = creator_id;
    }

    public String getMsg_content() {
        return msg_content;
    }

    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
    }

    public String getPost_to() {
        return post_to;
    }

    public void setPost_to(String post_to) {
        this.post_to = post_to;
    }
}
