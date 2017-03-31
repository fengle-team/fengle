package com.yunqi.fengle.ui.activity;


import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.APSService;
import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.component.ImageLoader;
import com.yunqi.fengle.model.bean.Payment;
import com.yunqi.fengle.model.http.ApiService;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.model.request.PaymentAddRequest;
import com.yunqi.fengle.presenter.PaymentDeclarationDetailsPresenter;
import com.yunqi.fengle.presenter.contract.AddPaymentDeclarationContract;
import com.yunqi.fengle.presenter.contract.PaymentDeclarationDetailsContract;
import com.yunqi.fengle.ui.view.BottomOpraterPopWindow;
import com.yunqi.fengle.util.ToastUtil;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 回款申报详情
 */
public class PaymentDeclarationDetailsActivity extends BaseActivity<PaymentDeclarationDetailsPresenter> implements PaymentDeclarationDetailsContract.View, View.OnFocusChangeListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txt_customer)
    TextView txtCustomer;
    @BindView(R.id.txt_remittance_date)
    TextView txtRemittanceDate;
    @BindView(R.id.txt_remitter)
    TextView txtRemitter;
    @BindView(R.id.txt_amount)
    TextView txtAmount;

    @BindView(R.id.img_show)
    ImageView imgShow;
    private BottomOpraterPopWindow popWindow;
    private SweetAlertDialog loadingDialog;
    private int id;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_payment_declaration_details;
    }

    @Override
    protected void initEventAndData() {
        id = getIntent().getExtras().getInt("id");
        Button btnRight = (Button) toolbar.findViewById(R.id.btn_right);
        btnRight.setText(R.string.operater);
        btnRight.setVisibility(View.VISIBLE);
        setToolBar(toolbar, getString(R.string.module_payment_declaration_details));
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomOpraterPopWindow();
            }
        });
        mPresenter.getPaymentDeclarationDetails(id);
    }


    @Override
    public void showLoading() {
        loadingDialog = new SweetAlertDialog(PaymentDeclarationDetailsActivity.this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("加载中...");
        loadingDialog.show();
        loadingDialog.setCancelable(false);
    }

    @Override
    public void cancelLoading() {
        loadingDialog.dismiss();
    }

    @Override
    public void showError(String msg) {
        ToastUtil.showErrorToast(this, msg);
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
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {

        }
    }


    /**
     * 弹出底部操作PopupWindow
     */
    public void showBottomOpraterPopWindow() {
        popWindow = new BottomOpraterPopWindow(this, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 隐藏弹出窗口
                popWindow.dismiss();
                switch (v.getId()) {
                    case R.id.btn_commit: {
                        // 提交
                        mPresenter.updateStatus(id, 2);
                    }
                    break;

                    case R.id.btn_temporary: {
                        // 暂存
                        mPresenter.updateStatus(id, 1);
                    }
                    break;
                    case R.id.btn_cancel:// 放弃
                        break;
                    default:
                        break;
                }
            }
        });
        popWindow.showAtLocation(findViewById(R.id.main_layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public void showContent(Payment payment) {
        txtCustomer.setText(payment.clientName);
        txtRemittanceDate.setText(payment.huikuan_time);
        txtRemitter.setText(payment.huikuan_name);
        txtAmount.setText(payment.huikuan_amount + "元");
        ImageLoader.load(this, ApiService.baseUrl+payment.images, imgShow);

    }

    @Override
    public void onSuccess() {

    }
}
