package com.yunqi.fengle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.ui.adapter.CustomerWholeAdapter;
import com.yunqi.fengle.ui.adapter.CustomerWholeMultiItem;
import com.yunqi.fengle.ui.view.RecycleViewDivider;
import com.yunqi.fengle.ui.view.RecycleViewDividerCustom;
import com.yunqi.fengle.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 00:26
 * @Description: 客户全貌
 */

public class CustomerWholeActivity extends BaseActivity implements CustomerWholeAdapter.CustomerListener {

    @BindView(R.id.rvList)
    RecyclerView rvList;

    CustomerWholeAdapter wholeAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("客户全貌");
        initRecyclerView();
    }

    private void initRecyclerView() {
//        rvList.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.VERTICAL));
//        rvList.addItemDecoration(new RecycleViewDividerCustom(this, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.red_btn_bg_color)));
        rvList.addItemDecoration(new RecycleViewDividerCustom(this, LinearLayoutManager.VERTICAL));
        rvList.setLayoutManager(new LinearLayoutManager(this));

        wholeAdapter = new CustomerWholeAdapter(this, CustomerWholeMultiItem.getMultiItemList());

        wholeAdapter.setListener(this);

//        rvList.addOnItemTouchListener(new OnItemClickListener() {
//            @Override
//            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
//                onViewItemClick(adapter.getItemViewType(position),position);
//            }
//        });

        rvList.setAdapter(wholeAdapter);
    }

    private void onViewItemClick(int itemType,int position) {
        ToastUtil.toast(this,itemType + "");
        Intent mIntent = new Intent();
        if (itemType == CustomerWholeMultiItem.TYPE_VISIT)
        {//拜访
            mIntent.setClass(this, VisitingPlanActivity.class);
            startActivity(mIntent);
        } else if (itemType == CustomerWholeMultiItem.TYPE_EXPENS)
        {//用费

        }
    }


    /**
     * 联系人---->增加联系人
     */
    @Override
    public void onAddContact() {
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

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_customer_whole;
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
