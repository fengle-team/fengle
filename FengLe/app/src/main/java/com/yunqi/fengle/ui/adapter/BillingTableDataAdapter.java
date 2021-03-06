package com.yunqi.fengle.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.model.bean.BillingApply;
import com.yunqi.fengle.util.TimeUtil;

import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;


public class BillingTableDataAdapter extends BaseTableDataAdapter<BillingApply> {
    private Context context;
    private int billStatus;

    public BillingTableDataAdapter(Context context, List<BillingApply> data) {
        super(context, data);
        this.context = context;
    }
    public void setBillStatus(int billStatus){
        this.billStatus=billStatus;
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        BillingApply billingApply = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderBillingApplyTime(billingApply);
                break;
            case 1:
                renderedView = renderCustomerName(billingApply);
                break;
            case 2:
                renderedView = renderStatus(billingApply);
                break;
            case 3:
                renderedView = renderOprater(billingApply);
                break;
        }
        return renderedView;
    }

    private View renderOprater(BillingApply billingApply) {
        return renderString("编辑/查看");
    }


    private View renderStatus(BillingApply billingApply) {
        String strStatus = null;
        switch (billingApply.status){
            case 1:
                strStatus = context.getString(R.string.bill_status_1);
                break;
            case 2:
                String id = App.getInstance().getUserInfo().id;
                //如果单据是本人提交的，则是未完成状态
                if (id.equals(billingApply.userid)) {
                    if(billStatus==1){
                        strStatus = context.getString(R.string.bill_status_2);
                    }
                    else{
                        strStatus = context.getString(R.string.bill_status_undone);
                    }
                } else {
                    if(billStatus==3){
                        strStatus = context.getString(R.string.bill_status_5);
                    }
                    else {
                        strStatus = context.getString(R.string.bill_status_2);
                    }
                }
                break;
            case 3:
                if (billingApply.u8_order != null) {
                    if("待修改".equals(billingApply.u8_order.define1)){
                        strStatus = context.getString(R.string.bill_status_7);
                    }
                    else {
                        strStatus = context.getString(R.string.bill_status_3);
                    }
                }
                else {
                    strStatus = context.getString(R.string.bill_status_3);
                }
                break;
            case 4:
                strStatus = context.getString(R.string.bill_status_4);
                break;
            default:
                strStatus =context.getString(R.string.bill_status_unknown);
                break;
        }
        return renderString(strStatus);
    }

    private View renderCustomerName(BillingApply billingApply) {
        return renderString(billingApply.client_name);
    }

    private View renderBillingApplyTime(BillingApply billingApply) {
        return renderString(TimeUtil.converTime("yyyy-MM-dd HH:mm:ss","yyyy-MM-dd",billingApply.create_time));
    }

    private View renderString(final String value) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_data_view, null);
        TextView textView = (TextView) view.findViewById(R.id.txt);
        textView.setText(value);
        return view;
    }

}
