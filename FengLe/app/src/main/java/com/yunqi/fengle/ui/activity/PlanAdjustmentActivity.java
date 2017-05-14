package com.yunqi.fengle.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jakewharton.rxbinding.view.RxView;
import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.Area;
import com.yunqi.fengle.model.bean.PlanAdjustmentApply;
import com.yunqi.fengle.presenter.PlanAdjustmentQueryPresenter;
import com.yunqi.fengle.presenter.contract.PlanAdjustmentRequestContract;
import com.yunqi.fengle.ui.adapter.TableHeader1Adapter;
import com.yunqi.fengle.ui.adapter.PlanAdjustmentTableDataAdapter;
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
 * 计划调剂申请
 */
public class PlanAdjustmentActivity extends BaseActivity<PlanAdjustmentQueryPresenter> implements PlanAdjustmentRequestContract.View, RadioGroup.OnCheckedChangeListener {


    public static final int ADD_REQUEST_CODE = 1;
    private static final int SELECT_CUSTOMER_REQUEST_CODE = 2;
    private static final int DETAIL_REQUEST_CODE = 3;
    public static final int DEL_DETAIL_RESULT_CODE = 1;
    public static final int UPDATE_DETAIL_RESULT_CODE = 2;
    public static final int APPROVAL_DETAIL_RESULT_CODE = 3;
    public static final int SELECT_AREA_IN_REQUEST_CODE = 4;
    public static final int SELECT_AREA_OUT_REQUEST_CODE = 5;
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
    @BindView(R.id.btn_in_area)
    Button btnInArea;
    @BindView(R.id.btn_out_area)
    Button btnOutArea;
    @BindView(R.id.btn_start_time)
    Button btnStartTime;
    @BindView(R.id.btn_end_time)
    Button btnEndTime;
    @BindView(R.id.btn_query)
    Button btnQuery;
    private int mStatus = 1;
    private long lstartTime = 0;
    private long lendTime = 0;
    private String startTime = "";
    private String endTime = "";
    private String userId = "";
    private String area_code = "";
    private int page = 1;
    private String lastStartTime = null;
    private String lastEndTime = null;
    List<PlanAdjustmentApply> mlistPlanAdjustmentApply = new ArrayList<>();
    private PlanAdjustmentTableDataAdapter adapter;
    private String from_area_code = "";
    private String to_area_code = "";
    private Area selectedInArea;
    private Area selectedOutArea;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_plan_request;
    }

    @Override
    protected void initEventAndData() {
        area_code = App.getInstance().getUserInfo().area_code;
        userId = App.getInstance().getUserInfo().id;
        setToolBar(toolbar, getString(R.string.module_plan_adjustment_request), R.drawable.right_add, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlanAdjustmentActivity.this, AddPlanAdjustmentRequestActivity.class);
                startActivityForResult(intent, ADD_REQUEST_CODE);
            }
        });
        TableColumnWeightModel columnModel = new TableColumnWeightModel(5);
        columnModel.setColumnWeight(0, 1);
        columnModel.setColumnWeight(1, 1);
        columnModel.setColumnWeight(2, 1);
        columnModel.setColumnWeight(3, 1);
        columnModel.setColumnWeight(4, 1);
        tableViewEx.tableView.setColumnModel(columnModel);
        final TableHeader1Adapter tableHeader1Adapter = new TableHeader1Adapter(this, getResources().getStringArray(R.array.header_title_plan_request));
        tableViewEx.tableView.setHeaderAdapter(tableHeader1Adapter);
        adapter = new PlanAdjustmentTableDataAdapter(this, mlistPlanAdjustmentApply);
        tableViewEx.tableView.setDataAdapter(adapter);
        initRadioGroup();
        setWidgetListener();

    }

    private void initRadioGroup() {
        radioBtn1.setText(R.string.bill_undeal);
        radioBtn2.setText(R.string.bill_undone);
        radioBtn3.setText(R.string.bill_history);
        loadData();
        radioGroup.check(R.id.radioBtn1);
        radioGroup.setOnCheckedChangeListener(this);
    }

    private void loadData() {
        mPresenter.queryPlanAdjustmentApply(userId, area_code, from_area_code, to_area_code, mStatus, startTime, endTime, page);
    }

    private void setWidgetListener() {
        tableViewEx.tableView.addDataClickListener(new TableDataClickListener<PlanAdjustmentApply>() {
            @Override
            public void onDataClicked(int rowIndex, PlanAdjustmentApply PlanAdjustmentApply) {
                Intent intent = new Intent(PlanAdjustmentActivity.this, PlanAdjustmentDetailsActivity.class);
                intent.putExtra("position", rowIndex);
                intent.putExtra("status", mStatus);
                intent.putExtra("PlanAdjustmentApply", PlanAdjustmentApply);
                startActivityForResult(intent, DETAIL_REQUEST_CODE);
            }
        });
        tableViewEx.setOnLoadMoreListener(new ExTableView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                loadData();
            }
        });
        tableViewEx.setOnLoadRetryListener(new ExTableView.OnLoadRetryListener() {
            @Override
            public void onLoadRetry() {
                loadData();
            }
        });
        RxView.clicks(btnQuery)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        page = 1;
                        if (lstartTime > 0 && lendTime > 0 && lstartTime >= lendTime) {
                            ToastUtil.showNoticeToast(PlanAdjustmentActivity.this, getString(R.string.warming_time_select));
                            return;
                        }
                        lastStartTime = startTime;
                        lastEndTime = endTime;
                        loadData();
                    }
                });
        RxView.clicks(btnStartTime)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        TimeSelectDialog dialog = new TimeSelectDialog(PlanAdjustmentActivity.this, new TimeSelectDialog.TimeSelectListener() {
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
                        TimeSelectDialog dialog = new TimeSelectDialog(PlanAdjustmentActivity.this, new TimeSelectDialog.TimeSelectListener() {
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
        RxView.clicks(btnInArea)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        //跳转到选择大区界面
                        Intent intent = new Intent(PlanAdjustmentActivity.this, AreaQueryActivity.class);
                        if (selectedInArea != null) {
                            intent.putExtra("selectAreaId", selectedInArea.id);
                        }
                        startActivityForResult(intent, SELECT_AREA_IN_REQUEST_CODE);
                    }
                });
        RxView.clicks(btnOutArea)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        //跳转到选择大区界面
                        Intent intent = new Intent(PlanAdjustmentActivity.this, AreaQueryActivity.class);
                        if (selectedOutArea != null) {
                            intent.putExtra("selectAreaId", selectedOutArea.id);
                        }
                        startActivityForResult(intent, SELECT_AREA_OUT_REQUEST_CODE);
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
            if (2 == mStatus) {
                loadData();
            }
        } else if (requestCode == DETAIL_REQUEST_CODE && resultCode == DEL_DETAIL_RESULT_CODE) {
            int position = data.getIntExtra("postion", 0);
            mlistPlanAdjustmentApply.remove(position);
            refreshData();
        } else if (requestCode == DETAIL_REQUEST_CODE && resultCode == UPDATE_DETAIL_RESULT_CODE) {
            loadData();
        } else if (requestCode == DETAIL_REQUEST_CODE && resultCode == APPROVAL_DETAIL_RESULT_CODE) {
            loadData();
        } else if (requestCode == SELECT_AREA_IN_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            selectedInArea = (Area) data.getSerializableExtra("SelectArea");
            to_area_code = selectedInArea.area_code;
            btnInArea.setText(selectedInArea.name);
        } else if (requestCode == SELECT_AREA_OUT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            selectedOutArea = (Area) data.getSerializableExtra("SelectArea");
            from_area_code = selectedOutArea.area_code;
            btnOutArea.setText(selectedOutArea.name);
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
        resetData();
        loadData();
    }

    private void resetData() {
        page = 1;
        btnStartTime.setText(R.string.start_time);
        btnEndTime.setText(R.string.end_time);
        btnInArea.setText(R.string.in_area);
        btnOutArea.setText(R.string.out_area);
        lstartTime = 0;
        startTime = "";
        lendTime = 0;
        endTime = "";
        from_area_code = "";
        to_area_code = "";
        selectedInArea = null;
        selectedOutArea = null;
    }

    @Override
    public void showContent(List<PlanAdjustmentApply> listPlanAdjustmentApply) {
        if (listPlanAdjustmentApply.isEmpty()) {
            Log.w(TAG, "No data!");
            tableViewEx.setEmptyData();
            mlistPlanAdjustmentApply.clear();
            refreshData();
            return;
        }
        tableViewEx.setLoadMoreEnabled(true);
        mlistPlanAdjustmentApply.clear();
        mlistPlanAdjustmentApply.addAll(listPlanAdjustmentApply);
        refreshData();

    }

    private void refreshData(){
        adapter.setBillStatus(mStatus);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void showMoreContent(List<PlanAdjustmentApply> listPlanAdjustmentApplyMore) {
        if (listPlanAdjustmentApplyMore.isEmpty()) {
            tableViewEx.setLoadMoreEnabled(false);
            Log.w(TAG, "No more data!");
            return;
        }
        tableViewEx.setLoadMoreEnabled(true);
        mlistPlanAdjustmentApply.addAll(listPlanAdjustmentApplyMore);
        refreshData();
    }
}
