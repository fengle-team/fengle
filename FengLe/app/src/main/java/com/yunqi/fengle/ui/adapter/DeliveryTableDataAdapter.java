package com.yunqi.fengle.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.model.bean.InvoiceApply;
import com.yunqi.fengle.model.bean.Payment;
import com.yunqi.fengle.util.TimeUtil;

import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;


public class DeliveryTableDataAdapter extends TableDataAdapter<InvoiceApply> {
    private Context context;

    public DeliveryTableDataAdapter(Context context, List<InvoiceApply> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        InvoiceApply invoiceApply = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderInvoiceApplyTime(invoiceApply);
                break;
            case 1:
                renderedView = renderCustomerName(invoiceApply);
                break;
            case 2:
                renderedView = renderStatus(invoiceApply);
                break;
            case 3:
                renderedView = renderOprater(invoiceApply);
                break;
        }
        if (rowIndex % 2 == 0) {
            renderedView.setBackgroundColor(getResources().getColor(R.color.white));
        } else {
            renderedView.setBackgroundColor(getResources().getColor(R.color.bg_color3));
        }

        return renderedView;
    }

    private View renderOprater(InvoiceApply invoiceApply) {
        return renderString("编辑/查看");
    }


    private View renderStatus(InvoiceApply invoiceApply) {
        String strStatus = null;
        switch (invoiceApply.status){
            case 1:
                strStatus = context.getString(R.string.bill_status_1);
                break;
            case 2:
                String id = App.getInstance().getUserInfo().id;
                //如果单据是本人提交的，则是未完成状态
                if (id.equals(invoiceApply.userid)) {
                    strStatus = context.getString(R.string.bill_status_undone);
                } else {
                    strStatus = context.getString(R.string.bill_status_2);
                }
                break;
            case 3:
                strStatus = context.getString(R.string.bill_status_3);
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

    private View renderCustomerName(InvoiceApply invoiceApply) {
        return renderString(invoiceApply.client_name);
    }

    private View renderInvoiceApplyTime(InvoiceApply invoiceApply) {
        return renderString(TimeUtil.converTime("yyyy-MM-dd HH:mm:ss","yyyy-MM-dd",invoiceApply.create_time));
    }

    private View renderString(final String value) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_data_view, null);
        TextView textView = (TextView) view.findViewById(R.id.txt);
        textView.setText(value);
        return view;
    }

}
