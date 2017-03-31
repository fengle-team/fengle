package com.yunqi.fengle.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;
import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.Area;
import com.yunqi.fengle.model.bean.Goods;
import com.yunqi.fengle.model.bean.Warehouse;
import com.yunqi.fengle.presenter.StockQueryPresenter;
import com.yunqi.fengle.presenter.contract.StockQueryContract;
import com.yunqi.fengle.ui.adapter.GoodsTableDataAdapter;
import com.yunqi.fengle.ui.adapter.StockTableDataAdapter;
import com.yunqi.fengle.ui.adapter.TableHeader1Adapter;
import com.yunqi.fengle.ui.view.ExTableView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * 库存查询
 */
public class StockQueryActivity extends BaseActivity<StockQueryPresenter> implements StockQueryContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tableViewEx)
    ExTableView tableViewEx;
    @BindView(R.id.edit_keywords)
    EditText editKeywords;
    @BindView(R.id.btn_area_select)
    Button btnAreaSelect;
    @BindView(R.id.btn_stock_select)
    Button btnStockSelect;
    @BindView(R.id.btn_query)
    Button btnQuery;
    private List<Goods> mListGoods = new ArrayList<>();
    private StockTableDataAdapter adapter;
    private int page = 1;
    private Area selectedArea;
    private Warehouse selectedWarehouse;
    private String warehouse_code="";
    private String area_code="";
    private String keyword="";

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_stock_query;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(toolbar, getString(R.string.module_stock_query));
        setWigetListener();
        final TableHeader1Adapter tableHeader1Adapter = new TableHeader1Adapter(this, getResources().getStringArray(R.array.header_title_stock));
        tableViewEx.tableView.setHeaderAdapter(tableHeader1Adapter);
        tableViewEx.tableView.setColumnCount(5);
        tableViewEx.setOnLoadMoreListener(new ExTableView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.queryStock(warehouse_code,area_code,keyword, ++page);
            }
        });
        mPresenter.queryStock(warehouse_code,area_code,keyword, page);
    }

    private void setWigetListener() {
        RxView.clicks(btnQuery)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        page = 1;
                        keyword = editKeywords.getText().toString();
                        mPresenter.queryStock(warehouse_code,area_code,keyword, page);
                    }
                });
        RxView.clicks(btnAreaSelect)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        //跳转到选择大区界面
                        Intent intent = new Intent(StockQueryActivity.this, AreaQueryActivity.class);
                        if (selectedArea != null) {
                            intent.putExtra("selectAreaId", selectedArea.id);
                        }
                        startActivityForResult(intent, 1);

                    }
                });
        RxView.clicks(btnStockSelect)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        //跳转到选择仓库界面
                        Intent intent = new Intent(StockQueryActivity.this, WarehouseQueryActivity.class);
                        if (selectedWarehouse != null) {
                            intent.putExtra("selectWarehouseId", selectedWarehouse.ck_id);
                        }
                        startActivityForResult(intent, 2);
                    }
                });
    }




    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void showContent(List<Goods> listGoods) {
        if (listGoods.isEmpty()) {
            Log.w(TAG, "No data!");
            tableViewEx.setEmptyData();
            mListGoods.clear();
            adapter.notifyDataSetChanged();
            return;
        }
        mListGoods.clear();
        mListGoods.addAll(listGoods);
        tableViewEx.setLoadMoreEnabled(true);
        adapter = new StockTableDataAdapter(this, mListGoods);
        tableViewEx.tableView.setDataAdapter(adapter);
    }

    @Override
    public void showMoreContent(List<Goods> listGoodsMore) {
        if (listGoodsMore.isEmpty()) {
            Log.w(TAG, "No more data!");
            tableViewEx.setLoadMoreEnabled(false);
            return;
        }
        tableViewEx.setLoadMoreEnabled(true);
        mListGoods.addAll(listGoodsMore);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void showLoading() {
        tableViewEx.showLoading();
    }

    @Override
    public void showError(String msg) {
        tableViewEx.loadingFail();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            selectedArea= (Area) data.getSerializableExtra("SelectArea");
            area_code=selectedArea.area_code;
            btnAreaSelect.setText(selectedArea.name);
        } else if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            selectedWarehouse= (Warehouse) data.getSerializableExtra("SelectWarehouse");
            warehouse_code=selectedWarehouse.warehouse_code;
            btnStockSelect.setText(selectedWarehouse.name);
        }
    }
}
