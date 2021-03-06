package com.yunqi.fengle.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.Customer;
import com.yunqi.fengle.model.response.VisitingPlanResponse;
import com.yunqi.fengle.presenter.VisitingPlanPresenter;
import com.yunqi.fengle.presenter.contract.VisitingPlanContract;
import com.yunqi.fengle.ui.adapter.VisitingMainAdapter;
import com.yunqi.fengle.ui.swipe.SwipyRefreshLayout;
import com.yunqi.fengle.ui.swipe.SwipyRefreshLayoutDirection;
import com.yunqi.fengle.ui.view.RecycleViewDivider;
import com.yunqi.fengle.util.RxBus;
import com.yunqi.fengle.util.ToastUtil;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * @Author: Huangweicai
 * @date 2017-02-17 09:23
 * @Description:拜访计划
 */

public class VisitingPlanActivity extends BaseActivity<VisitingPlanPresenter> implements VisitingPlanContract.View,SwipyRefreshLayout.OnRefreshListener {

    public static String TAG_FROM = "tagFrom";
    public static String TAG_FROM_SELF = "self";
    public static String TAG_FROM_CUSTOMER_SITUATION = "customersSituation";

    Observable<Boolean> addOb;

    @BindView(R.id.rvVisPlan)
    RecyclerView rvVisPlan;

    @BindView(R.id.swipyRefreshLayout)
    SwipyRefreshLayout swipyRefreshLayout;

    private List<VisitingPlanResponse> responseList = new ArrayList<>();
    private VisitingMainAdapter adapter;

    /** 上个界面传过来的code*/
    String customerCode = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("拜访计划");

        customerCode = getIntent().getStringExtra(CustomerWholeActivity.TAG);//是否从客户全貌跳转过来

        if (!TAG_FROM_CUSTOMER_SITUATION.equals(getIntent().getStringExtra(TAG_FROM))) {
            if (TextUtils.isEmpty(customerCode)) {
                setTitleRightImage(R.drawable.right_add);
            }
        }
        initRecyclerView();
        swipyRefreshLayout.setOnRefreshListener(this);




        progresser.showProgress();
        initData();

        addOb = RxBus.get().register(VistingAddVisteActivity.RX_TAG, Boolean.class);
        addOb.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    swipyRefreshLayout.setRefreshing(true);
                    initData();
                }
            }
        });
    }


    private void initRecyclerView() {
        rvVisPlan.setLayoutManager(new LinearLayoutManager(this));
        rvVisPlan.addItemDecoration(new RecycleViewDivider(mContext,RecycleViewDivider.VERTICAL_LIST));

        rvVisPlan.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                VisitingPlanResponse item= responseList.get(position);
                if(item.getStatus() == VisitingPlanResponse.STATUS_CONFIRM_YES){
                    return;
                }
                onViewItemClick(position);
            }
        });

        adapter = new VisitingMainAdapter(responseList);
        rvVisPlan.setAdapter(adapter);
    }

    private void initData() {
        mPresenter.getVisitingPlanList(customerCode,new ResponseListener() {
            @Override
            public void onSuccess(NetResponse response) {
                swipyRefreshLayout.setRefreshing(false);
                responseList = (List<VisitingPlanResponse>) response.getResult();
                adapter.setNewData(responseList);

                if (responseList == null || responseList.size() == 0) {
                    progresser.showEmpty();
                } else {
                    progresser.showContent();
                }
            }

            @Override
            public void onFaild(NetResponse response) {
                progresser.showError(true);
                ToastUtil.toast(mContext, response.getMsg());
            }
        });
    }

    @Override
    public void onRetry() {
        progresser.showProgress();
        initData();
    }

    private void onViewItemClick(int position) {

        if (TAG_FROM_CUSTOMER_SITUATION.equals(getIntent().getStringExtra(TAG_FROM)))
        {//客情维护跳过来
            Intent mIntent = new Intent();
            mIntent.putExtra(AddMaintainActivity.TAG_NAME, responseList.get(position).getClient_name());
            mIntent.setClass(this, AddMaintainActivity.class);
            startActivity(mIntent);
            return;
        }

        Intent mIntent = new Intent();
        mIntent.putExtra(ActivityPlanDetailActivity.TAG_DETAIL, responseList.get(position));
        mIntent.setClass(this, ActivityPlanDetailActivity.class);
        startActivity(mIntent);

        //        ToastUtil.toast(VisitingPlanActivity.this,"click");
//        Intent mIntent = new Intent();
//        if (TAG_FROM_CUSTOMER_SITUATION.equals(getIntent().getStringExtra(TAG_FROM)))
//        {//客情维护跳转过来的
//            mIntent.setClass(VisitingPlanActivity.this, AddMaintainActivity.class);
//        } else {
////            mIntent.setClass(VisitingPlanActivity.this, VisitingAddCustomerActivity.class);
//        }
//
    }



    @Override
    protected void onTitleRightClicked(View v) {
        skipActivity();
    }

    private void skipActivity() {
        Intent mIntent = new Intent();
        if (TAG_FROM_CUSTOMER_SITUATION.equals(getIntent().getStringExtra(TAG_FROM)))
        {//客情维护跳转过来的
            mIntent.setClass(VisitingPlanActivity.this, AddMaintainActivity.class);
            startActivity(mIntent);
        } else {

            mIntent.putExtra(VistingAddVisteActivity.TAG_SELECTED, selectCustomer);
            mIntent.setClass(this, VistingAddVisteActivity.class);
            this.startActivity(mIntent);

//            Intent intent = new Intent(this, CustomerQueryActivity.class);
//            intent.putExtra("module", 1);
//            startActivityForResult(intent, 1);
//            mIntent.setClass(VisitingPlanActivity.this, VisitingAddCustomerActivity2.class);
        }

    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if (direction == SwipyRefreshLayoutDirection.TOP) {
            initData();
        }
    }

    Customer selectCustomer;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            selectCustomer = (Customer) data.getSerializableExtra("customer");
            Intent mIntent = new Intent();
            mIntent.putExtra(VistingAddVisteActivity.TAG_SELECTED, selectCustomer);
            mIntent.setClass(this, VistingAddVisteActivity.class);
            this.startActivity(mIntent);
        }
    }

    @Override
    protected int getViewModel() {
        return MODE_VIEW_02;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_visiting_plan;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void onDestroy() {
        if (addOb != null) {
            RxBus.get().unregister(VistingAddVisteActivity.RX_TAG,addOb);
        }
        super.onDestroy();
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
