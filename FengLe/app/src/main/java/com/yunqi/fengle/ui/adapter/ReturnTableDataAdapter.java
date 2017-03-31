package com.yunqi.fengle.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.model.bean.ReturnApply;
import com.yunqi.fengle.util.TimeUtil;

import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;


public class ReturnTableDataAdapter extends TableDataAdapter<ReturnApply> {
    private Context context;

    public ReturnTableDataAdapter(Context context, List<ReturnApply> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        ReturnApply ReturnApply = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderReturnApplyTime(ReturnApply);
                break;
            case 1:
                renderedView = renderCustomerName(ReturnApply);
                break;
            case 2:
                renderedView = renderStatus(ReturnApply);
                break;
            case 3:
                renderedView = renderOprater(ReturnApply);
                break;
        }
        if (rowIndex % 2 == 0) {
            renderedView.setBackgroundColor(getResources().getColor(R.color.white));
        } else {
            renderedView.setBackgroundColor(getResources().getColor(R.color.bg_color3));
        }

        return renderedView;
    }

    private View renderOprater(ReturnApply ReturnApply) {
        return renderString("编辑/查看");
    }


    private View renderStatus(ReturnApply ReturnApply) {
        String strStatus = null;
        switch (ReturnApply.status){
            case 1:
                strStatus = context.getString(R.string.bill_status_1);
                break;
            case 2:
                String id = App.getInstance().getUserInfo().id;
                //如果单据是本人提交的，则是未完成状态
                if (id.equals(ReturnApply.userid)) {
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

    private View renderCustomerName(ReturnApply ReturnApply) {
        return renderString(ReturnApply.client_name);
    }

    private View renderReturnApplyTime(ReturnApply ReturnApply) {
        return renderString(TimeUtil.converTime("yyyy-MM-dd HH:mm:ss","yyyy-MM-dd",ReturnApply.create_time));
    }

    private View renderString(final String value) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_data_view, null);
        TextView textView = (TextView) view.findViewById(R.id.txt);
        textView.setText(value);
        return view;
    }

}
