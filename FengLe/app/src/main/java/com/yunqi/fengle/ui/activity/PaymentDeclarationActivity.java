package com.yunqi.fengle.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jakewharton.rxbinding.view.RxView;
import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.Payment;
import com.yunqi.fengle.presenter.PaymentQueryPresenter;
import com.yunqi.fengle.presenter.contract.PaymentQueryContract;
import com.yunqi.fengle.ui.adapter.PaymentTableDataAdapter;
import com.yunqi.fengle.ui.adapter.TableHeader1Adapter;
import com.yunqi.fengle.ui.fragment.dialog.SimpleDialogFragment;
import com.yunqi.fengle.ui.view.ExTableView;
import com.yunqi.fengle.ui.view.TimeSelectDialog;
import com.yunqi.fengle.util.DialogHelper;
import com.yunqi.fengle.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import rx.functions.Action1;


/**
 * 回款申报
 */
public class PaymentDeclarationActivity extends BaseActivity<PaymentQueryPresenter> implements PaymentQueryContract.View, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.radioBtn1)
    RadioButton radioBtn1;
    @BindView(R.id.radioBtn2)
    RadioButton radioBtn2;
    @BindView(R.id.tableViewEx)
    ExTableView tableViewEx;
    @BindView(R.id.btn_start_time)
    Button btnStartTime;
    @BindView(R.id.btn_end_time)
    Button btnEndTime;
    @BindView(R.id.btn_query)
    Button btnQuery;


    ImageView imgRight;
    private long lstartTime = 0;
    private long lendTime = 0;
    private String startTime;
    private String endTime;
    private int status = 1;
    private String userId = "";
    private int page = 1;
    List<Payment> mlistPayment = new ArrayList<>();
    private PaymentTableDataAdapter adapter;

    private String lastStartTime = null;
    private String lastEndTime = null;
    private SweetAlertDialog loadingDialog;
    private int positionPayment;


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_payment_declaration;
    }

    @Override
    protected void initEventAndData() {
        userId = App.getInstance().getUserInfo().id;
        imgRight = (ImageView) toolbar.findViewById(R.id.img_right);
        imgRight.setVisibility(View.VISIBLE);
        setToolBar(toolbar, getString(R.string.module_payment_declaration));
        final TableHeader1Adapter tableHeader1Adapter = new TableHeader1Adapter(this, getResources().getStringArray(R.array.header_title_payment_declaration));
        tableViewEx.tableView.setHeaderAdapter(tableHeader1Adapter);
        adapter = new PaymentTableDataAdapter(this, mlistPayment);
        tableViewEx.tableView.setDataAdapter(adapter);
        initRadioGroup();
        mPresenter.queryPayment(userId, status, "", "", page);
        setWidgetListener();

    }

    private void initRadioGroup() {
        radioBtn1.setText(R.string.to_be_confirmed);
        radioBtn2.setText(R.string.confirmed);
        radioGroup.setOnCheckedChangeListener(this);
        radioGroup.check(R.id.radioBtn1);
    }

    private void setWidgetListener() {
        tableViewEx.tableView.addDataClickListener(new TableDataClickListener<Payment>() {
                @Override
                public void onDataClicked(int rowIndex, final Payment payment) {
//                Intent intent=new Intent(PaymentDeclarationActivity.this,PaymentDeclarationDetailsActivity.class);
//                intent.putExtra("id",payment.id);
//                startActivity(intent);
                if(status==2){
                    ToastUtil.showNoticeToast(PaymentDeclarationActivity.this,"单据已提交，不可操作");
                    return;
                }
                positionPayment = rowIndex;
                DialogHelper.showDialog(PaymentDeclarationActivity.this, "确定删除?", new SimpleDialogFragment.OnSimpleDialogListener() {
                    @Override
                    public void onOk() {
                        mPresenter.deletePayment(payment.hk_id);
                    }
                });
            }
        });
        tableViewEx.setOnLoadMoreListener(new ExTableView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (lstartTime > 0 && lendTime > 0 && lstartTime >= lendTime) {
                    ToastUtil.showNoticeToast(PaymentDeclarationActivity.this, getString(R.string.warming_time_select));
                    return;
                }
                mPresenter.queryPayment(userId, status, lastStartTime, lastEndTime, ++page);
            }
        });
        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentDeclarationActivity.this, AddPaymentDeclarationActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        RxView.clicks(btnQuery)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        page = 1;
                        if (lstartTime > 0 && lendTime > 0 && lstartTime >= lendTime) {
                            ToastUtil.showNoticeToast(PaymentDeclarationActivity.this, getString(R.string.warming_time_select));
                            return;
                        }
                        lastStartTime = startTime;
                        lastEndTime = endTime;
                        mPresenter.queryPayment(userId, status, startTime, endTime, page);
                    }
                });
        RxView.clicks(btnStartTime)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        TimeSelectDialog dialog = new TimeSelectDialog(PaymentDeclarationActivity.this, new TimeSelectDialog.TimeSelectListener() {
                            @Override
                            public void onTimeSelected(long ltime, String strTime) {
                                startTime = strTime;
                                lstartTime = ltime;
                                btnStartTime.setText(strTime);
                            }
                        });
                        dialog.show();
                    }
                });
        RxView.clicks(btnEndTime)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        TimeSelectDialog dialog = new TimeSelectDialog(PaymentDeclarationActivity.this, new TimeSelectDialog.TimeSelectListener() {
                            @Override
                            public void onTimeSelected(long ltime, String strTime) {
                                endTime = strTime;
                                lendTime = ltime;
                                btnEndTime.setText(strTime);
                            }
                        });
                        dialog.show();
                    }
                });


    }


    @Override
    public void showLoading() {
        tableViewEx.showLoading();
    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void showLoading(int type) {
        super.showLoading("正在删除...");
    }

    @Override
    public void cancelLoading(int type) {
        super.cancelLoading();
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        tableViewEx.loadingFail();
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
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        int id = radioGroup.getCheckedRadioButtonId();
        switch (id) {
            case R.id.radioBtn1://待确认
                status = 1;
                break;
            case R.id.radioBtn2://已确认
                status = 2;
                break;
        }
        btnStartTime.setText(R.string.start_time);
        btnEndTime.setText(R.string.end_time);
        page = 1;
        mPresenter.queryPayment(userId, status, "", "", page);
    }

    @Override
    public void showContent(List<Payment> listPayment) {
        if (listPayment.isEmpty()) {
            Log.w(TAG, "No data!");
            tableViewEx.setEmptyData();
            mlistPayment.clear();
            adapter.notifyDataSetChanged();
            return;
        }
        tableViewEx.setLoadMoreEnabled(true);
        mlistPayment.clear();
        mlistPayment.addAll(listPayment);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void showMoreContent(List<Payment> listPaymentMore) {
        if (listPaymentMore.isEmpty()) {
            tableViewEx.setLoadMoreEnabled(false);
            Log.w(TAG, "No more data!");
            return;
        }
        tableViewEx.setLoadMoreEnabled(true);
        mlistPayment.addAll(listPaymentMore);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccess() {
        ToastUtil.showHookToast(this, "单据删除成功！");
        mlistPayment.remove(positionPayment);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if(status==1){
                page=1;
                mPresenter.queryPayment(userId, status, lastStartTime, lastEndTime, page);
            }
        }
    }
}
