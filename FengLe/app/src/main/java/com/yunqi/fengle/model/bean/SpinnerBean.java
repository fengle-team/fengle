package com.yunqi.fengle.model.bean;

import android.text.TextUtils;

import com.yunqi.fengle.model.request.TypeRequest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Huangweicai
 * @date 2017-03-16 21:10
 * @Description:(这里用一句话描述这个类的作用)
 */
public class SpinnerBean implements Serializable{

    private String key;
    private String value;

    private List<SpinnerBean> dataList;
    private List<Map<String,String>> strSpnnerMap;

    public void addSpinner(String key,String value) {
        SpinnerBean bean = new SpinnerBean();
        bean.setKey(key);
        bean.setValue(value);
        Map<String, String> map = new HashMap<>();
        map.put(key, value);
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        dataList.add(bean);

        Map<String, String> strMap = new HashMap<>();
        if (strSpnnerMap == null) {
            strSpnnerMap = new ArrayList<>();
        }
        strMap.put("content", value);
        strSpnnerMap.add(strMap);

    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(key);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<SpinnerBean> getDataList() {
        return dataList;
    }

    public List<Map<String, String>> getStrSpnnerMap() {
        return strSpnnerMap;
    }

    public void format(List<TypeRequest> requestList) {
        if (requestList == null || requestList.size() == 0) {
            return;
        }
        for (TypeRequest bean : requestList) {
            addSpinner(bean.getId(),bean.getName());
        }
    }

}
