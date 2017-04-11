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

import de.codecrafters.tableview.TableDataAdapter;


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
                renderedView =renderName(customer);
                break;
            case 1:
                renderedView = renderAmount(customer);
                break;
            case 2:
                renderedView = renderRanking(customer);
                break;
            case 3:
                renderedView = renderLastShipment(customer);
                break;
            case 4:
                renderedView = renderLastRanking(customer);
                break;
            case 5:
                renderedView = renderLift(customer);
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

    private View renderName(CustomerAnalysis customer) {
        return renderString(customer.client_name);
    }
    private View renderLastRanking(CustomerAnalysis customer) {
        return renderString(customer.fh_amount_rank+"");
    }
    private View renderRanking(CustomerAnalysis customer) {
        return renderString(customer.fh_num_rank+"");
    }
    private View renderLastShipment(CustomerAnalysis customer) {
        return renderString(customer.huikuan_amount+"");
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
