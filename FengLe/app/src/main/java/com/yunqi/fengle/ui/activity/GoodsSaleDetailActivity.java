package com.yunqi.fengle.ui.activity;


import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.GoodsSaleDetail;
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
        String sale_id=getIntent().getExtras().getString("sale_id");
        mPresenter.getGoodsSaleDetail(sale_id);
    }
    @Override
    public void showContent(GoodsSaleDetail goodsSaleDetail) {

    }
}
