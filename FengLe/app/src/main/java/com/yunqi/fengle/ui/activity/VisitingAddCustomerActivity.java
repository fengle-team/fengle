package com.yunqi.fengle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.response.CustomersResponse;
import com.yunqi.fengle.presenter.VisitingAddCustomerPresenter;
import com.yunqi.fengle.presenter.VisitingPlanPresenter;
import com.yunqi.fengle.presenter.contract.VisitingAddCustomerContract;
import com.yunqi.fengle.ui.adapter.GoodsTableDataAdapter;
import com.yunqi.fengle.ui.adapter.TableHeader1Adapter;
import com.yunqi.fengle.ui.adapter.VisitingCustomerAdapter;
import com.yunqi.fengle.ui.fragment.dialog.SimpleDialogFragment;
import com.yunqi.fengle.util.DialogHelper;
import com.yunqi.fengle.util.ToastUtil;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.codecrafters.tableview.TableView;

/**
 * @Author: Huangweicai
 * @date 2017-02-17 14:19
 * @Description:(这里用一句话描述这个类的作用)
 */

public class VisitingAddCustomerActivity extends BaseActivity<VisitingAddCustomerPresenter> implements VisitingAddCustomerContract.View{
    @BindView(R.id.tableView)
    TableView mTableView;

    private List<CustomersResponse> dataList = new ArrayList<>();

    private VisitingCustomerAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("我的客户");
        setTitleRight("提交");

        initView();
        initData();
    }

    private void initData() {
        progresser.showProgress();
        mPresenter.getMyCustomer(new ResponseListener() {
            @Override
            public void onSuccess(NetResponse response) {
                dataList = (List<CustomersResponse>) response.getResult();
                setDataChange(dataList);
                progresser.showContent();
            }

            @Override
            public void onFaild(NetResponse response) {
                ToastUtil.toast(mContext,response.getMsg());
                progresser.showContent();
            }
        });
    }

    private void initView() {
        mTableView.setHeaderAdapter(new TableHeader1Adapter(this,"客户名称","公司","职位","选择"));

        adapter = new VisitingCustomerAdapter(this, dataList);

        mTableView.setDataAdapter(adapter);
    }

    private void setDataChange(List<CustomersResponse> dataList) {
        adapter = new VisitingCustomerAdapter(this, dataList);
        mTableView.setDataAdapter(adapter);
    }

    @Override
    protected void onTitleRightClicked(View v) {
        DialogHelper.showDialog(this, "你确定提交?", new SimpleDialogFragment.OnSimpleDialogListener() {
            @Override
            public void onOk() {
                ArrayList<CustomersResponse> selectedData = adapter.getSelectedData();
                if (selectedData == null || selectedData.size() == 0) {
                    ToastUtil.toast(mContext,"请选择客户");
                    return;
                }
                Intent mIntent = new Intent();
                mIntent.putExtra(VistingAddVisteActivity.TAG_SELECTED, selectedData);
                mIntent.setClass(VisitingAddCustomerActivity.this, VistingAddVisteActivity.class);
                VisitingAddCustomerActivity.this.startActivity(mIntent);
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
        return R.layout.activity_visiting_addcustomer;
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
