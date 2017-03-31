package com.yunqi.fengle.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.model.bean.Goods;
import com.yunqi.fengle.model.response.CustomersResponse;

import java.util.ArrayList;
import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;
import de.codecrafters.tableview.TableHeaderAdapter;
import de.codecrafters.tableview.model.TableColumnModel;

/**
 * @Author: Huangweicai
 * @date 2017-02-17 14:25
 * @Description:(这里用一句话描述这个类的作用)
 */

public class VisitingCustomerAdapter extends TableDataAdapter<CustomersResponse> {

    private Context mContext;

    private List<CustomersResponse> data;

    private ArrayList<CustomersResponse> selectedData;


    public VisitingCustomerAdapter(Context context, List<CustomersResponse> data) {
        super(context, data);
        this.mContext = context;
        this.data = data;
        selectedData = new ArrayList<>();
    }


    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        View renderedView = null;
        CustomersResponse bean = data.get(rowIndex);
        if (columnIndex + 1 == getColumnModel().getColumnCount()) {
            renderedView = renderCheckBox(rowIndex);
        } else if(columnIndex == 0) {//姓名
            renderedView = renderString(bean.getName());
        } else if (columnIndex == 1) {//公司
            renderedView = renderString(bean.getCompany_name());
        } else if (columnIndex == 2) {//职位
            renderedView = renderString(bean.getPosition());
        }

        if (rowIndex % 2 == 0) {
            renderedView.setBackgroundColor(getResources().getColor(R.color.white));
        } else {
            renderedView.setBackgroundColor(getResources().getColor(R.color.bg_color3));
        }

        return renderedView;
    }

    public void setDataChanged(List<CustomersResponse> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    private View renderString(final String value) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.table_data_view, null);
        TextView textView = (TextView) view.findViewById(R.id.txt);
        textView.setText(value);
        return view;
    }

    private View renderCheckBox(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.table_data_check, null);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.check);
        checkBox.setOnCheckedChangeListener(new VisiteOnCheckedChangeListener(position));
        return view;
    }

    class VisiteOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {

        private int position;

        public VisiteOnCheckedChangeListener(int position) {
            this.position = position;
        }

        @Override

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            CustomersResponse customer = data.get(position);
            customer.setChecked(isChecked);
        }
    }

    public ArrayList<CustomersResponse> getSelectedData() {
        selectedData.clear();
        for (CustomersResponse bean : data) {
            if (bean.isChecked()) {
                selectedData.add(bean);
            }
        }
        return selectedData;
    }
}
