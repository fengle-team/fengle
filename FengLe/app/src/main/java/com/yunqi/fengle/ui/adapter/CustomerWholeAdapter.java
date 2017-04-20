package com.yunqi.fengle.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunqi.fengle.R;
import com.yunqi.fengle.model.response.CustomerWholeResponse;
import com.yunqi.fengle.model.response.CustomersResponse;
import com.yunqi.fengle.model.response.model.VisitPlanEntity;
import com.yunqi.fengle.ui.activity.CustomerWholeActivity;
import com.yunqi.fengle.util.LogEx;

import java.util.List;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 00:28
 * @Description:客户全貌Adapter
 *              {@link com.yunqi.fengle.ui.activity.CustomerWholeActivity}
 */

public class CustomerWholeAdapter extends BaseMultiItemQuickAdapter<CustomerWholeResponse, BaseViewHolder> {

    private Context mContext;

    private CustomerListener listener;


//    public static final int TYPE_VISIT = 0x03;//拜访
//    public static final int TYPE_SALE = 0x04;//销售订单
//    public static final int TYPE_RETURN = 0x05;//回款
//    public static final int TYPE_REFUND = 0x06;//退款
//    public static final int TYPE_INVOICE = 0x07;//开票

    public CustomerWholeAdapter(Context context,List<CustomerWholeResponse> data) {
        super(data);
        this.mContext = context;
        addItemType(CustomerWholeMultiItem.TYPE_CONTENT, R.layout.item_customer_whole_content);
//        addItemType(CustomerWholeMultiItem.TYPE_CONTACT, R.layout.item_customer_whole_contack);//联系人
        addItemType(CustomerWholeMultiItem.TYPE_VISIT, R.layout.item_customer_whole_visit);//拜访
//        addItemType(CustomerWholeMultiItem.TYPE_SALE, R.layout.item_customer_whole_sale);//销售订单不用
        addItemType(CustomerWholeMultiItem.TYPE_RETURN, R.layout.item_customer_whole_return);//回款
        addItemType(CustomerWholeMultiItem.TYPE_REFUND, R.layout.item_customer_whole_refund);//退款
        addItemType(CustomerWholeMultiItem.TYPE_INVOICE, R.layout.item_customer_whole_invoice);
//        addItemType(CustomerWholeMultiItem.TYPE_EXPENS, R.layout.item_customer_expens);
        addItemType(CustomerWholeMultiItem.TYPE_OTHER, R.layout.item_customer_other);//其他
//        addItemType(CustomerWholeMultiItem.TYPE_CONTENT1, R.layout.item_customer_whole_content);
//        addItemType(CustomerWholeMultiItem.TYPE_CONTENT2, R.layout.item_customer_whole_content);
    }

    @Override
    protected void convert(BaseViewHolder helper, CustomerWholeResponse item) {
        switch (helper.getItemViewType()) {

            case CustomerWholeMultiItem.TYPE_CONTENT:
                TextView tvFahuo = helper.getView(R.id.tvFahuo);
                TextView tvTuihuo = helper.getView(R.id.tvTuihuo);
                TextView tvHuikuan = helper.getView(R.id.tvHuikuan);
                TextView tvCompany = helper.getView(R.id.tvCompany);
                TextView tvChengj = helper.getView(R.id.tvChengj);

                tvFahuo.setText(item.getFahuo_amount() + "");
                tvCompany.setText(item.getCompany_name() + "");
                tvTuihuo.setText(item.getHuikuan_amount() + "");
                tvHuikuan.setText(item.getHuikuan_amount() + "");

                if (item.getType() == CustomersResponse.TYPE_DEAL)
                {//已经成交
                    tvChengj.setText("已成交");
                } else {
                    tvChengj.setText("未成交");
                }

                break;
            case CustomerWholeMultiItem.TYPE_CONTACT://联系人

                helper.getView(R.id.ivAdd).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onAddContact();
                    }
                });
                break;
            case CustomerWholeMultiItem.TYPE_VISIT://拜访
                TextView tvCompanyVisit = helper.getView(R.id.tvCompany);
                TextView tvPlanTime = helper.getView(R.id.tvPlanTime);
                TextView tvNum = helper.getView(R.id.tvNum);
                TextView tvFuz = helper.getView(R.id.tvFuz);


                VisitPlanEntity.LastVisitePlanEntity planEntity = item.getVisit_plan().getLast_visite_plan();
                tvNum.setText(item.getVisit_plan().getVisite_plan_count() + "");
                if (item.getVisit_plan().getLast_visite_plan() != null) {
                    tvPlanTime.setText(planEntity.getPlan_time() + "");
                    if (planEntity.getClient_name() == null || planEntity.getClient_name().equals("")) {
                        tvCompanyVisit.setVisibility(View.GONE);
                    }
                    tvCompanyVisit.setText(planEntity.getClient_name() + "");
                    tvFuz.setText(planEntity.getResponsible_name() + "");
                }
                break;
            case CustomerWholeMultiItem.TYPE_RETURN://回款
                TextView tvId = helper.getView(R.id.tvId);
                TextView tvDate = helper.getView(R.id.tvHKDate);
                TextView tvReturnNum = helper.getView(R.id.tvNum);
                TextView tvAmount = helper.getView(R.id.tvAmount);

                tvReturnNum.setText(item.getHuikuan_info().getHuikuan_count() + "");

                if (item.getHuikuan_info().getLast_huikuan() != null) {
                    tvId.setText(item.getHuikuan_info().getLast_huikuan().getHk_id() + "");
                    tvDate.setText(item.getHuikuan_info().getLast_huikuan().getHuikuan_time() + "");
                    tvAmount.setText(item.getHuikuan_info().getLast_huikuan().getHuikuan_amount() + "");
                }
                break;
            case CustomerWholeMultiItem.TYPE_REFUND://退款
                TextView tvTuikuanId = helper.getView(R.id.tvTuikuanId);
                TextView tvTuikuanDate = helper.getView(R.id.tvTuikuanDate);
                TextView tvTuikuanNum = helper.getView(R.id.tvTuikuanNum);
                TextView tvTuikuanAmount = helper.getView(R.id.tvTuikuanAmount);

                tvTuikuanNum.setText(item.getTuihuo_info().getTuihuo_count() + "");

                if (item.getTuihuo_info().getLast_tuihuo() != null) {
                    tvTuikuanId.setText(item.getTuihuo_info().getLast_tuihuo().getOrder_code() + "");
                    tvTuikuanDate.setText(item.getTuihuo_info().getLast_tuihuo().getCreate_time() + "");
//                    tvTuikuanNum.setText(item.getTuihuo_info().getLast_tuihuo().get + "");
//                    tvTuikuanAmount.setText(item.getTuihuo_info().getLast_tuihuo().get + "");
                }
                break;
            case CustomerWholeMultiItem.TYPE_INVOICE://开票申请
                TextView tvKpAmount = helper.getView(R.id.tvKpAmount);
                TextView tvKpDate = helper.getView(R.id.tvKpDate);
                TextView tvKpId = helper.getView(R.id.tvKpId);

                tvKpAmount.setText(item.getInvoce_info().getInvoice_count() + "");
                if (item.getInvoce_info().getLast_invoice() != null) {
                    tvKpDate.setText(item.getInvoce_info().getLast_invoice().getCreate_time() + "");
                    tvKpId.setText(item.getInvoce_info().getLast_invoice().getOrder_code() + "");
                }
                break;
            case CustomerWholeMultiItem.TYPE_OTHER://其他
                helper.addOnClickListener(R.id.rlActivity);
                helper.addOnClickListener(R.id.rlPlan);
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
