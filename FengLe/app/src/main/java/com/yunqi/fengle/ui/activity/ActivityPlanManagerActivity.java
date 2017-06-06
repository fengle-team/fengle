package com.yunqi.fengle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
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
import com.yunqi.fengle.util.RxBus;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 17:00
 * @Description: 活动计划 经理界面
 *              {@link ActivitiesManagerActivity}
 */

public class ActivityPlanManagerActivity extends BaseActivity<ActivityPlanPresenter> implements ActivityPlanContract.View,RadioGroup.OnCheckedChangeListener{

    private final String STATUS_1 = "1";//暂存
    private final String STATUS_2 = "2";//提交待处理
    private final String STATUS_3 = "3";//审核通过
    private final String STATUS_4 = "4";//驳回

    //1=暂存 2=提交待处理 3=审核通过 4=驳回
    private String status = STATUS_1;

    @BindView(R.id.rgRank)
    RadioGroup rgRank;

    @BindView(R.id.rvList)
    RecyclerView rvList;
    @BindView(R.id.swipe)
    SwipyRefreshLayout swipe;

    private ActivityPlanManagerAdapter adapter;

    Observable<Boolean> addOb;

    public static String RX_TAG = "ActivityPlanManagerActivityTAG";

    List<ActivityAddResponse> responseList;

    private String customerCode = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("活动计划");
        initView();

        if (getIntent().hasExtra("custom_code")) {
            customerCode = getIntent().getStringExtra("custom_code");
        } else {
            setTitleRightImage(R.drawable.right_add);
        }

        progresser.showProgress();
        initRadio();

        addOb = RxBus.get().register(ActivityPlanManagerActivity.RX_TAG, Boolean.class);
        addOb.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    swipe.setRefreshing(true);
                    progresser.showProgress();
                    initData();
                }
            }
        });
    }

    private void initRadio() {
        rgRank.setOnCheckedChangeListener(this);
        rgRank.check(R.id.radioBtn1);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        int id = group.getCheckedRadioButtonId();
        switch (id) {
            case R.id.radioBtn1://待处理
                status = STATUS_1;
                adapter.setSelectStatus(1);
                break;
            case R.id.radioBtn2://未完成
                status = STATUS_2;
                adapter.setSelectStatus(2);
                break;
            case R.id.radioBtn3://历史单据
                status = STATUS_3;
                adapter.setSelectStatus(3);
                break;
        }
        progresser.showProgress();
        initData();
    }

    private void initData() {
        mPresenter.showData(status,customerCode,"","",new ResponseListener() {
            @Override
            public void onSuccess(NetResponse response) {
                responseList = (List<ActivityAddResponse>) response.getResult();
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
//        rvList.addItemDecoration(new RecycleViewDivider(this,RecycleViewDivider.VERTICAL_LIST));

        adapter = new ActivityPlanManagerAdapter();

        rvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter1, View view, int position) {
                if (adapter.getSelectStatus() == 1) {
                    Intent mIntent = new Intent();
                    mIntent.setClass(ActivityPlanManagerActivity.this, ActivityPlanUpdateActivity.class);
                    mIntent.putExtra(ActivityPlanUpdateActivity.TAG, responseList.get(position));
                    ActivityPlanManagerActivity.this.startActivity(mIntent);
                }
            }
        });

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
        return R.layout.activity_recycler_view2;
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
        RxBus.get().unregister(ActivityPlanManagerActivity.RX_TAG, addOb);
        mPresenter.unSubscribe();
        super.onDestroy();
    }
}
