package com.yunqi.fengle.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.model.response.RegionalRankingResponse;
import com.yunqi.fengle.model.response.SaleRankingResponse;

import java.util.List;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 14:12:33
 * @Description: 业务员
 */

public class SaleHeaderAdapter extends BaseTableDataAdapter<SaleRankingResponse> {

    private Context mContext;

    private List<SaleRankingResponse> data;


    public SaleHeaderAdapter(Context context, List<SaleRankingResponse> data) {
        super(context, data);
        this.mContext=context;
        this.data = data;
    }



    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        SaleRankingResponse response = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0:
                renderedView = renderString(response.getUser_info().getReal_name());
                break;
            case 1:
                renderedView = renderString(response.getGoal_num() + "");
                break;
            case 2:
                renderedView = renderString(response.getFh_amount_sum() + "");
                break;
            case 3:
                renderedView = renderString(response.getHuikuan_amount_sum() + "");
                break;
            case 4:
                renderedView = renderString(response.getRank() + "");
                break;
            case 5:
                renderedView = renderString(response.getCompletion_rate() + "");
                break;
        }

        return renderedView;
    }

    private View renderString(final String value) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.table_data_view, null);
        TextView textView = (TextView) view.findViewById(R.id.txt);
        textView.setText(value);
        return view;
    }

}
