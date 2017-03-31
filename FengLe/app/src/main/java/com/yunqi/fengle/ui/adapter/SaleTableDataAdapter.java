package com.yunqi.fengle.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.model.bean.Goods;
import com.yunqi.fengle.model.bean.SaleInfo;

import java.util.ArrayList;
import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;


public class SaleTableDataAdapter extends TableDataAdapter<SaleInfo> {
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
                renderedView = renderGoodsDetail(saleInfo);
                break;
        }
        if(rowIndex%2==0){
            renderedView.setBackgroundColor(getResources().getColor(R.color.white));
        }
        else{
            renderedView.setBackgroundColor(getResources().getColor(R.color.bg_color3));
        }

        return renderedView;
    }



    private View renderClientName(SaleInfo saleInfo) {
        return renderString(saleInfo.client_name);
    }

    private View renderGoodsCode(SaleInfo saleInfo) {
        return renderString(saleInfo.goods_code);
    }

    private View renderGoodsName(SaleInfo saleInfo) {
        return renderString(saleInfo.goods_name);
    }
    private View renderGoodsDetail(SaleInfo saleInfo) {
        return renderString("查看");
    }



    private View renderString(final String value) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_data_view, null);
        TextView textView = (TextView) view.findViewById(R.id.txt);
        textView.setText(value);
        return view;
    }

}
