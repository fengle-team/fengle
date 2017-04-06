package com.yunqi.fengle.ui.activity;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.SaleInfo;
import com.yunqi.fengle.presenter.SaleDetailPresenter;
import com.yunqi.fengle.presenter.contract.SaleDetailContract;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 单一货物销售明细
 */
public class GoodsSaleDetailActivity extends BaseActivity<SaleDetailPresenter> implements SaleDetailContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolBar;
    @BindView(R.id.txt_customer_name)
    TextView txtCustomerName;
    @BindView(R.id.txt_stock_name)
    TextView txtStockName;
    @BindView(R.id.txt_begin_count)
    TextView txtBeginCount;
    @BindView(R.id.txt_deliver_tonnage)
    TextView txtDeliverTonnage;
    @BindView(R.id.txt_transfer_tonnage)
    TextView txtTransferTonnage;
    @BindView(R.id.txt_return_tonnage)
    TextView txtReturnTonnage;
    @BindView(R.id.txt_consignment_value)
    TextView txtConsignmentValue;
    @BindView(R.id.txt_billing_amount)
    TextView txtBillingAmount;
    @BindView(R.id.txt_tax_amount)
    TextView txtTaxAmount;
    @BindView(R.id.txt_binlling_tonnage)
    TextView txtBinllingTonnage;
    @BindView(R.id.txt_total_price)
    TextView txtTotalPrice;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_goods_sale_details;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(toolBar, getString(R.string.module_goods_sale_detail));


    }

    private void setWidgetListener() {
    }


    @Override
    public void showLoading() {
    }


    @Override
    public void showError(String msg) {

    }

    @Override
    public void showContent(List<SaleInfo> listSaleInfo) {

    }

    @Override
    public void showMoreContent(List<SaleInfo> listMoreSaleInfo) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
