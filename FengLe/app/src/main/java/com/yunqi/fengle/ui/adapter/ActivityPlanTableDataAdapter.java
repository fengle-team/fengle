package com.yunqi.fengle.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.model.bean.Goods;
import com.yunqi.fengle.model.response.ActivityAddResponse;

import java.util.List;


public class ActivityPlanTableDataAdapter extends BaseTableDataAdapter<ActivityAddResponse> {
    private Context context;

    private int billStatus;

    public ActivityPlanTableDataAdapter(Context context, List<ActivityAddResponse> data) {
        super(context, data);
        this.context=context;
    }


    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        ActivityAddResponse bean = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {

            case 0:
                renderedView = renderString(bean.getRegion(),parentView);//大区
                break;
            case 1:
                renderedView = renderString(bean.getClient_name(), parentView);//客户名称
                break;
            case 2:
                renderedView = renderString(bean.getApply_budget() + "", parentView);//费用预算
                break;
            case 3:
                renderedView = renderString(bean.getStatusDes(billStatus), parentView);//状态
                break;
            case 4:
                renderedView = renderString("查看", parentView);
                break;
        }
        return renderedView;
    }

    public void setBillStatus(int billStatus){
        this.billStatus=billStatus;
    }

    public int getBillStatus() {
        return billStatus;
    }

    private View renderString(String value, ViewGroup parentView ) {
        final View view = getLayoutInflater().inflate(R.layout.table_data_view, parentView, false);
        final TextView textView = (TextView) view.findViewById(R.id.txt);
        textView.setText(value);
        return view;
    }

}
