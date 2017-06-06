package com.yunqi.fengle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.PictureConfig;
import com.yalantis.ucrop.entity.LocalMedia;
import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.Area;
import com.yunqi.fengle.model.bean.SpinnerBean;
import com.yunqi.fengle.model.request.ActivitySummaryRequest;
import com.yunqi.fengle.model.request.TypeRequest;
import com.yunqi.fengle.model.response.ActivityAddResponse;
import com.yunqi.fengle.model.response.CustomersResponse;
import com.yunqi.fengle.presenter.ActivitySummaryPresenter;
import com.yunqi.fengle.ui.adapter.ActivityPlanAdapter;
import com.yunqi.fengle.ui.adapter.GridImageAdapter;
import com.yunqi.fengle.ui.fragment.dialog.SpinnerDialogFragment;
import com.yunqi.fengle.ui.swipe.SwipyRefreshLayout;
import com.yunqi.fengle.ui.swipe.SwipyRefreshLayoutDirection;
import com.yunqi.fengle.ui.view.FullyGridLayoutManager;
import com.yunqi.fengle.ui.view.RecycleViewDivider;
import com.yunqi.fengle.ui.view.TimeSelectDialog;
import com.yunqi.fengle.ui.view.UnderLineEditNewPlanEx;
import com.yunqi.fengle.ui.view.UnderLineTextNewPlanEx;
import com.yunqi.fengle.util.DateUtil;
import com.yunqi.fengle.util.DialogHelper;
import com.yunqi.fengle.util.LogEx;
import com.yunqi.fengle.util.StringUtil;
import com.yunqi.fengle.util.ToastUtil;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 17:00
 * @Description: 活动总结 {@link ActivitiesManagerActivity}
 */

public class ActivitySummaryActivity extends BaseActivity<ActivitySummaryPresenter> implements RadioGroup.OnCheckedChangeListener{

    @BindView(R.id.rvList)
    RecyclerView rvList;
    @BindView(R.id.swipe)
    SwipyRefreshLayout swipe;

//    @BindView(R.id.rgRank)
//    RadioGroup rgRank;

    private ActivityPlanAdapter adapter;

    private final String STATUS_1 = "1";//暂存
    private final String STATUS_2 = "2";//提交待处理
    private final String STATUS_3 = "3";//审核通过
    private final String STATUS_4 = "4";//驳回

    //1=暂存 2=提交待处理 3=审核通过 4=驳回
    private String status = STATUS_3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("活动总结");

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

        adapter = new ActivityPlanAdapter();
        rvList.setAdapter(adapter);

        rvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                onViewItemClick(position);
            }
        });
    }

    private void onViewItemClick(int position) {
        ActivityAddResponse response = adapter.getData().get(position);
        Intent mIntent = new Intent();
        mIntent.putExtra(ActivitySummaryDetailActivity.TAG_INFO, response);
        mIntent.setClass(this, ActivitySummaryDetailActivity.class);
        startActivity(mIntent);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        int id = group.getCheckedRadioButtonId();
        switch (id) {
            case R.id.radioBtn1://待处理
                status = STATUS_1;
                break;
            case R.id.radioBtn2://未完成
                status = STATUS_2;
                break;
            case R.id.radioBtn3://历史单据
                status = STATUS_3;
                break;
        }
        progresser.showProgress();
        initData();
    }

    @Override
    public void onRetry() {
        progresser.showProgress();
        initData();
    }

    private void initData() {
        mPresenter.showData(status,new ResponseListener() {
            @Override
            public void onSuccess(NetResponse response) {
                List<ActivityAddResponse> responseList = (List<ActivityAddResponse>) response.getResult();
                adapter.setNewData(responseList);
                if (responseList == null || responseList.size() == 0) {
                    progresser.showEmpty();
                } else {
                    progresser.showContent();
                }
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
