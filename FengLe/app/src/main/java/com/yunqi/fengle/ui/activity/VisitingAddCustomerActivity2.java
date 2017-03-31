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
import com.yunqi.fengle.presenter.VisitingAddCustomerPresenter;
import com.yunqi.fengle.presenter.contract.VisitingAddCustomerContract;
import com.yunqi.fengle.ui.adapter.VisitingAddCustomerAdapter;
import com.yunqi.fengle.ui.fragment.dialog.SimpleDialogFragment;
import com.yunqi.fengle.ui.view.RecycleViewDivider;
import com.yunqi.fengle.util.DialogHelper;
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
 * @date 2017-02-17 14:19
 * @Description:(这里用一句话描述这个类的作用)
 */

public class VisitingAddCustomerActivity2 extends BaseActivity<VisitingAddCustomerPresenter> implements VisitingAddCustomerContract.View {

    private List<CustomersResponse> dataList = new ArrayList<>();

    //    private VisitingCustomerAdapter adapter;
    Observable<Boolean> addOb;


    private VisitingAddCustomerAdapter adapter;

    @BindView(R.id.rvList)
    RecyclerView rvList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("我的客户");
//        setTitleRight("提交");

        initView();
        initData();

        addOb = RxBus.get().register(VistingAddVisteActivity.RX_TAG, Boolean.class);
        addOb.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    finish();
                }
            }
        });
    }

    private void initData() {
        progresser.showProgress();
        mPresenter.getMyCustomer(new ResponseListener() {
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

    private void initView() {
        adapter = new VisitingAddCustomerAdapter(dataList);

        rvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent mIntent = new Intent();
                mIntent.putExtra(VistingAddVisteActivity.TAG_SELECTED, dataList.get(position));
                mIntent.setClass(VisitingAddCustomerActivity2.this, VistingAddVisteActivity.class);
                VisitingAddCustomerActivity2.this.startActivity(mIntent);
            }
        });

        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        rvList.addItemDecoration(new RecycleViewDivider(mContext, RecycleViewDivider.VERTICAL_LIST));
        rvList.setAdapter(adapter);
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
    protected void onDestroy() {
        if (addOb != null) {
            RxBus.get().unregister(VistingAddVisteActivity.RX_TAG,addOb);
        }
        super.onDestroy();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_visiting_addcustomer2;
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
