package com.yunqi.fengle.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.Area;
import com.yunqi.fengle.model.bean.Warehouse;
import com.yunqi.fengle.presenter.AreaQueryPresenter;
import com.yunqi.fengle.presenter.WarehouseQueryPresenter;
import com.yunqi.fengle.presenter.contract.AreaQueryContract;
import com.yunqi.fengle.presenter.contract.WarehouseQueryContract;
import com.yunqi.fengle.ui.adapter.AreaAdapter;
import com.yunqi.fengle.ui.adapter.WarehouseAdapter;
import com.yunqi.fengle.ui.view.RecycleViewDivider;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 仓库查询
 */
public class WarehouseQueryActivity extends BaseActivity<WarehouseQueryPresenter> implements WarehouseQueryContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<Warehouse> mListWarehouse = new ArrayList<>();
    private WarehouseAdapter madapter;
    private int selectWarehouseId=-1;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_area_or_warehouse_query;
    }

    @Override
    protected void initEventAndData() {
        selectWarehouseId=getIntent().getIntExtra("selectWarehouseId",-1);
        setToolBar(toolbar, getString(R.string.module_warehouse_query));
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, RecycleViewDivider.VERTICAL_LIST));

        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent();
                intent.putExtra("SelectWarehouse",mListWarehouse.get(position));
                selectWarehouseId=mListWarehouse.get(position).ck_id;
                madapter.setSelectId(selectWarehouseId);
                madapter.notifyDataSetChanged();
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
//        for (int i=0;i<10;i++){
//            mListWarehouse.add(new Warehouse(i,"仓库"+i));
//        }
        madapter = new WarehouseAdapter(mListWarehouse,selectWarehouseId);
        recyclerView.setAdapter(madapter);
        mPresenter.queryWarehouse();

    }




    @Override
    public void showLoading() {
        super.showLoading("加载中...");
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
    public void showContent(List<Warehouse> listWarehouse) {
        if (listWarehouse.isEmpty()) {
            Log.w(TAG, "No data!");
            return;
        }
        mListWarehouse.clear();
        mListWarehouse.addAll(listWarehouse);
        madapter.notifyDataSetChanged();
    }


}
