package com.yunqi.fengle.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.model.bean.PlanAdjustmentApply;
import com.yunqi.fengle.util.TimeUtil;

import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;


public class PlanAdjustmentTableDataAdapter extends TableDataAdapter<PlanAdjustmentApply> {
    private Context context;

    public PlanAdjustmentTableDataAdapter(Context context, List<PlanAdjustmentApply> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        PlanAdjustmentApply PlanAdjustmentApply = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderPlanAdjustmentApplyTime(PlanAdjustmentApply);
                break;
            case 1:
                renderedView = renderCustomerName(PlanAdjustmentApply);
                break;
            case 2:
                renderedView = renderStatus(PlanAdjustmentApply);
                break;
            case 3:
                renderedView = renderOprater(PlanAdjustmentApply);
                break;
        }
        if (rowIndex % 2 == 0) {
            renderedView.setBackgroundColor(getResources().getColor(R.color.white));
        } else {
            renderedView.setBackgroundColor(getResources().getColor(R.color.bg_color3));
        }
        return renderedView;
    }

    private View renderOprater(PlanAdjustmentApply PlanAdjustmentApply) {
        return renderString("编辑/查看");
    }


    private View renderStatus(PlanAdjustmentApply PlanAdjustmentApply) {
        String strStatus = null;
        switch (PlanAdjustmentApply.status){
            case 1:
                strStatus = context.getString(R.string.bill_status_1);
                break;
            case 2:
                String id = App.getInstance().getUserInfo().id;
                //如果单据是本人提交的，则是未完成状态
                if (id.equals(PlanAdjustmentApply.userid)) {
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

    private View renderCustomerName(PlanAdjustmentApply planAdjustmentApply) {
        return renderString(planAdjustmentApply.client_name_from);
    }

    private View renderPlanAdjustmentApplyTime(PlanAdjustmentApply PlanAdjustmentApply) {
        return renderString(TimeUtil.converTime("yyyy-MM-dd HH:mm:ss","yyyy-MM-dd",PlanAdjustmentApply.create_time));
    }

    private View renderString(final String value) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_data_view, null);
        TextView textView = (TextView) view.findViewById(R.id.txt);
        textView.setText(value);
        return view;
    }

}
