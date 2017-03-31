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
import com.yunqi.fengle.presenter.AreaQueryPresenter;
import com.yunqi.fengle.presenter.contract.AreaQueryContract;
import com.yunqi.fengle.ui.adapter.AreaAdapter;
import com.yunqi.fengle.ui.view.RecycleViewDivider;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 大区查询
 */
public class AreaQueryActivity extends BaseActivity<AreaQueryPresenter> implements AreaQueryContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<Area> mListArea = new ArrayList<>();
    private AreaAdapter madapter;
    private int selectAreaId=-1;

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
        selectAreaId=getIntent().getIntExtra("selectAreaId",-1);
        setToolBar(toolbar, getString(R.string.module_area_query));
        initRecyclerView();
        mPresenter.queryArea();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, RecycleViewDivider.VERTICAL_LIST));

        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent();
                intent.putExtra("SelectArea",mListArea.get(position));
                selectAreaId=mListArea.get(position).id;
                madapter.setSelectAreaId(selectAreaId);
                madapter.notifyDataSetChanged();
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
//        for (int i=0;i<10;i++){
//            mListArea.add(new Area(i,"区域"+i));
//        }
        madapter = new AreaAdapter(mListArea,selectAreaId);
        recyclerView.setAdapter(madapter);
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
    public void showContent(List<Area> listArea) {
        if (listArea.isEmpty()) {
            Log.w(TAG, "No data!");
            return;
        }
        mListArea.clear();
        mListArea.addAll(listArea);
        madapter.notifyDataSetChanged();

    }


}
