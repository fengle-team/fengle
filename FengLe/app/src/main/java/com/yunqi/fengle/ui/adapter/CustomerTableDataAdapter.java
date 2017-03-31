package com.yunqi.fengle.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.model.bean.Customer;
import com.yunqi.fengle.model.bean.Goods;

import java.util.ArrayList;
import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;


public class CustomerTableDataAdapter extends TableDataAdapter<Customer> {
    private Context context;
    private List<Customer> listSelect=new ArrayList<>();
    public CustomerTableDataAdapter(Context context, List<Customer> data) {
        super(context, data);
        this.context=context;
    }



    public void addSelect(Customer customer){
        listSelect.clear();
        listSelect.add(customer);
    }
    public List<Customer> getListSelect(){
        return listSelect;
    }
    public void removeSelect(Customer customer){
        listSelect.remove(customer);
    }
    public boolean isExsit(Customer customer){
        return listSelect.contains(customer);
    }


    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        Customer customer = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderCustomerNumber(customer);
                break;
            case 1:
                renderedView = renderCustomerName(customer);
                break;
            case 2:
                renderedView = renderCustomerSelect(customer);
                break;
        }
        if(rowIndex%2==0){
            renderedView.setBackgroundColor(getResources().getColor(R.color.white));
        }
        else{
            renderedView.setBackgroundColor(getResources().getColor(R.color.bg_color3));
        }

        return renderedView;
    }



    private View renderCustomerSelect(Customer customer) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_data_select_view, null);
        ImageView img= (ImageView) view.findViewById(R.id.img);
        if(this.listSelect.contains(customer)){
            img.setImageResource(R.drawable.selected);
        }
        else{
            img.setImageResource(R.drawable.unselect);
        }
        return view;
    }

    private View renderCustomerName(Customer customer) {
        return renderString(customer.name);
    }

    private View renderCustomerNumber(Customer customer) {
        return renderString(customer.custom_code+"");
    }

    private View renderString(final String value) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_data_view, null);
        TextView textView = (TextView) view.findViewById(R.id.txt);
        textView.setText(value);
        return view;
    }

}
