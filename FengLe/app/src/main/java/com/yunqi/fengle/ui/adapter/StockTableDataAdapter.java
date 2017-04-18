package com.yunqi.fengle.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.model.bean.Goods;

import java.util.List;



public class StockTableDataAdapter extends BaseTableDataAdapter<Goods> {
    private Context context;
    public StockTableDataAdapter(Context context, List<Goods> data) {
        super(context, data);
        this.context=context;
    }


    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        Goods goods = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {

            case 0:
                renderedView = renderGoodsName(goods,parentView);
                break;
            case 1:
                renderedView = renderGoodsStandard(goods,parentView);
                break;
            case 2:
                renderedView = renderGoodsPlan(goods,parentView);
                break;
            case 3:
                renderedView = renderGoodsWarehouse(goods,parentView);
                break;
            case 4:
                renderedView = renderGoodsPrice(goods,parentView);
                break;
        }
        return renderedView;
    }



    private View renderGoodsPrice(Goods goods,ViewGroup parentView ) {
        return renderString(goods.goods_price+"",parentView);
    }

    private View renderGoodsStandard(Goods goods,ViewGroup parentView ) {
        return renderString(goods.goods_standard,parentView);
    }

    private View renderGoodsName(Goods goods,ViewGroup parentView ) {
        return renderString(goods.goods_name,parentView);
    }
    private View renderGoodsWarehouse(Goods goods,ViewGroup parentView ) {
        return renderString(goods.goods_num+"",parentView);
    }


    private View renderGoodsPlan(Goods goods,ViewGroup parentView ) {
        return renderString(goods.goods_plan+"",parentView);
    }

    private View renderString( String value,ViewGroup parentView ) {
        final View view = getLayoutInflater().inflate(R.layout.table_data_view, parentView, false);
        final TextView textView = (TextView) view.findViewById(R.id.txt);
        textView.setText(value);
        return view;
    }

}
