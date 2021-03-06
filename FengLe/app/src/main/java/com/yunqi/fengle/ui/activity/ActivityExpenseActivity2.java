package com.yunqi.fengle.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.SpinnerBean;
import com.yunqi.fengle.model.request.ActivityExpenseRequest;
import com.yunqi.fengle.model.response.ActivitySummaryResponse;
import com.yunqi.fengle.presenter.ActivityExpensePresenter;
import com.yunqi.fengle.presenter.contract.ActivityExpenseContract;
import com.yunqi.fengle.ui.fragment.dialog.SpinnerDialogFragment;
import com.yunqi.fengle.ui.view.TimeSelectDialog;
import com.yunqi.fengle.ui.view.UnderLineEditTextEx;
import com.yunqi.fengle.ui.view.UnderLineTextEx;
import com.yunqi.fengle.util.DialogHelper;
import com.yunqi.fengle.util.ToastUtil;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 17:00
 * @Description: 活动报销 {@link ActivitiesManagerActivity}
 */
public class ActivityExpenseActivity2 extends BaseActivity<ActivityExpensePresenter> implements ActivityExpenseContract.View{

    @BindView(R.id.etTime)
    UnderLineTextEx etTime;
    @BindView(R.id.etName)
    UnderLineEditTextEx etName;
    @BindView(R.id.etReplyAmount)
    UnderLineEditTextEx etReplyAmount;
    @BindView(R.id.etReimburseAmount)
    UnderLineEditTextEx etReimburseAmount;
    @BindView(R.id.etActionType)
    UnderLineTextEx etActionType;
    @BindView(R.id.etReimburseType)
    UnderLineTextEx etReimburseType;
    @BindView(R.id.etInvoiceType)
    UnderLineTextEx etInvoiceType;
    @BindView(R.id.etActionTime)
    UnderLineTextEx etActionTime;

    private SpinnerBean spinnerAction = new SpinnerBean();//活动类型
    private SpinnerBean spinnerInvoice = new SpinnerBean();//发票类型
    private SpinnerBean spinnerReimburse = new SpinnerBean();//报账类型

    private boolean isTypeSuccess = false;

    private ActivitySummaryResponse summaryBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("费用报销");
        setTitleRight("提交");
        initData();
    }

    private void initView() {
        String actionVal = spinnerAction.getValue(summaryBean.getAction_type());
        spinnerAction.setKey(summaryBean.getAction_type());
        spinnerAction.setValue(actionVal);

        etName.setText(summaryBean.getClient_name());
        etActionType.setText(actionVal);
    }

    private void initData() {
        progresser.showProgress();
        mPresenter.getData(new ResponseListener() {
            @Override
            public void onSuccess() {
                spinnerAction.format(mPresenter.spinnerAction);
                spinnerInvoice.format(mPresenter.spinnerInvoice);
                spinnerReimburse.format(mPresenter.spinnerReimburse);

                if (getIntent().hasExtra(ActivitySummaryDetailActivity.TAG_INFO)) {
                    summaryBean = (ActivitySummaryResponse) getIntent().getSerializableExtra(ActivitySummaryDetailActivity.TAG_INFO);
                    initView();
                }

                progresser.showContent();
                isTypeSuccess = true;
            }

            @Override
            public void onFaild(NetResponse response) {
                ToastUtil.toast(mContext,response.getMsg());
                progresser.showError(true);
            }
        });
    }

    @Override
    public void onRetry() {
        if (!isTypeSuccess) {
            initData();
        }
    }

    @Override
    protected void onTitleRightClicked(View v) {

        if (TextUtils.isEmpty(etTime.getText().toString()) || TextUtils.isEmpty(etName.getText().toString())
                || TextUtils.isEmpty(etReplyAmount.getText().toString())
                || TextUtils.isEmpty(etReimburseAmount.getText().toString())
                || TextUtils.isEmpty(etActionTime.getText().toString())) {
            ToastUtil.toast(this,"请填写完整.");
            return;
        }
        if (spinnerAction.isEmpty() || spinnerInvoice.isEmpty() || spinnerReimburse.isEmpty()) {
            ToastUtil.toast(this,"请填写完整.");
            return ;
        }


        ActivityExpenseRequest request = new ActivityExpenseRequest();

        request.setAction_time(etActionTime.getText().toString());
        request.setClient_name(etName.getText().toString());
        request.setUserid(App.getInstance().getUserInfo().id);
        request.setReply_amount(etReplyAmount.getText().toString());
        request.setReimburse_amount(etReimburseAmount.getText().toString());
        request.setAction_type(spinnerAction.getKey());
        request.setInvoice_type(spinnerInvoice.getKey());
        request.setReimburse_type(spinnerReimburse.getKey());
        request.setAdd_time(etTime.getText().toString());

        mPresenter.addExpense(request, new ResponseListener() {
            @Override
            public void onSuccess() {
                ToastUtil.toast(mContext, "提交成功");
                ActivityExpenseActivity2.this.finish();
            }

            @Override
            public void onFaild(NetResponse response) {
                ToastUtil.toast(mContext, response.getMsg());
            }
        });
    }


    /**
     * 活动类型
     */
    @OnClick(R.id.llActionType)
    public void clickActionType() {
        DialogHelper.showSpinnerDialog(this, spinnerAction, new SpinnerDialogFragment.OnSpinnerDialogListener() {
            @Override
            public void onItemSelected(int position, SpinnerBean bean) {
                spinnerAction.setKey(bean.getKey());
                spinnerAction.setValue(bean.getValue());
                etActionType.setText(bean.getValue());
            }
        });
    }

    /**
     * 报销类型
     */
    @OnClick(R.id.llReimburseType)
    public void clickReimburType() {
        DialogHelper.showSpinnerDialog(this, spinnerReimburse, new SpinnerDialogFragment.OnSpinnerDialogListener() {
            @Override
            public void onItemSelected(int position, SpinnerBean bean) {
                spinnerReimburse.setKey(bean.getKey());
                spinnerReimburse.setValue(bean.getValue());
                etReimburseType.setText(bean.getValue());
            }
        });
    }

    /**
     * 发票类型
     */
    @OnClick(R.id.llInvoiceType)
    public void clickInvoiceType() {
        DialogHelper.showSpinnerDialog(this, spinnerInvoice, new SpinnerDialogFragment.OnSpinnerDialogListener() {
            @Override
            public void onItemSelected(int position, SpinnerBean bean) {
                spinnerInvoice.setKey(bean.getKey());
                spinnerInvoice.setValue(bean.getValue());
                etInvoiceType.setText(bean.getValue());
            }
        });
    }

    /**
     * 填报时间
     */
    @OnClick(R.id.llTime)
    public void clickTime() {

        new TimeSelectDialog(this, new TimeSelectDialog.TimeSelectListener() {
            @Override
            public void onTimeSelected(long ltime, String strTime) {
                etTime.setText(strTime);
            }
        }).show();
//        DialogHelper.showSpinnerDialog(this, spinnerInvoice, new SpinnerDialogFragment.OnSpinnerDialogListener() {
//            @Override
//            public void onItemSelected(int position, SpinnerBean bean) {
//                spinnerInvoice.setKey(bean.getKey());
//                spinnerInvoice.setValue(bean.getValue());
//                etInvoiceType.setText(bean.getValue());
//            }
//        });
    }
    /**
     *  活动时间
     */
    @OnClick(R.id.llActionTime)
    public void clickActionTime() {

        new TimeSelectDialog(this, new TimeSelectDialog.TimeSelectListener() {
            @Override
            public void onTimeSelected(long ltime, String strTime) {
                etActionTime.setText(strTime);
            }
        }).show();
//        DialogHelper.showSpinnerDialog(this, spinnerInvoice, new SpinnerDialogFragment.OnSpinnerDialogListener() {
//            @Override
//            public void onItemSelected(int position, SpinnerBean bean) {
//                spinnerInvoice.setKey(bean.getKey());
//                spinnerInvoice.setValue(bean.getValue());
//                etInvoiceType.setText(bean.getValue());
//            }
//        });
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
        return R.layout.activity_activity_expense;
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
