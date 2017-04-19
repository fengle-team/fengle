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
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.response.CustomerWholeResponse;
import com.yunqi.fengle.model.response.CustomersResponse;
import com.yunqi.fengle.presenter.CustomerWholePresenter;
import com.yunqi.fengle.presenter.contract.CustomerWholeContract;
import com.yunqi.fengle.ui.adapter.CustomerWholeAdapter;
import com.yunqi.fengle.ui.adapter.CustomerWholeMultiItem;
import com.yunqi.fengle.ui.view.RecycleViewDividerCustom;
import com.yunqi.fengle.util.ToastUtil;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import org.json.JSONObject;

import butterknife.BindView;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 00:26
 * @Description: 客户全貌
 */

public class CustomerWholeActivity extends BaseActivity<CustomerWholePresenter> implements CustomerWholeAdapter.CustomerListener, CustomerWholeContract.View {

    public static final String TAG = "custom_code";

    @BindView(R.id.rvList)
    RecyclerView rvList;

    CustomerWholeAdapter wholeAdapter;

    /** 上个界面传过来的 {@link MyCustomersActivity}*/
    CustomersResponse customerModel;

    CustomerWholeResponse customerWholeResponse = new CustomerWholeResponse();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("客户全貌");
        initRecyclerView();

        initData();
    }

    private void initData() {

        if (!getIntent().hasExtra(MyCustomersActivity.TAG)) {
            ToastUtil.toast(mContext,"null");
            return;
        }

        customerModel = (CustomersResponse) getIntent().getSerializableExtra(MyCustomersActivity.TAG);

        progresser.showProgress();

        mPresenter.getData(App.getInstance().getUserInfo().id + "", customerModel.getCustom_code(), new ResponseListener() {
            @Override
            public void onSuccess(NetResponse response) {
                progresser.showContent();
                customerWholeResponse = (CustomerWholeResponse) response.getResult();
                System.out.print("呵呵哒");
                wholeAdapter.setNewData(CustomerWholeResponse.getMultiItemList(customerWholeResponse));
            }

            @Override
            public void onFaild(NetResponse response) {
                progresser.showError(true);
                ToastUtil.toast(mContext,response.getMsg());
            }
        });
    }

    @Override
    public void onRetry() {
        initData();
    }

    private void initRecyclerView() {
//        rvList.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.VERTICAL));
//        rvList.addItemDecoration(new RecycleViewDividerCustom(this, LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.red_btn_bg_color)));
        rvList.addItemDecoration(new RecycleViewDividerCustom(this, LinearLayoutManager.VERTICAL));
        rvList.setLayoutManager(new LinearLayoutManager(this));

        wholeAdapter = new CustomerWholeAdapter(this, CustomerWholeResponse.getMultiItemList(customerWholeResponse));

        wholeAdapter.setListener(this);

        rvList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                onViewItemClick(adapter.getItemViewType(position),position);
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

        rvList.setAdapter(wholeAdapter);
    }

    private void onViewItemClick(int itemType,int position) {
        Intent mIntent = new Intent();
        mIntent.putExtra(TAG, customerWholeResponse.getCustom_code());
        if (itemType == CustomerWholeMultiItem.TYPE_VISIT)
        {//拜访
            if (customerWholeResponse.getVisit_plan().getVisite_plan_count() > 0) {
                mIntent.setClass(this, VisitingPlanActivity.class);
                startActivity(mIntent);
            }
        } else if(itemType == CustomerWholeMultiItem.TYPE_REFUND)
        {//退款
            if (customerWholeResponse.getTuihuo_info().getTuihuo_count() > 0) {
                mIntent.setClass(this, ReturnRequestActivity.class);
                startActivity(mIntent);
            }
        } else if(itemType == CustomerWholeMultiItem.TYPE_RETURN)
        {//回款
            if (customerWholeResponse.getHuikuan_info().getHuikuan_count() > 0) {
                mIntent.setClass(this, PaymentQueryActivity.class);
                startActivity(mIntent);
            }
        } else if(itemType == CustomerWholeMultiItem.TYPE_INVOICE)
        {//开票
            if (customerWholeResponse.getInvoce_info().getInvoice_count() > 0) {
                mIntent.setClass(this, BillingRequestActivity.class);
                startActivity(mIntent);
            }
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
        getActivityComponent().inject(this);
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
