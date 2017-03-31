package com.yunqi.fengle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.response.CustomersResponse;
import com.yunqi.fengle.model.response.CustomersSituationResponse;
import com.yunqi.fengle.model.response.VisitingPlanResponse;
import com.yunqi.fengle.presenter.CustomersSituationPresenter;
import com.yunqi.fengle.ui.adapter.CustomersSituationAdapter;
import com.yunqi.fengle.ui.adapter.VisitingCustomerAdapter;
import com.yunqi.fengle.util.ToastUtil;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author: Huangweicai
 * @date 2017-02-17 17:15
 * @Description:客情维护
 */

public class CustomersSituationActivity extends BaseActivity<CustomersSituationPresenter> implements View.OnClickListener{

    @BindView(R.id.tvViewPlan)
    TextView tvViewPlan;
    @BindView(R.id.rvList)
    RecyclerView rvList;


    CustomersSituationAdapter adapter;

    List<CustomersSituationResponse> dataList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("客情维护");
        setTitleRightImage(R.drawable.right_add);
        initView();
        dataList = new ArrayList<>();
//        initData();
        initRecyclerView();
    }

    private void initData() {


        progresser.showProgress();
        mPresenter.getMaintain(new ResponseListener() {
            @Override
            public void onSuccess(NetResponse response) {
                progresser.showContent();
                dataList = (List<CustomersSituationResponse>) response.getResult();
                adapter.setNewData(dataList);
            }

            @Override
            public void onFaild(NetResponse response) {
                progresser.showContent();
                ToastUtil.toast(mContext,response.getMsg());
            }
        });
    }

    private void initRecyclerView() {
        adapter = new CustomersSituationAdapter(dataList);

        rvList.setLayoutManager(new LinearLayoutManager(mContext));

        rvList.setAdapter(adapter);
    }

    private void initView() {
    }

    @Override
    protected void onTitleRightClicked(View v) {
        Intent mIntent = new Intent();
        mIntent.setClass(this, AddMaintainActivity.class);
        startActivity(mIntent);
    }

    @OnClick(R.id.llView)
    public void viewClick() {
        Intent mIntent = new Intent();
        mIntent.putExtra(VisitingPlanActivity.TAG_FROM, VisitingPlanActivity.TAG_FROM_CUSTOMER_SITUATION);
        mIntent.setClass(CustomersSituationActivity.this, VisitingPlanActivity.class);
        CustomersSituationActivity.this.startActivity(mIntent);
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
        return R.layout.activity_customers_situation;
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
