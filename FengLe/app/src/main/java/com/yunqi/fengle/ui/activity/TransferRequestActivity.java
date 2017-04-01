package com.yunqi.fengle.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.jakewharton.rxbinding.view.RxView;
import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.Customer;
import com.yunqi.fengle.model.bean.TransferApply;
import com.yunqi.fengle.model.bean.TransferApply;
import com.yunqi.fengle.presenter.TransferQueryPresenter;
import com.yunqi.fengle.presenter.contract.TransferRequestContract;
import com.yunqi.fengle.ui.adapter.TransferTableDataAdapter;
import com.yunqi.fengle.ui.adapter.TableHeader1Adapter;
import com.yunqi.fengle.ui.view.ExTableView;
import com.yunqi.fengle.ui.view.TimeSelectDialog;
import com.yunqi.fengle.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import rx.functions.Action1;


/**
 * 调货申请
 */
public class TransferRequestActivity extends BaseActivity<TransferQueryPresenter> implements TransferRequestContract.View, RadioGroup.OnCheckedChangeListener {


    public static final int ADD_REQUEST_CODE = 1;
    private static final int SELECT_CUSTOMER_REQUEST_CODE = 2;
    private static final int DETAIL_REQUEST_CODE = 3;
    public static final int DEL_DETAIL_RESULT_CODE = 1;
    public static final int UPDATE_DETAIL_RESULT_CODE = 2;
    public static final int APPROVAL_DETAIL_RESULT_CODE = 3;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.radioBtn1)
    RadioButton radioBtn1;
    @BindView(R.id.radioBtn2)
    RadioButton radioBtn2;
    @BindView(R.id.radioBtn3)
    RadioButton radioBtn3;
    @BindView(R.id.tableViewEx)
    ExTableView tableViewEx;
    @BindView(R.id.btn_start_time)
    Button btnStartTime;
    @BindView(R.id.btn_end_time)
    Button btnEndTime;
    @BindView(R.id.btn_query)
    Button btnQuery;
    @BindView(R.id.edit_keyword)
    EditText editKeyword;

    ImageView imgRight;
    private int mStatus = 1;
    private long lstartTime = 0;
    private long lendTime = 0;
    private String startTime = "";
    private String endTime = "";
    private String userId = "";
    private int page = 1;
    private String lastStartTime = null;
    private String lastEndTime = null;
    private String keyword = "";
    List<TransferApply> mlistTransferApply = new ArrayList<>();
    private TransferTableDataAdapter adapter;
    private Customer selectCustomer;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_delivery_request;
    }

    @Override
    protected void initEventAndData() {
        userId = App.getInstance().getUserInfo().id;
        imgRight = (ImageView) toolbar.findViewById(R.id.img_right);
        imgRight.setVisibility(View.VISIBLE);
        setToolBar(toolbar, getString(R.string.module_transfer_request));
        TableColumnWeightModel columnModel = new TableColumnWeightModel(4);
        columnModel.setColumnWeight(0, 2);
        columnModel.setColumnWeight(1, 3);
        columnModel.setColumnWeight(2, 2);
        columnModel.setColumnWeight(3, 2);
        tableViewEx.tableView.setColumnModel(columnModel);
        final TableHeader1Adapter tableHeader1Adapter = new TableHeader1Adapter(this, getResources().getStringArray(R.array.header_title_bill_request));
        tableViewEx.tableView.setHeaderAdapter(tableHeader1Adapter);
        adapter = new TransferTableDataAdapter(this, mlistTransferApply);
        tableViewEx.tableView.setDataAdapter(adapter);
        initRadioGroup();
        setWidgetListener();

    }

    private void initRadioGroup() {
        radioBtn1.setText(R.string.bill_undeal);
        radioBtn2.setText(R.string.bill_undone);
        radioBtn3.setText(R.string.bill_history);
        mPresenter.queryTransferApply(userId,keyword, mStatus, "", "", page);
        radioGroup.check(R.id.radioBtn1);
        radioGroup.setOnCheckedChangeListener(this);

    }

    private void setWidgetListener() {
        tableViewEx.tableView.addDataClickListener(new TableDataClickListener<TransferApply>() {
            @Override
            public void onDataClicked(int rowIndex, TransferApply transferApply) {
                Intent intent = new Intent(TransferRequestActivity.this, TransferDetailsActivity.class);
                intent.putExtra("position", rowIndex);
                intent.putExtra("status", mStatus);
                intent.putExtra("TransferApply", transferApply);
                startActivityForResult(intent, DETAIL_REQUEST_CODE);
            }
        });
        tableViewEx.setOnLoadMoreListener(new ExTableView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.queryTransferApply(userId,keyword, mStatus, lastStartTime, lastEndTime, ++page);
            }
        });
        tableViewEx.setOnLoadRetryListener(new ExTableView.OnLoadRetryListener() {
            @Override
            public void onLoadRetry() {
                mPresenter.queryTransferApply(userId,keyword, mStatus, lastStartTime, lastEndTime, page);
            }
        });
        RxView.clicks(btnQuery)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        page = 1;
                        if (lstartTime > 0 && lendTime > 0 && lstartTime >= lendTime) {
                            ToastUtil.showNoticeToast(TransferRequestActivity.this, getString(R.string.warming_time_select));
                            return;
                        }
                        keyword =editKeyword.getText().toString();
                        lastStartTime = startTime;
                        lastEndTime = endTime;
                        mPresenter.queryTransferApply(userId,keyword, mStatus, startTime, endTime, page);
                    }
                });
        RxView.clicks(btnStartTime)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        TimeSelectDialog dialog = new TimeSelectDialog(TransferRequestActivity.this, new TimeSelectDialog.TimeSelectListener() {
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
                        TimeSelectDialog dialog = new TimeSelectDialog(TransferRequestActivity.this, new TimeSelectDialog.TimeSelectListener() {
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
        RxView.clicks(imgRight)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent intent = new Intent(TransferRequestActivity.this, AddTransferRequestActivity.class);
                        startActivityForResult(intent, ADD_REQUEST_CODE);
                    }
                });

    }


    @Override
    public void showLoading() {
        tableViewEx.showLoading();
    }

    @Override
    public void showError(String msg) {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        page = 1;
        if (requestCode == ADD_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            int status = data.getIntExtra("status", 0);
            if (2 == mStatus) {
                mPresenter.queryTransferApply(userId,keyword, mStatus, startTime, endTime, page);
            }
        }else if (requestCode == DETAIL_REQUEST_CODE && resultCode == DEL_DETAIL_RESULT_CODE) {
            int position = data.getIntExtra("postion", 0);
            mlistTransferApply.remove(position);
            adapter.notifyDataSetChanged();
        } else if (requestCode == DETAIL_REQUEST_CODE && resultCode == UPDATE_DETAIL_RESULT_CODE) {
            int status = data.getIntExtra("status", 0);
            if (1 == mStatus) {
                mPresenter.queryTransferApply(userId,keyword, mStatus, startTime, endTime, page);
            }
        }else if (requestCode == DETAIL_REQUEST_CODE && resultCode == APPROVAL_DETAIL_RESULT_CODE) {
            int status = data.getIntExtra("status", 0);
            if (2 == mStatus) {
                mPresenter.queryTransferApply(userId,keyword, mStatus, startTime, endTime, page);
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        int id = radioGroup.getCheckedRadioButtonId();
        switch (id) {
            case R.id.radioBtn1://待处理
                mStatus = 1;
                break;
            case R.id.radioBtn2://未完成
                mStatus = 2;
                break;
            case R.id.radioBtn3://历史单据
                mStatus = 3;
                break;
        }
        btnStartTime.setText(R.string.start_time);
        btnEndTime.setText(R.string.end_time);
        page = 1;
        mPresenter.queryTransferApply(userId,keyword, mStatus, "", "", page);
    }

    @Override
    public void showContent(List<TransferApply> listTransferApply) {
        if (listTransferApply.isEmpty()) {
            Log.w(TAG, "No data!");
            tableViewEx.setEmptyData();
            mlistTransferApply.clear();
            adapter.notifyDataSetChanged();
            return;
        }
        tableViewEx.setLoadMoreEnabled(true);
        mlistTransferApply.clear();
        mlistTransferApply.addAll(listTransferApply);
        adapter.notifyDataSetChanged();
    }

   

    @Override
    public void showMoreContent(List<TransferApply> listTransferApplyMore) {
        if (listTransferApplyMore.isEmpty()) {
            tableViewEx.setLoadMoreEnabled(false);
            Log.w(TAG, "No more data!");
            return;
        }
        tableViewEx.setLoadMoreEnabled(true);
        mlistTransferApply.addAll(listTransferApplyMore);
        adapter.notifyDataSetChanged();
    }
}
