package com.yunqi.fengle.model.bean;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 16:49
 * @Description: {@link com.yunqi.fengle.ui.adapter.ActivityManagerAdapter}
 */

public class ActivityMgrBean {
    private String text;
    private int icon;

    public ActivityMgrBean(int icon,String text) {
        this.icon = icon;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
