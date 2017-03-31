package com.yunqi.fengle.ui.adapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 00:31
 * @Description:(这里用一句话描述这个类的作用)
 */

public class CustomerWholeMultiItem implements MultiItemEntity {

    private int type;

    public static final int TYPE_CONTENT = 0x01;//title
    public static final int TYPE_CONTACT = 0x02;//联系人
    public static final int TYPE_VISIT = 0x03;//拜访
    public static final int TYPE_SALE = 0x04;//销售订单
    public static final int TYPE_RETURN = 0x05;//回款
    public static final int TYPE_REFUND = 0x06;//退款
    public static final int TYPE_INVOICE = 0x07;//开票
    public static final int TYPE_EXPENS = 0x08;//费用报销
    public static final int TYPE_OTHER = 0x09;//其他

    public CustomerWholeMultiItem(int type) {
        this.type = type;
    }

    public static List<CustomerWholeMultiItem> getMultiItemList() {
        CustomerWholeMultiItem content = new CustomerWholeMultiItem(TYPE_CONTENT);
        CustomerWholeMultiItem contact = new CustomerWholeMultiItem(TYPE_CONTACT);
        CustomerWholeMultiItem visit = new CustomerWholeMultiItem(TYPE_VISIT);
//        CustomerWholeMultiItem sale = new CustomerWholeMultiItem(TYPE_SALE);
        CustomerWholeMultiItem returN = new CustomerWholeMultiItem(TYPE_RETURN);
        CustomerWholeMultiItem refund = new CustomerWholeMultiItem(TYPE_REFUND);
        CustomerWholeMultiItem invoice = new CustomerWholeMultiItem(TYPE_INVOICE);
        CustomerWholeMultiItem expens = new CustomerWholeMultiItem(TYPE_EXPENS);
        CustomerWholeMultiItem other = new CustomerWholeMultiItem(TYPE_OTHER);
        List<CustomerWholeMultiItem> itemList = new ArrayList<>();
        itemList.add(content);//content
        itemList.add(contact);//联系人
        itemList.add(visit);//拜访
//        itemList.add(sale);
        itemList.add(returN);//回款
        itemList.add(refund);//退款
        itemList.add(invoice);//开票
        itemList.add(expens);//费用报销
        itemList.add(other);//其他
        return itemList;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
