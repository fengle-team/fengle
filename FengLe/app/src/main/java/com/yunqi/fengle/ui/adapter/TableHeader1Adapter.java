package com.yunqi.fengle.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunqi.fengle.R;

import de.codecrafters.tableview.TableHeaderAdapter;


public class TableHeader1Adapter extends TableHeaderAdapter {

    private Context context;

    @Override
    public View getHeaderView(int columnIndex, ViewGroup viewGroup) {

        View view = LayoutInflater.from(context).inflate(R.layout.table_header_view, null);
        TextView textView = (TextView) view.findViewById(R.id.txt);
        if (columnIndex < this.headers.length) {
            textView.setText(this.headers[columnIndex]);
        }
        return view;
    }


    private final String[] headers;

    public TableHeader1Adapter(Context context, String... headers) {
        super(context);
        this.context=context;
        this.headers = headers;
    }

    public TableHeader1Adapter(Context context, int... headerStringResources) {
        super(context);
        this.context=context;
        this.headers = new String[headerStringResources.length];

        for (int i = 0; i < headerStringResources.length; ++i) {
            this.headers[i] = context.getString(headerStringResources[i]);
        }

    }

}
