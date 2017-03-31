package com.yunqi.fengle.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.model.bean.Goods;

import java.util.ArrayList;
import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;


public class StockTableDataAdapter extends TableDataAdapter<Goods> {
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
                renderedView = renderGoodsName(goods);
                break;
            case 1:
                renderedView = renderGoodsStandard(goods);
                break;
            case 2:
                renderedView = renderGoodsPlan(goods);
                break;
            case 3:
                renderedView = renderGoodsWarehouse(goods);
                break;
            case 4:
                renderedView = renderGoodsPrice(goods);
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


    private View renderGoodsPrice(Goods goods) {
        return renderString(goods.goods_price+"");
    }

    private View renderGoodsStandard(Goods goods) {
        return renderString(goods.goods_standard);
    }

    private View renderGoodsName(Goods goods) {
        return renderString(goods.goods_name);
    }
    private View renderGoodsWarehouse(Goods goods) {
        return renderString(goods.goods_num+"");
    }


    private View renderGoodsPlan(Goods goods) {
        return renderString(goods.goods_plan);
    }

    private View renderString(final String value) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_data_view, null);
        TextView textView = (TextView) view.findViewById(R.id.txt);
        textView.setText(value);
        return view;
    }

}
