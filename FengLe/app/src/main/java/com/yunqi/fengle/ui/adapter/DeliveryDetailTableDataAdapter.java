package com.yunqi.fengle.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.model.bean.DeliveryDetail;
import com.yunqi.fengle.model.bean.InvoiceApply;
import com.yunqi.fengle.util.TimeUtil;

import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;


public class DeliveryDetailTableDataAdapter extends BaseTableDataAdapter<DeliveryDetail> {
    private Context context;

    public DeliveryDetailTableDataAdapter(Context context, List<DeliveryDetail> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        DeliveryDetail deliveryDetail = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderGoodName(deliveryDetail);
                break;
            case 1:
                renderedView = renderGoodStandard(deliveryDetail);
                break;
            case 2:
                renderedView = renderGoodNum(deliveryDetail);
                break;
            case 3:
                renderedView = renderGoodsUnitsNum(deliveryDetail);
                break;
            case 4:
                renderedView = renderOperater(deliveryDetail);
                break;
        }
        return renderedView;
    }


    private View renderOperater(DeliveryDetail deliveryDetail) {
        return renderString("删除");
    }

    private View renderGoodsUnitsNum(DeliveryDetail deliveryDetail) {
        if (deliveryDetail.goods_units_num > 0) {
            return renderString(deliveryDetail.goods_units_num + "");
        } else {
            return renderString("");
        }

    }

    private View renderGoodNum(DeliveryDetail deliveryDetail) {
        return renderString(deliveryDetail.goods_num + "");
    }

    private View renderGoodName(DeliveryDetail deliveryDetail) {
        return renderString(deliveryDetail.goods_name);
    }

    private View renderGoodStandard(DeliveryDetail deliveryDetail) {
        return renderString(deliveryDetail.goods_standard);
    }

    private View renderWarehouse(DeliveryDetail deliveryDetail) {
        return renderString(deliveryDetail.goods_warehouse);
    }

    private View renderString(final String value) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_data_view, null);
        TextView textView = (TextView) view.findViewById(R.id.txt);
        textView.setText(value);
        return view;
    }


}
