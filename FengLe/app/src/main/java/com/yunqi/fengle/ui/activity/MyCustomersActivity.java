package com.yunqi.fengle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.response.CustomersResponse;
import com.yunqi.fengle.presenter.MyCustomersPresenter;
import com.yunqi.fengle.presenter.contract.MyCustomersContract;
import com.yunqi.fengle.ui.adapter.MyCustomersAdapter;
import com.yunqi.fengle.ui.fragment.dialog.SimpleDialogFragment;
import com.yunqi.fengle.ui.swipe.SwipyRefreshLayout;
import com.yunqi.fengle.ui.swipe.SwipyRefreshLayoutDirection;
import com.yunqi.fengle.ui.view.RecycleViewDivider;
import com.yunqi.fengle.ui.view.RecycleViewDividerCustom;
import com.yunqi.fengle.util.DialogHelper;
import com.yunqi.fengle.util.ToastUtil;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author: Huangweicai
 * @date 2017-02-17 23:42
 * @Description:我的客户
 */

public class MyCustomersActivity extends BaseActivity<MyCustomersPresenter> implements MyCustomersContract.View ,SwipyRefreshLayout.OnRefreshListener {

    @BindView(R.id.rvMyCustomers)
    RecyclerView rvMyCustomers;

    public static String TAG = "MyCustomersActivity";

    @BindView(R.id.swipyRefreshLayout)
    SwipyRefreshLayout swipyRefreshLayout;

    int page = 1;
    int pageSize = 20;

    List<CustomersResponse> dataList = new ArrayList<>();
    MyCustomersAdapter adapter;

    SwipyRefreshLayoutDirection refreshType = SwipyRefreshLayoutDirection.TOP ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("我的客户");
//        setTitleRight("添加");
        initRecyclerView();

        progresser.showProgress();
        initData();

        swipyRefreshLayout.setOnRefreshListener(this);

    }

    private void initData() {

        mPresenter.getMyCustomers(page,pageSize,new ResponseListener() {
            @Override
            public void onSuccess(NetResponse response) {
                swipyRefreshLayout.setRefreshing(false);
                if (refreshType == SwipyRefreshLayoutDirection.TOP) {
                    page = 2;
                    dataList = (List<CustomersResponse>) response.getResult();
                    adapter.setNewData(dataList);

                    if (dataList == null || dataList.size() == 0) {
                        progresser.showEmpty();
                    } else {
                        progresser.showContent();
                    }

                } else {
                    List<CustomersResponse> dataList01 = (List<CustomersResponse>) response.getResult();
                    if (dataList01 == null || dataList01.size() == 0) {
                        ToastUtil.toast(mContext, "没有更多数据了!");
                    } else {
                        dataList.addAll(dataList01);
                        adapter.setNewData(dataList);
                        page++;
                    }
                    progresser.showContent();
                }
            }

            @Override
            public void onFaild(NetResponse response) {
                swipyRefreshLayout.setRefreshing(false);
                ToastUtil.toast(mContext, response.getMsg());
                progresser.showError(true);
            }
        });
    }

    @Override
    public void onRetry() {
        initData();
    }

    private void initRecyclerView() {
        rvMyCustomers.setLayoutManager(new LinearLayoutManager(this));
        rvMyCustomers.addItemDecoration(new RecycleViewDivider(mContext, RecycleViewDivider.VERTICAL_LIST));



        rvMyCustomers.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent mIntent = new Intent(MyCustomersActivity.this, CustomerWholeActivity.class);
                mIntent.putExtra(MyCustomersActivity.TAG, dataList.get(position));
                MyCustomersActivity.this.startActivity(mIntent);
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                if (!dataList.get(position).isNeedUp()) {
                    return;
                }
                String msg = dataList.get(position).getMsg();
                DialogHelper.showDialog(MyCustomersActivity.this, msg, new SimpleDialogFragment.OnSimpleDialogListener() {
                    @Override
                    public void onOk() {
                        doUpgrade(position);
                    }
                });
            }
        });

        adapter = new MyCustomersAdapter(dataList);
        rvMyCustomers.addItemDecoration(new RecycleViewDividerCustom(this, LinearLayoutManager.VERTICAL,10));
        rvMyCustomers.setAdapter(adapter);


    }

    /**
     * 升级客户类型
     * @param position
     */
    private void doUpgrade(final int position) {
        final CustomersResponse selectBean = dataList.get(position);
        selectBean.up();
        progresser.showProgress();
        mPresenter.doUpgradeClient(selectBean, new ResponseListener() {
            @Override
            public void onSuccess() {
                progresser.showContent();
                ToastUtil.toast(mContext,"升级成功.");
                adapter.getData().set(position, selectBean);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFaild(NetResponse response) {
                progresser.showContent();
                ToastUtil.toast(mContext,response.getMsg());
            }
        });


    }

    @Override
    protected void onTitleRightClicked(View v) {
        Intent mIntent = new Intent();
        mIntent.setClass(this, NewContactsActivity.class);
        startActivity(mIntent);
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (direction == SwipyRefreshLayoutDirection.TOP) {
            page = 1;
            refreshType = SwipyRefreshLayoutDirection.TOP;
            initData();
        } else if(direction == SwipyRefreshLayoutDirection.BOTTOM){
            initData();
            refreshType = SwipyRefreshLayoutDirection.BOTTOM;
        }
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
        return R.layout.activity_my_customers;
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


}
