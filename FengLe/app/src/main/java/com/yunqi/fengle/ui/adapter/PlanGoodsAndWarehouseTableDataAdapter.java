package com.yunqi.fengle.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.model.bean.GoodsAndWarehouse;

import java.util.List;


public class PlanGoodsAndWarehouseTableDataAdapter extends BaseTableDataAdapter<GoodsAndWarehouse> {
    private Context context;
    public PlanGoodsAndWarehouseTableDataAdapter(Context context, List<GoodsAndWarehouse> data) {
        super(context, data);
        this.context=context;
    }



    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        GoodsAndWarehouse goodsAndWarehouse = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {

            case 0:
                renderedView = renderGoodsName(goodsAndWarehouse);
                break;
            case 1:
                renderedView = renderGoodsStandard(goodsAndWarehouse);
                break;
            case 2:
                renderedView = renderGoodsPlanLeftNum(goodsAndWarehouse);
                break;
            case 3:
                renderedView = renderGoodsNum(goodsAndWarehouse);
                break;
            case 4:
                renderedView = renderOprater(goodsAndWarehouse);
                break;
        }
        return renderedView;
    }



    private View renderGoodsPlanLeftNum(GoodsAndWarehouse goodsAndWarehouse) {
        return renderString(goodsAndWarehouse.goods.goods_plan_left+"");
    }
    private View renderGoodsPrice(GoodsAndWarehouse goodsAndWarehouse) {
        return renderString(goodsAndWarehouse.goods.goods_price+"");
    }
    private View renderOprater(GoodsAndWarehouse goodsAndWarehouse) {
        return renderString("删除");
    }

    private View renderGoodsNum(GoodsAndWarehouse goodsAndWarehouse) {
        return renderString(goodsAndWarehouse.goods.goods_num+"");
    }

    private View renderGoodsName(GoodsAndWarehouse goodsAndWarehouse) {
        return renderString(goodsAndWarehouse.goods.goods_name);
    }

    private View renderWarehouse(GoodsAndWarehouse goodsAndWarehouse) {
        return renderString(goodsAndWarehouse.warehouse.name);
    }
    private View renderGoodsStandard(GoodsAndWarehouse goodsAndWarehouse) {
        return renderString(goodsAndWarehouse.goods.goods_standard);
    }
    private View renderString(final String value) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_data_view, null);
        TextView textView = (TextView) view.findViewById(R.id.txt);
        textView.setText(value);
        return view;
    }

}
