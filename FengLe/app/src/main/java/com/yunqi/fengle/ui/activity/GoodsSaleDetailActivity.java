package com.yunqi.fengle.ui.activity;


import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.GoodsSaleDetail;
import com.yunqi.fengle.model.bean.SaleInfo;
import com.yunqi.fengle.presenter.GoodsSaleDetailPresenter;
import com.yunqi.fengle.presenter.contract.GoodsSaleDetailContract;
import butterknife.BindView;


/**
 * 单一货物销售明细
 */
public class GoodsSaleDetailActivity extends BaseActivity<GoodsSaleDetailPresenter> implements GoodsSaleDetailContract.View {


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
        SaleInfo saleInfo= (SaleInfo) getIntent().getExtras().getSerializable("saleInfo");
        showContent(saleInfo);
    }
    public void showContent(SaleInfo saleInfo) {
        txtCustomerName.setText(saleInfo.ccusname);
        txtStockName.setText(saleInfo.存货名称);
        txtBeginCount.setText(saleInfo.期初数量+"");
        txtDeliverTonnage.setText(saleInfo.发货吨位+"");
        txtTransferTonnage.setText(saleInfo.调货吨位+"");
        txtReturnTonnage.setText(saleInfo.退货吨位+"");
        txtConsignmentValue.setText(saleInfo.代销市值+"");
        txtBillingAmount.setText(saleInfo.开票金额+"");
        txtTaxAmount.setText(saleInfo.含税金额+"");
        txtBinllingTonnage.setText(saleInfo.开票吨位+"");
        txtTotalPrice.setText(saleInfo.合计欠结+"");
    }
    @Override
    public void showContent(GoodsSaleDetail goodsSaleDetail) {

    }
}
