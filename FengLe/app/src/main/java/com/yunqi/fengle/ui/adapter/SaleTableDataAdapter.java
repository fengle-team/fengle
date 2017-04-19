package com.yunqi.fengle.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.model.bean.SaleInfo;

import java.util.List;



public class SaleTableDataAdapter extends BaseTableDataAdapter<SaleInfo> {
    private Context context;
    public SaleTableDataAdapter(Context context, List<SaleInfo> data) {
        super(context, data);
        this.context=context;
    }


    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        SaleInfo saleInfo = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderClientName(saleInfo);
                break;
            case 1:
                renderedView = renderGoodsCode(saleInfo);
                break;
            case 2:
                renderedView = renderGoodsName(saleInfo);
                break;
            case 3:
                renderedView = renderGoodsDetail();
                break;
        }

        return renderedView;
    }



    private View renderClientName(SaleInfo saleInfo) {
        return renderString(saleInfo.ccusname);
    }

    private View renderGoodsCode(SaleInfo saleInfo) {
        return renderString(saleInfo.cinvcode);
    }

    private View renderGoodsName(SaleInfo saleInfo) {
        return renderString(saleInfo.存货名称);
    }
    private View renderGoodsDetail() {
        return renderString("查看");
    }



    private View renderString(final String value) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_data_view, null);
        TextView textView = (TextView) view.findViewById(R.id.txt);
        textView.setText(value);
        return view;
    }

}
