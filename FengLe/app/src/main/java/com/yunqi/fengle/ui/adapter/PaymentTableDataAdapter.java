package com.yunqi.fengle.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.model.bean.Goods;
import com.yunqi.fengle.model.bean.Payment;
import com.yunqi.fengle.util.TimeUtil;

import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;


public class PaymentTableDataAdapter extends BaseTableDataAdapter<Payment> {
    private Context context;
    public PaymentTableDataAdapter(Context context, List<Payment> data) {
        super(context, data);
        this.context=context;
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        Payment payment = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderPaymentDate(payment);
                break;
            case 1:
                renderedView = renderClientName(payment);
                break;
            case 2:
                renderedView = renderRemittanceAmount(payment);
                break;
            case 3:
                renderedView = renderOprater(payment);
                break;
        }
        return renderedView;
    }

    private View renderOprater(Payment payment) {
        return renderString("删除");
    }


    private View renderRemittanceAmount(Payment payment) {
        return renderString(payment.huikuan_amount+"");
    }

    private View renderClientName(Payment payment) {
        return renderString(payment.client_name);
    }

    private View renderPaymentDate(Payment payment ) {
        return renderString(TimeUtil.converTime("yyyy-MM-dd HH:mm:ss","yyyy-MM-dd",payment.huikuan_time));
    }

    private View renderString(final String value) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_data_view, null);
        TextView textView = (TextView) view.findViewById(R.id.txt);
        textView.setText(value);
        return view;
    }

}
