package com.yunqi.fengle.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.model.bean.PlanAdjustmentDetail;

import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;


public class PlanAdjustmentDetailTableDataAdapter extends TableDataAdapter<PlanAdjustmentDetail> {
    private Context context;

    public PlanAdjustmentDetailTableDataAdapter(Context context, List<PlanAdjustmentDetail> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        PlanAdjustmentDetail planAdjustmentDetail = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderGoodName(planAdjustmentDetail);
                break;
            case 1:
                renderedView = renderGoodStandard(planAdjustmentDetail);
                break;
            case 2:
                renderedView = renderGoodNum(planAdjustmentDetail);
                break;
            case 3:
                renderedView = renderGoodsUnitsNum(planAdjustmentDetail);
                break;
            case 4:
                renderedView = renderOperater(planAdjustmentDetail);
                break;
        }
        if (rowIndex % 2 == 0) {
            renderedView.setBackgroundColor(getResources().getColor(R.color.white));
        } else {
            renderedView.setBackgroundColor(getResources().getColor(R.color.bg_color3));
        }

        return renderedView;
    }




    private View renderOperater(PlanAdjustmentDetail PlanAdjustmentDetail) {
        return renderString("删除");
    }
    private View renderGoodsUnitsNum(PlanAdjustmentDetail PlanAdjustmentDetail) {
        return renderString(PlanAdjustmentDetail.goods_units_num+"");
    }

    private View renderGoodNum(PlanAdjustmentDetail PlanAdjustmentDetail) {
        return renderString(PlanAdjustmentDetail.goods_num+"");
    }

    private View renderGoodName(PlanAdjustmentDetail PlanAdjustmentDetail) {
        return renderString(PlanAdjustmentDetail.goods_name);
    }
    private View renderGoodStandard(PlanAdjustmentDetail PlanAdjustmentDetail) {
        return renderString(PlanAdjustmentDetail.goods_standard);
    }

    private View renderWarehouse(PlanAdjustmentDetail PlanAdjustmentDetail) {
        return renderString(PlanAdjustmentDetail.goods_warehouse);
    }

    private View renderString(final String value) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_data_view, null);
        TextView textView = (TextView) view.findViewById(R.id.txt);
        textView.setText(value);
        return view;
    }

}
