package com.yunqi.fengle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.response.ActivityAddResponse;
import com.yunqi.fengle.presenter.ActivityPlanPresenter;
import com.yunqi.fengle.presenter.contract.ActivityPlanContract;
import com.yunqi.fengle.ui.adapter.ActivityPlanAdapter;
import com.yunqi.fengle.ui.adapter.ActivityPlanManagerAdapter;
import com.yunqi.fengle.ui.swipe.SwipyRefreshLayout;
import com.yunqi.fengle.ui.swipe.SwipyRefreshLayoutDirection;
import com.yunqi.fengle.ui.view.RecycleViewDivider;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import java.util.List;

import butterknife.BindView;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 17:00
 * @Description: 活动计划 经理界面
 *              {@link ActivitiesManagerActivity}
 */

public class ActivityPlanManagerActivity extends BaseActivity<ActivityPlanPresenter> implements ActivityPlanContract.View{

    @BindView(R.id.rvList)
    RecyclerView rvList;
    @BindView(R.id.swipe)
    SwipyRefreshLayout swipe;

    private ActivityPlanManagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("活动计划");
        setTitleRightImage(R.drawable.right_add);
        initView();

        progresser.showProgress();
        initData();
    }

    private void initData() {
        mPresenter.showData(new ResponseListener() {
            @Override
            public void onSuccess(NetResponse response) {
                List<ActivityAddResponse> responseList = (List<ActivityAddResponse>) response.getResult();
                adapter.setNewData(responseList);
                progresser.showContent();
                swipe.setRefreshing(false);
            }

            @Override
            public void onFaild(NetResponse response) {
                progresser.showError(response.getMsg());
                swipe.setRefreshing(false);
            }
        });
    }

    private void initView() {
        initRecyclerView();
        swipe.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                initData();
//                swipe.setRefreshing(false);
            }
        });
    }

    private void initRecyclerView() {
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new RecycleViewDivider(this,RecycleViewDivider.VERTICAL_LIST));

        adapter = new ActivityPlanManagerAdapter();
        rvList.setAdapter(adapter);
    }

    @Override
    protected void onTitleRightClicked(View v) {
        Intent mIntent = new Intent(this, ActivityNewPlanActivity.class);
        startActivity(mIntent);
    }

    @Override
    protected int getViewModel() {
        return MODE_VIEW_02;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_recycler_view;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    protected void onDestroy() {
        mPresenter.unSubscribe();
        super.onDestroy();
    }
}
