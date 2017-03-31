package com.yunqi.fengle.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.model.bean.TransferDetail;

import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;


public class TransferDetailTableDataAdapter extends TableDataAdapter<TransferDetail> {
    private Context context;

    public TransferDetailTableDataAdapter(Context context, List<TransferDetail> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        TransferDetail transferDetail = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderGoodName(transferDetail);
                break;
            case 1:
                renderedView = renderGoodStandard(transferDetail);
                break;
            case 2:
                renderedView = renderGoodNum(transferDetail);
                break;
            case 3:
                renderedView = renderGoodsUnitsNum(transferDetail);
                break;
            case 4:
                renderedView = renderOperater(transferDetail);
                break;
        }
        if (rowIndex % 2 == 0) {
            renderedView.setBackgroundColor(getResources().getColor(R.color.white));
        } else {
            renderedView.setBackgroundColor(getResources().getColor(R.color.bg_color3));
        }

        return renderedView;
    }




    private View renderOperater(TransferDetail transferDetail) {
        return renderString("删除");
    }
    private View renderGoodsUnitsNum(TransferDetail transferDetail) {
        return renderString(transferDetail.goods_units_num+"");
    }

    private View renderGoodNum(TransferDetail transferDetail) {
        return renderString(transferDetail.goods_num+"");
    }

    private View renderGoodName(TransferDetail transferDetail) {
        return renderString(transferDetail.goods_name);
    }
    private View renderGoodStandard(TransferDetail transferDetail) {
        return renderString(transferDetail.goods_standard);
    }

    private View renderWarehouse(TransferDetail transferDetail) {
        return renderString(transferDetail.goods_warehouse);
    }

    private View renderString(final String value) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_data_view, null);
        TextView textView = (TextView) view.findViewById(R.id.txt);
        textView.setText(value);
        return view;
    }

}
