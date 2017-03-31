package com.yunqi.fengle.ui.adapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 00:31
 * @Description:(这里用一句话描述这个类的作用)
 */

public class DailyMultiItem implements MultiItemEntity {

    private int type;

    public static final int TYPE_DAILY = 0x01;//日历
    public static final int TYPE_CONTENT = 0x02;//日志

    public DailyMultiItem(int type) {
        this.type = type;
    }

    public static List<DailyMultiItem> getMultiItemList() {
        DailyMultiItem daily = new DailyMultiItem(TYPE_DAILY);
        DailyMultiItem content = new DailyMultiItem(TYPE_CONTENT);
        List<DailyMultiItem> itemList = new ArrayList<>();
        itemList.add(daily);//content
        itemList.add(content);//联系人
        return itemList;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
