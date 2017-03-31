package com.yunqi.fengle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.response.CustomersResponse;
import com.yunqi.fengle.presenter.MyCustomersPresenter;
import com.yunqi.fengle.presenter.contract.MyCustomersContract;
import com.yunqi.fengle.ui.adapter.MyCustomersAdapter;
import com.yunqi.fengle.ui.view.RecycleViewDivider;
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

public class MyCustomersActivity extends BaseActivity<MyCustomersPresenter> implements MyCustomersContract.View {

    @BindView(R.id.rvMyCustomers)
    RecyclerView rvMyCustomers;

    List<CustomersResponse> dataList = new ArrayList<>();
    MyCustomersAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("我的客户");
//        setTitleRight("添加");
        initRecyclerView();

        initData();
    }

    private void initData() {
        progresser.showProgress();
        mPresenter.getMyCustomers(new ResponseListener() {
            @Override
            public void onSuccess(NetResponse response) {
                dataList = (List<CustomersResponse>) response.getResult();
                adapter.setNewData(dataList);
                progresser.showContent();
            }

            @Override
            public void onFaild(NetResponse response) {
                ToastUtil.toast(mContext, response.getMsg());
                progresser.showContent();
            }
        });
    }

    private void initRecyclerView() {
        rvMyCustomers.setLayoutManager(new LinearLayoutManager(this));
        rvMyCustomers.addItemDecoration(new RecycleViewDivider(mContext, RecycleViewDivider.VERTICAL_LIST));

        rvMyCustomers.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent mIntent = new Intent(MyCustomersActivity.this, CustomerWholeActivity.class);
                MyCustomersActivity.this.startActivity(mIntent);
            }
        });

        adapter = new MyCustomersAdapter(dataList);
        rvMyCustomers.setAdapter(adapter);
    }

    @Override
    protected void onTitleRightClicked(View v) {
        Intent mIntent = new Intent();
        mIntent.setClass(this, NewContactsActivity.class);
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
