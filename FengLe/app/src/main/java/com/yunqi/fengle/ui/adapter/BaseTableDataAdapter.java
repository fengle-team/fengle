package com.yunqi.fengle.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.yunqi.fengle.R;

import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;


public abstract class BaseTableDataAdapter<T> extends TableDataAdapter<T> {
    private Context context;
    public BaseTableDataAdapter(Context context, List<T> data) {
        super(context, data);
        this.context=context;
    }

    @Override
    public void setRowView(int rowIndex,View view) {
        if(rowIndex%2==0){
            view.setBackgroundColor(getResources().getColor(R.color.white));
        }
        else{
            view.setBackgroundColor(getResources().getColor(R.color.bg_color3));
        }
    }

    @Override
    public View getDividerView() {
        LinearLayout.LayoutParams diverLayoutParams = new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT);
        View view=new View(context);
        view.setBackgroundColor(getResources().getColor(R.color.line_bg));
        view.setLayoutParams(diverLayoutParams);
        return view;
    }
}
