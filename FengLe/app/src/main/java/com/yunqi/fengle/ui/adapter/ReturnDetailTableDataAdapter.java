package com.yunqi.fengle.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.model.bean.ReturnDetail;

import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;


public class ReturnDetailTableDataAdapter extends TableDataAdapter<ReturnDetail> {
    private Context context;

    public ReturnDetailTableDataAdapter(Context context, List<ReturnDetail> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        ReturnDetail returnDetail = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderGoodName(returnDetail);
                break;
            case 1:
                renderedView = renderGoodStandard(returnDetail);
                break;
            case 2:
                renderedView = renderGoodNum(returnDetail);
                break;
            case 3:
                renderedView = renderGoodsUnitsNum(returnDetail);
                break;
            case 4:
                renderedView = renderOperater(returnDetail);
                break;
        }
        if (rowIndex % 2 == 0) {
            renderedView.setBackgroundColor(getResources().getColor(R.color.white));
        } else {
            renderedView.setBackgroundColor(getResources().getColor(R.color.bg_color3));
        }

        return renderedView;
    }




    private View renderOperater(ReturnDetail ReturnDetail) {
        return renderString("删除");
    }
    private View renderGoodsUnitsNum(ReturnDetail ReturnDetail) {
        return renderString(ReturnDetail.goods_units_num+"");
    }

    private View renderGoodNum(ReturnDetail ReturnDetail) {
        return renderString(ReturnDetail.goods_num+"");
    }

    private View renderGoodName(ReturnDetail ReturnDetail) {
        return renderString(ReturnDetail.goods_name);
    }
    private View renderGoodStandard(ReturnDetail ReturnDetail) {
        return renderString(ReturnDetail.goods_standard);
    }

    private View renderWarehouse(ReturnDetail ReturnDetail) {
        return renderString(ReturnDetail.goods_warehouse);
    }

    private View renderString(final String value) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_data_view, null);
        TextView textView = (TextView) view.findViewById(R.id.txt);
        textView.setText(value);
        return view;
    }

}
