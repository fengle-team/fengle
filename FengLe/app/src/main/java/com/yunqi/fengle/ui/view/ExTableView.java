package com.yunqi.fengle.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.yunqi.fengle.R;

import de.codecrafters.tableview.TableDataAdapter;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;


/**
 * @author ghcui
 * @time 2017/2/17
 */
public class ExTableView<T> extends RelativeLayout {

    private OnLoadMoreListener loadMoreListener;
    private OnLoadRetryListener loadRetryListener;
    private Context mcontext;
    public TableView tableView;
    Button btnLoadmore;
    private int loadType = 0;//0：表示加载更多 1：表示尝试重新加载

    public ExTableView(Context context, AttributeSet attributes, int styleAttributes) {
        super(context, attributes, styleAttributes);
        this.mcontext = context;
        init();
    }

    public ExTableView(Context context, AttributeSet attributes) {
        super(context, attributes);
        this.mcontext = context;
        init();

    }

    public ExTableView(Context context) {
        super(context);
        this.mcontext = context;
        init();
    }


    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        this.loadMoreListener = listener;
    }

    public void setOnLoadRetryListener(OnLoadRetryListener listener) {
        this.loadRetryListener = listener;
    }

    public void setLoadMoreEnabled(boolean loadMoreEnabled) {
        this.loadType = 0;
        if (loadMoreEnabled) {
            btnLoadmore.setClickable(true);
            btnLoadmore.setText("加载更多");
            btnLoadmore.setAlpha(1.0f);
        } else {
            btnLoadmore.setClickable(false);
            btnLoadmore.setText("已经到最后一页了");
            btnLoadmore.setAlpha(0.7f);
        }
    }


    public void showLoading() {
        btnLoadmore.setText("正在加载...");
        btnLoadmore.setClickable(false);
    }

    public void setEmptyData() {
        btnLoadmore.setText("暂无数据");
        btnLoadmore.setClickable(false);
    }

    public void loadingFail() {
        this.loadType = 1;
        btnLoadmore.setText("加载失败，点击重试...");
        btnLoadmore.setClickable(true);
    }

    private void init() {
        LayoutInflater.from(mcontext).inflate(R.layout.layout_extable, this, true);
        tableView = (TableView) findViewById(R.id.tableView);
        btnLoadmore = (Button) findViewById(R.id.btn_loadmore);
        btnLoadmore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loadType == 1) {
                    if (loadRetryListener != null) {
                        loadRetryListener.onLoadRetry();
                    }
                } else {
                    if (loadMoreListener != null) {
                        loadMoreListener.onLoadMore();
                    }
                }

            }
        });
    }


    public interface OnLoadMoreListener {
        void onLoadMore();

    }

    public interface OnLoadRetryListener {
        void onLoadRetry();

    }

}
