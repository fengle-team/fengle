package com.yunqi.fengle.ui.adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunqi.fengle.R;
import com.yunqi.fengle.ui.activity.CustomerWholeActivity;
import com.yunqi.fengle.util.LogEx;

import java.util.List;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 00:28
 * @Description:客户全貌Adapter
 *              {@link com.yunqi.fengle.ui.activity.CustomerWholeActivity}
 */

public class CustomerWholeAdapter extends BaseMultiItemQuickAdapter<CustomerWholeMultiItem, BaseViewHolder> {

    private Context mContext;

    private CustomerListener listener;


//    public static final int TYPE_VISIT = 0x03;//拜访
//    public static final int TYPE_SALE = 0x04;//销售订单
//    public static final int TYPE_RETURN = 0x05;//回款
//    public static final int TYPE_REFUND = 0x06;//退款
//    public static final int TYPE_INVOICE = 0x07;//开票

    public CustomerWholeAdapter(Context context,List<CustomerWholeMultiItem> data) {
        super(data);
        this.mContext = context;
        addItemType(CustomerWholeMultiItem.TYPE_CONTENT, R.layout.item_customer_whole_content);
        addItemType(CustomerWholeMultiItem.TYPE_CONTACT, R.layout.item_customer_whole_contack);
        addItemType(CustomerWholeMultiItem.TYPE_VISIT, R.layout.item_customer_whole_visit);
//        addItemType(CustomerWholeMultiItem.TYPE_SALE, R.layout.item_customer_whole_sale);//销售订单不用
        addItemType(CustomerWholeMultiItem.TYPE_RETURN, R.layout.item_customer_whole_return);
        addItemType(CustomerWholeMultiItem.TYPE_REFUND, R.layout.item_customer_whole_refund);//退款
        addItemType(CustomerWholeMultiItem.TYPE_INVOICE, R.layout.item_customer_whole_invoice);
        addItemType(CustomerWholeMultiItem.TYPE_EXPENS, R.layout.item_customer_expens);
        addItemType(CustomerWholeMultiItem.TYPE_OTHER, R.layout.item_customer_other);
//        addItemType(CustomerWholeMultiItem.TYPE_CONTENT1, R.layout.item_customer_whole_content);
//        addItemType(CustomerWholeMultiItem.TYPE_CONTENT2, R.layout.item_customer_whole_content);
    }

    @Override
    protected void convert(BaseViewHolder helper, CustomerWholeMultiItem item) {
        switch (helper.getItemViewType()) {
            case CustomerWholeMultiItem.TYPE_CONTACT://联系人
                helper.getView(R.id.ivAdd).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onAddContact();
                    }
                });
                break;
        }
    }

    public void setListener(CustomerListener listener) {
        this.listener = listener;
    }

    public interface CustomerListener {
        void onAddContact();
    }


}
