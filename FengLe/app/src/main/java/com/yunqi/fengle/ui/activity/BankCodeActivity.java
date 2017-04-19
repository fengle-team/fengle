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
import com.yunqi.fengle.model.bean.BankCaption;
import com.yunqi.fengle.model.bean.BankCaption;
import com.yunqi.fengle.presenter.BankCaptionQueryPresenter;
import com.yunqi.fengle.presenter.contract.BankCaptionQueryContract;
import com.yunqi.fengle.ui.adapter.BankCaptionAdapter;
import com.yunqi.fengle.ui.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 会计科目查询
 */
public class BankCodeActivity extends BaseActivity<BankCaptionQueryPresenter> implements BankCaptionQueryContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<BankCaption> mListBankCaption = new ArrayList<>();
    private BankCaptionAdapter madapter;
    private String selectBankCode="";

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
        selectBankCode=getIntent().getStringExtra("selectBankCode");
        setToolBar(toolbar, getString(R.string.module_bank_code));
        initRecyclerView();
        mPresenter.queryBankCaption();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, RecycleViewDivider.VERTICAL_LIST));

        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent();
                intent.putExtra("SelectBankCaption",mListBankCaption.get(position));
                selectBankCode=mListBankCaption.get(position).bank_code;
                madapter.setSelectBankCode(selectBankCode);
                madapter.notifyDataSetChanged();
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
        madapter = new BankCaptionAdapter(mListBankCaption,selectBankCode);
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
    public void showContent(List<BankCaption> listBankCaption) {
        if (listBankCaption.isEmpty()) {
            Log.w(TAG, "No data!");
            return;
        }
        mListBankCaption.clear();
        mListBankCaption.addAll(listBankCaption);
        madapter.notifyDataSetChanged();

    }


}
