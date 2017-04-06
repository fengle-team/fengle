package com.yunqi.fengle.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.yunqi.fengle.R;

import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 14:12:33
 * @Description: 大区排名 {@link com.yunqi.fengle.ui.fragment.RegionalRankingFragment}
 */

public class RegionalHeaderAdapter extends BaseTableDataAdapter<Object> {

    private Context mContext;

    private List<Object> data;


    public RegionalHeaderAdapter(Context context, List<Object> data) {
        super(context, data);
        this.mContext=context;
        this.data = data;
    }



    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        View renderedView = null;

         renderedView = renderString("测试");

        return renderedView;
    }

    private View renderString(final String value) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.table_data_view, null);
        TextView textView = (TextView) view.findViewById(R.id.txt);
        textView.setText(value);
        return view;
    }

}
