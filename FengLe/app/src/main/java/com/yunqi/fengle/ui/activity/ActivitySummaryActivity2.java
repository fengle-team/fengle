package com.yunqi.fengle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jakewharton.rxbinding.view.RxView;
import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.InvoiceApply;
import com.yunqi.fengle.model.response.ActivityAddResponse;
import com.yunqi.fengle.model.response.ActivitySummaryResponse;
import com.yunqi.fengle.presenter.ActivitySummaryPresenter2;
import com.yunqi.fengle.presenter.contract.ActivitySummaryContract2;
import com.yunqi.fengle.ui.adapter.ActivityPlanTableDataAdapter;
import com.yunqi.fengle.ui.adapter.ActivitySummaryTableDataAdapter;
import com.yunqi.fengle.ui.adapter.TableHeader1Adapter;
import com.yunqi.fengle.ui.view.ExTableView;
import com.yunqi.fengle.ui.view.TimeSelectDialog;
import com.yunqi.fengle.util.DateUtil;
import com.yunqi.fengle.util.RxBus;
import com.yunqi.fengle.util.ToastUtil;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 17:00
 * @Description: 活动总结 {@link ActivitiesManagerActivity}
 */

public class ActivitySummaryActivity2 extends BaseActivity<ActivitySummaryPresenter2> implements RadioGroup.OnCheckedChangeListener,ActivitySummaryContract2.View{

    @BindView(R.id.rgRank)
    RadioGroup rgRank;

    public static String RX_TAG = "ActivitySummaryActivity2";

//    private ActivityPlanManagerAdapter adapter;

    private ActivitySummaryTableDataAdapter adapter;

    private final int STATUS_1 = 1;//待执行
    private final int STATUS_2 = 2;//待确认
    private final int STATUS_3 = 3;//已完成


    //1=暂存 2=提交待处理 3=审核通过 4=驳回
    private int status = STATUS_1;

    private int page = 1;

    //关键字，客户名称
    private String keyword = "";

    private String startTime,endTime;


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
    @BindView(R.id.radioBtn1)
    RadioButton radioBtn1;
    @BindView(R.id.radioBtn2)
    RadioButton radioBtn2;
    @BindView(R.id.radioBtn3)
    RadioButton radioBtn3;

    Observable<Boolean> addOb;


    List<ActivitySummaryResponse> mlistInvoiceApply = new ArrayList<>();

//    @BindView(R.id.edit_keyword)
//    EditText editKeyword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("活动总结");
        initView();

        setTitleRightImage(R.drawable.right_add);


        initTableView();
        initRadio();

        addOb = RxBus.get().register(ActivityPlanManagerActivity.RX_TAG, Boolean.class);
        addOb.observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    initData();
                }
            }
        });

    }

    private void initTableView() {
        final TableHeader1Adapter tableHeader1Adapter = new TableHeader1Adapter(this, getResources().getStringArray(R.array.header_title_summary));
        tableViewEx.tableView.setHeaderAdapter(tableHeader1Adapter);
        TableColumnWeightModel columnModel = new TableColumnWeightModel(3);
        columnModel.setColumnWeight(0, 1);
        columnModel.setColumnWeight(1, 1);
        columnModel.setColumnWeight(2, 1);
        tableViewEx.tableView.setColumnModel(columnModel);

        adapter = new ActivitySummaryTableDataAdapter(this, mlistInvoiceApply);
        tableViewEx.tableView.setDataAdapter(adapter);

        tableViewEx.setOnLoadRetryListener(new ExTableView.OnLoadRetryListener() {
            @Override
            public void onLoadRetry() {
                initData();
            }
        });

        tableViewEx.tableView.addDataClickListener(new TableDataClickListener<ActivityAddResponse>() {
            @Override
            public void onDataClicked(int rowIndex, ActivityAddResponse transferApply) {
//                Intent mIntent = new Intent();
//                mIntent.setClass(ActivitySummaryActivity2.this, ActivityPlanUpdateActivity.class);
//                mIntent.putExtra(ActivityPlanUpdateActivity.TAG, responseList.get(rowIndex));
//                if (adapter.getBillStatus() == 1) {
//
//                } else {
//                    mIntent.putExtra(ActivityPlanUpdateActivity.TAG_2, adapter.getBillStatus());
//                }
//                ActivitySummaryActivity2.this.startActivityForResult(mIntent,111);
            }
        });
    }

    private void initRadio() {
        radioBtn1.setText("待执行");
        radioBtn2.setText("待确认");
        radioBtn3.setText("已完成");
        rgRank.setOnCheckedChangeListener(this);
        rgRank.check(R.id.radioBtn1);

    }


    private void initData() {
        mPresenter.getSummaryData(startTime,endTime,keyword,status + "",page);
    }

    private void initView() {
        RxView.clicks(btnStartTime)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        TimeSelectDialog dialog = new TimeSelectDialog(ActivitySummaryActivity2.this, new TimeSelectDialog.TimeSelectListener() {
                            @Override
                            public void onTimeSelected(long ltime, String strTime) {
                                btnStartTime.setText(strTime);
                                startTime = strTime;
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
                        TimeSelectDialog dialog = new TimeSelectDialog(ActivitySummaryActivity2.this, new TimeSelectDialog.TimeSelectListener() {
                            @Override
                            public void onTimeSelected(long ltime, String strTime) {
                                btnEndTime.setText(strTime);
                                endTime = strTime;
                            }
                        });
                        dialog.show();
                    }
                });

        RxView.clicks(btnQuery)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
                            if (!DateUtil.compareTime(startTime, endTime)) {
                                ToastUtil.toast(mContext, getString(R.string.warming_time_select));
                                return;
                            }
                        }

                        initData();
                    }
                });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//        int select = adapter.getBillStatus();
//        adapter = new ActivityPlanTableDataAdapter(mContext, responseList);
//        adapter.setBillStatus(select);
//        tableViewEx.tableView.setDataAdapter(adapter);

        int id = group.getCheckedRadioButtonId();
        switch (id) {
            case R.id.radioBtn1://待处理
                status = STATUS_1;
                adapter.setBillStatus(1);
                break;
            case R.id.radioBtn2://未完成
                status = STATUS_2;
                adapter.setBillStatus(2);
                break;
            case R.id.radioBtn3://历史单据
                status = STATUS_3;
                adapter.setBillStatus(3);
                break;
        }
        adapter.setBillStatus(status);
        resetData();
        initData();
    }

    private void resetData(){
        page = 1;
        btnStartTime.setText(R.string.start_time);
        btnEndTime.setText(R.string.end_time);
        startTime="";
        endTime="";
        editKeyword.setText("");
        keyword="";
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
    protected void onTitleRightClicked(View v) {
        Intent mIntent = new Intent(this, ActivityNewPlanActivity.class);
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
        return R.layout.activity_summary2;
    }

    @Override
    protected void initEventAndData() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            initData();
        }
    }

    @Override
    public void cancelLoading() {

    }


    @Override
    protected void onDestroy() {
        mPresenter.unSubscribe();
        RxBus.get().unregister(ActivityPlanManagerActivity.RX_TAG, addOb);
        super.onDestroy();
    }


    @Override
    public void showMoreContent(List<ActivitySummaryResponse> data) {
        if (data.isEmpty()) {
            tableViewEx.setLoadMoreEnabled(false);
            Log.w(TAG, "No more data!");
            return;
        }
        tableViewEx.setLoadMoreEnabled(true);
        mlistInvoiceApply.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showContent(List<ActivitySummaryResponse> data) {
        if (data.isEmpty()) {
            Log.w(TAG, "No data!");
            tableViewEx.setEmptyData();
            mlistInvoiceApply.clear();
            adapter.notifyDataSetChanged();
            return;
        }
        tableViewEx.setLoadMoreEnabled(true);
        mlistInvoiceApply.clear();
        mlistInvoiceApply.addAll(data);
        adapter.notifyDataSetChanged();
    }

}
