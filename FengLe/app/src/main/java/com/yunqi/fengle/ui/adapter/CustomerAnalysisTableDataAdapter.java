package com.yunqi.fengle.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.model.bean.CustomerAnalysis;

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



    private View renderLift(CustomerAnalysis customer) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_data_select_view, null);
        ImageView img= (ImageView) view.findViewById(R.id.img);
        if(customer.status==1){
            img.setImageResource(R.drawable.up);
        }
        else{
            img.setImageResource(R.drawable.down);
        }
        return view;
    }
    private View renderNum(int rowIndex) {
        return renderString((rowIndex+1)+"");
    }
    private View renderName(CustomerAnalysis customer) {
        return renderString(customer.client_name);
    }
    private View renderRate(CustomerAnalysis customer) {
        return renderString(customer.rank_last_year+"");
    }
    private View renderLastShipment(CustomerAnalysis customer) {
        return renderString(customer.fh_amount_last_year+"");
    }

    private View renderAmount(CustomerAnalysis customer) {
        return renderString(customer.fh_amount+"");
    }

    private View renderString(final String value) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_data_view, null);
        TextView textView = (TextView) view.findViewById(R.id.txt);
        textView.setText(value);
        return view;
    }

}
