package com.yunqi.fengle.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.PaymentType;
import com.yunqi.fengle.presenter.PaymentTypeQueryPresenter;
import com.yunqi.fengle.presenter.contract.PaymentTypeQueryContract;
import com.yunqi.fengle.ui.adapter.PaymentTypeAdapter;
import com.yunqi.fengle.ui.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 回款类型查询
 */
public class PaymentTypeActivity extends BaseActivity<PaymentTypeQueryPresenter> implements PaymentTypeQueryContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<PaymentType> mListPaymentType = new ArrayList<>();
    private PaymentTypeAdapter madapter;
    private int selectPaymentTypeId=-1;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_area_or_warehouse_query;
    }

    @Override
    protected void initEventAndData() {
        selectPaymentTypeId=getIntent().getIntExtra("selectPaymentTypeId",-1);
        setToolBar(toolbar, getString(R.string.module_payment_type));
        initRecyclerView();
        mPresenter.queryPaymentType();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, RecycleViewDivider.VERTICAL_LIST));

        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent();
                intent.putExtra("SelectPaymentType",mListPaymentType.get(position));
                selectPaymentTypeId=mListPaymentType.get(position).id;
                madapter.setSelectPaymentTypeId(selectPaymentTypeId);
                madapter.notifyDataSetChanged();
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
        madapter = new PaymentTypeAdapter(mListPaymentType,selectPaymentTypeId);
        recyclerView.setAdapter(madapter);
    }




    @Override
    public void showLoading() {
        super.showLoading("加载中...");
    }





    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void showContent(List<PaymentType> listPaymentType) {
        if (listPaymentType.isEmpty()) {
            Log.w(TAG, "No data!");
            return;
        }
        mListPaymentType.clear();
        mListPaymentType.addAll(listPaymentType);
        madapter.notifyDataSetChanged();

    }


}
