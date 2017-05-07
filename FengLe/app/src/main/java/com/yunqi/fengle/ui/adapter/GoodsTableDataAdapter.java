package com.yunqi.fengle.ui.adapter;

import android.content.Context;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.model.bean.CustomerAnalysis;
import com.yunqi.fengle.model.bean.Goods;

import java.util.ArrayList;
import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;


public class GoodsTableDataAdapter extends BaseTableDataAdapter<Goods> {
    private Context context;
    private List<Goods> listSelect=new ArrayList<>();
    private int module;//0：表示退货 、开票等 1：发货 2：表示促销
    public GoodsTableDataAdapter(Context context, List<Goods> data) {
        super(context, data);
        this.context=context;
    }

    public void setModule(int module){
        this.module=module;
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        Goods goods = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderGoodsNumber(goods);
                break;
            case 1:
                renderedView = renderGoodsName(goods);
                break;
            case 2:
                renderedView = renderGoodsStandard(goods);
                break;
            case 3:
                renderedView = renderStock(goods);
                break;
            case 4:
                switch (module){
                    case 1:
                        renderedView = renderGoodsPlan(goods);
                        break;
                    case 2:
                        break;
                    default:
                        renderedView = renderGoodsPrice(goods);
                        break;
                }
                break;
            case 5:
                renderedView = renderGoodsPrice(goods);
                break;
        }
        return renderedView;
    }

    private View renderGoodsSelect(Goods goods) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_data_select_view, null);
        ImageView img= (ImageView) view.findViewById(R.id.img);
        if(listSelect.contains(goods)){
            img.setImageResource(R.drawable.selected);
        }
        else{
            img.setImageResource(R.drawable.unselect);
        }
        return view;
    }
    private View renderGoodsPlan(Goods goods) {
        return renderString(goods.goods_plan+"");
    }
    private View renderGoodsPrice(Goods goods) {
        return renderString(goods.goods_price+"");
    }
    private View renderStock(Goods goods) {
        return renderString(goods.goods_num+"");
    }

    private View renderGoodsStandard(Goods goods) {
        return renderString(goods.goods_standard);
    }

    private View renderGoodsName(Goods goods) {
        return renderString(goods.goods_name);
    }

    private View renderGoodsNumber(Goods goods) {
        return renderString(goods.goods_code);
    }

    private View renderString(final String value) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_data_view, null);
        TextView textView = (TextView) view.findViewById(R.id.txt);
        textView.setText(value);
        return view;
    }

}
