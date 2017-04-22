package com.yunqi.fengle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.SpinnerBean;
import com.yunqi.fengle.model.request.ActivityExpenseRequest;
import com.yunqi.fengle.model.response.ActivityAddResponse;
import com.yunqi.fengle.model.response.ActivitySummaryResponse;
import com.yunqi.fengle.presenter.ActivityExpensePresenter;
import com.yunqi.fengle.presenter.ActivitySummaryPresenter;
import com.yunqi.fengle.presenter.contract.ActivityExpenseContract;
import com.yunqi.fengle.presenter.contract.ActivitySummaryContract;
import com.yunqi.fengle.ui.adapter.ActivityPlanAdapter;
import com.yunqi.fengle.ui.adapter.ActivityPlanSummaryAdapter;
import com.yunqi.fengle.ui.fragment.dialog.SpinnerDialogFragment;
import com.yunqi.fengle.ui.swipe.SwipyRefreshLayout;
import com.yunqi.fengle.ui.swipe.SwipyRefreshLayoutDirection;
import com.yunqi.fengle.ui.view.RecycleViewDivider;
import com.yunqi.fengle.ui.view.TimeSelectDialog;
import com.yunqi.fengle.ui.view.UnderLineEditTextEx;
import com.yunqi.fengle.ui.view.UnderLineTextEx;
import com.yunqi.fengle.util.DialogHelper;
import com.yunqi.fengle.util.ToastUtil;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 17:00
 * @Description: 活动报销 {@link ActivitiesManagerActivity}
 */
public class ActivityExpenseActivity extends BaseActivity<ActivitySummaryPresenter> implements ActivitySummaryContract.View{
    @BindView(R.id.rvList)
    RecyclerView rvList;
    @BindView(R.id.swipe)
    SwipyRefreshLayout swipe;

//    @BindView(R.id.rgRank)
//    RadioGroup rgRank;

    private ActivityPlanSummaryAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("活动报销");

        initView();
        initRadio();

        progresser.showProgress();

        initData();
    }

    private void initRadio() {
//        rgRank.setOnCheckedChangeListener(this);
//        rgRank.check(R.id.rbBtn1);
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

        adapter = new ActivityPlanSummaryAdapter();
        rvList.setAdapter(adapter);

        rvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                onViewItemClick(position);
            }
        });
    }

    private void onViewItemClick(int position) {
        ActivitySummaryResponse response = adapter.getData().get(position);
        Intent mIntent = new Intent();
        mIntent.putExtra(ActivitySummaryDetailActivity.TAG_INFO, response);
        mIntent.setClass(this, ActivityExpenseActivity2.class);
        startActivity(mIntent);
    }

    @Override
    public void onRetry() {
        progresser.showProgress();
        initData();
    }

    private void initData() {
        mPresenter.getSummaryData(new ResponseListener() {
            @Override
            public void onSuccess(NetResponse response) {
                List<ActivitySummaryResponse> responseList = (List<ActivitySummaryResponse>) response.getResult();
                adapter.setNewData(responseList);
                progresser.showContent();
                swipe.setRefreshing(false);
            }

            @Override
            public void onFaild(NetResponse response) {
                progresser.showError(true);
                swipe.setRefreshing(false);
            }
        });
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
}
