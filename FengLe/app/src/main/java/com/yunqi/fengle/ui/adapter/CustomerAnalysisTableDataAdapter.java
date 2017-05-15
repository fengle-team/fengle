package com.yunqi.fengle.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.model.bean.CustomerAnalysis;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CustomerAnalysisTableDataAdapter extends BaseTableDataAdapter<CustomerAnalysis> {
    private Context context;
    public CustomerAnalysisTableDataAdapter(Context context, List<CustomerAnalysis> data) {
        super(context, data);
        this.context=context;
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        CustomerAnalysis customer = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView =renderNum(rowIndex);
                break;
            case 1:
                renderedView =renderName(customer);
                break;
            case 2:
                renderedView = renderAmount(customer);
                break;
            case 3:
                renderedView = renderLastShipment(customer);
                break;
            case 4:
                renderedView = renderRate(customer);
                break;
        }
        return renderedView;
    }
    private View renderNum(int rowIndex) {
        return renderString((rowIndex+1)+"");
    }
    private View renderName(CustomerAnalysis customer) {
        return renderString(customer.cusrom_name);
    }
    private View renderRate(CustomerAnalysis customer) {
        return renderString(customer.kp_rates+"%");
    }
    private View renderLastShipment(CustomerAnalysis customer) {
        String fh_amount_last=formatDouble2(customer.fh_amount_last)+"";
        return renderString(fh_amount_last);
    }

    private View renderAmount(CustomerAnalysis customer) {
        String fh_amount_now=formatDouble2(customer.fh_amount_now)+"";
        return renderString(fh_amount_now);
    }

    private View renderString(final String value) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_data_view, null);
        TextView textView = (TextView) view.findViewById(R.id.txt);
        textView.setText(value);
        return view;
    }
    public static double formatDouble2(double d) {
        // 新方法，如果不需要四舍五入，可以使用RoundingMode.DOWN
        BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.UP);
        return bg.doubleValue();
    }
}
