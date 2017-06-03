package com.yunqi.fengle.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.model.response.ActivityAddResponse;
import com.yunqi.fengle.model.response.ActivitySummaryResponse;

import java.util.List;


public class ActivitySummaryTableDataAdapter extends BaseTableDataAdapter<ActivitySummaryResponse> {
    private Context context;

    private int billStatus;

    public ActivitySummaryTableDataAdapter(Context context, List<ActivitySummaryResponse> data) {
        super(context, data);
        this.context=context;
    }


    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        ActivitySummaryResponse bean = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {

            case 0:
                renderedView = renderString(bean.getCreate_time(),parentView);//大区
                break;
            case 1:
                renderedView = renderString(bean.getClient_name(), parentView);//客户名称
                break;
            case 2:
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
