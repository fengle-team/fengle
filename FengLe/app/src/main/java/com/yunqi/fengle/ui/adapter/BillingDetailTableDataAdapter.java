package com.yunqi.fengle.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.model.bean.BillingDetail;

import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;


public class BillingDetailTableDataAdapter extends TableDataAdapter<BillingDetail> {
    private Context context;

    public BillingDetailTableDataAdapter(Context context, List<BillingDetail> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        BillingDetail BillingDetail = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderGoodName(BillingDetail);
                break;
            case 1:
                renderedView = renderGoodStandard(BillingDetail);
                break;
            case 2:
                renderedView = renderGoodNum(BillingDetail);
                break;
            case 3:
                renderedView = renderGoodsUnitsNum(BillingDetail);
                break;
            case 4:
                renderedView = renderOperater(BillingDetail);
                break;
        }
        if (rowIndex % 2 == 0) {
            renderedView.setBackgroundColor(getResources().getColor(R.color.white));
        } else {
            renderedView.setBackgroundColor(getResources().getColor(R.color.bg_color3));
        }

        return renderedView;
    }




    private View renderOperater(BillingDetail BillingDetail) {
        return renderString("删除");
    }
    private View renderGoodsUnitsNum(BillingDetail BillingDetail) {
        return renderString(BillingDetail.goods_units_num+"");
    }

    private View renderGoodNum(BillingDetail BillingDetail) {
        return renderString(BillingDetail.goods_num+"");
    }

    private View renderGoodName(BillingDetail BillingDetail) {
        return renderString(BillingDetail.goods_name);
    }
    private View renderGoodStandard(BillingDetail BillingDetail) {
        return renderString(BillingDetail.goods_standard);
    }

    private View renderWarehouse(BillingDetail BillingDetail) {
        return renderString(BillingDetail.goods_warehouse);
    }

    private View renderString(final String value) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_data_view, null);
        TextView textView = (TextView) view.findViewById(R.id.txt);
        textView.setText(value);
        return view;
    }

}
