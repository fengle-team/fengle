package com.yunqi.fengle.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import de.codecrafters.tableview.TableDataAdapter;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.OnScrollListener;


/**
 * @author ghcui
 * @time 2017/2/17
 */
public class TableViewEx<T> extends TableView<T> {

    private OnLoadMoreListener listener;
    private int lastItem;
    private boolean loadMoreEnabled=true;

    public TableViewEx(Context context, AttributeSet attributes, int styleAttributes) {
        super(context, attributes, styleAttributes);
        init();
    }

    public TableViewEx(Context context) {
        super(context);
        init();

    }

    @Override
    public void setDataAdapter(TableDataAdapter<T> dataAdapter) {
        super.setDataAdapter(dataAdapter);
    }



    public void setViewHeightBasedOnChildren(TableDataAdapter dataAdapter){
        setListViewHeightBasedOnChildren(dataAdapter);
    }


    public TableViewEx(Context context, AttributeSet attributes) {
        super(context, attributes);
        init();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.listener = listener;
    }

    public void setLoadMoreEnabled(boolean loadMoreEnabled) {
        this.loadMoreEnabled = loadMoreEnabled;
    }


    private void init() {

    }

    public  void setListViewHeightBasedOnChildren(TableDataAdapter adapter) {
        if (adapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, null);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        totalHeight+=50;
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = totalHeight + (1 * (adapter.getCount() - 1));
        this.setLayoutParams(params);
    }




    public  interface OnLoadMoreListener {
        void onLoadMore();

    }

}
