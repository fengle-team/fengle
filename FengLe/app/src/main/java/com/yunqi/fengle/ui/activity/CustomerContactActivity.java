package com.yunqi.fengle.ui.activity;


import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.CustomerContactDetail;
import com.yunqi.fengle.presenter.CustomerContactPresenter;
import com.yunqi.fengle.presenter.contract.CustomerContactContract;


import butterknife.BindView;


/**
 * 客户往来
 */
public class CustomerContactActivity extends BaseActivity<CustomerContactPresenter> implements CustomerContactContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.custom_name)
    TextView customNname;
    @BindView(R.id.current_delivery)
    TextView currentDelivery;
    @BindView(R.id.current_payment)
    TextView currentPayment;
    @BindView(R.id.trust_rate)
    TextView trustRate;
    @BindView(R.id.real_rate)
    TextView realRate;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_customer_contact;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(toolbar, getString(R.string.module_customer_contact));
        String id=getIntent().getExtras().getString("customerId");
        mPresenter.queryCustomerContact(id);
    }

    @Override
    public void showContent(CustomerContactDetail customerContactDetail) {
        customNname.setText(customerContactDetail.name);
        currentDelivery.setText(customerContactDetail.fahuo_num+"");
        currentPayment.setText(customerContactDetail.huikuan_num+"");
        trustRate.setText(customerContactDetail.xinren_bili+"");
        realRate.setText(customerContactDetail.real_bili+"");

    }
}
