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


public class PlanAdjustmentTableDataAdapter extends BaseTableDataAdapter<PlanAdjustmentApply> {
    private Context context;

    public PlanAdjustmentTableDataAdapter(Context context, List<PlanAdjustmentApply> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        PlanAdjustmentApply planAdjustmentApply = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderPlanAdjustmentApplyTime(planAdjustmentApply);
                break;
            case 1:
                renderedView = renderFromArea(planAdjustmentApply);
                break;
            case 2:
                renderedView = renderToArea(planAdjustmentApply);
                break;
            case 3:
                renderedView = renderStatus(planAdjustmentApply);
                break;
            case 4:
                renderedView = renderOprater();
                break;
        }
        return renderedView;
    }

    private View renderFromArea(PlanAdjustmentApply planAdjustmentApply) {
        return renderString(planAdjustmentApply.from_area_code);
    }
    private View renderToArea(PlanAdjustmentApply planAdjustmentApply) {
        return renderString(planAdjustmentApply.to_area_code);
    }
    private View renderOprater() {
        return renderString("编辑/查看");
    }


    private View renderStatus(PlanAdjustmentApply PlanAdjustmentApply) {
        String strStatus = null;
        switch (PlanAdjustmentApply.status){
            case 1:
                String id = App.getInstance().getUserInfo().id;
                //如果单据是本人提交的，则是未完成状态
                if (id.equals(PlanAdjustmentApply.userid)) {
                    strStatus = context.getString(R.string.bill_status_undone);
                } else {
                    strStatus = context.getString(R.string.bill_status_2);
                }
                break;
            case 2:
                strStatus = context.getString(R.string.bill_status_3);
                break;
            case 3:
                strStatus = context.getString(R.string.bill_status_4);
                break;
        }
        return renderString(strStatus);
    }

    private View renderStandard(PlanAdjustmentApply planAdjustmentApply) {
        return renderString(planAdjustmentApply.from_area_code);
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
