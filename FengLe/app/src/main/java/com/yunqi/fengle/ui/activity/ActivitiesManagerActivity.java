package com.yunqi.fengle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.yalantis.ucrop.entity.LocalMedia;
import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.UserBean;
import com.yunqi.fengle.ui.adapter.ActivityManagerAdapter;
import com.yunqi.fengle.ui.view.UnderLineEditNewPlanEx;
import com.yunqi.fengle.util.StringUtil;
import com.yunqi.fengle.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author: Huangweicai
 * @date 2017-02-17 09:23
 * @Description:活动管理
 */

public class ActivitiesManagerActivity extends BaseActivity{

    @BindView(R.id.rvList)
    RecyclerView rvList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("活动管理");
        initView();
    }

    private void initView() {
        rvList.setLayoutManager(new GridLayoutManager(this,4));

        rvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                skipNext(position);
            }
        });

        rvList.setAdapter(new ActivityManagerAdapter(ActivityManagerAdapter.getBeanList()));
    }

    @Override
    protected void initInject() {

    }

    private void skipNext(int position) {
        Intent mIntent = new Intent();
        switch (position) {
            case 0://活动计划
                if (App.getInstance().getUserInfo().role_code.equals(UserBean.ROLE_YWY))
                {//业务员
                    mIntent.setClass(ActivitiesManagerActivity.this, ActivityPlanActivity.class);
                } else
                {//经理
                    mIntent.setClass(ActivitiesManagerActivity.this, ActivityPlanManagerActivity.class);
                }
                startActivity(mIntent);
                break;
            case 1://活动总结
                mIntent.setClass(ActivitiesManagerActivity.this, ActivitySummaryActivity.class);
//                ToastUtil.toast(mContext,"呵呵哒");
                startActivity(mIntent);
                break;
            case 2://活动报销
                mIntent.setClass(ActivitiesManagerActivity.this, ActivityExpenseActivity.class);
                startActivity(mIntent);
                break;
        }

    }

    @Override
    protected int getViewModel() {
        return MODE_VIEW_02;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_activities_manager;
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
