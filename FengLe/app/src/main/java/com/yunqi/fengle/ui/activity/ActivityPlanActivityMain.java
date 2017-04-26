package com.yunqi.fengle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.jakewharton.rxbinding.view.RxView;
import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.Goods;
import com.yunqi.fengle.model.bean.TransferApply;
import com.yunqi.fengle.model.response.ActivityAddResponse;
import com.yunqi.fengle.presenter.ActivityPlanPresenter;
import com.yunqi.fengle.presenter.contract.ActivityPlanContract;
import com.yunqi.fengle.ui.adapter.ActivityPlanManagerAdapter;
import com.yunqi.fengle.ui.adapter.ActivityPlanTableDataAdapter;
import com.yunqi.fengle.ui.adapter.StockTableDataAdapter;
import com.yunqi.fengle.ui.adapter.TableHeader1Adapter;
import com.yunqi.fengle.ui.swipe.SwipyRefreshLayout;
import com.yunqi.fengle.ui.swipe.SwipyRefreshLayoutDirection;
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
 * @Description: 活动计划
 *              {@link ActivitiesManagerActivity}
 */

public class ActivityPlanActivityMain extends BaseActivity<ActivityPlanPresenter> implements ActivityPlanContract.View,RadioGroup.OnCheckedChangeListener{


    @BindView(R.id.rgRank)
    RadioGroup rgRank;

//    private ActivityPlanManagerAdapter adapter;

    private ActivityPlanTableDataAdapter adapter;

    private final String STATUS_1 = "1";//暂存
    private final String STATUS_2 = "2";//提交待处理
    private final String STATUS_3 = "3";//审核通过
    private final String STATUS_4 = "4";//驳回

    List<ActivityAddResponse> responseList = new ArrayList<>();

    //1=暂存 2=提交待处理 3=审核通过 4=驳回
    private String status = STATUS_1;

    private String customerCode = "";

    private String startTime,endTime;


    @BindView(R.id.tableViewEx)
    ExTableView tableViewEx;
    @BindView(R.id.btn_start_time)
    Button btnStartTime;
    @BindView(R.id.btn_end_time)
    Button btnEndTime;
    @BindView(R.id.btn_query)
    Button btnQuery;

    Observable<Boolean> addOb;

//    @BindView(R.id.edit_keyword)
//    EditText editKeyword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("活动计划");
        initView();

        if (getIntent().hasExtra("custom_code")) {
            customerCode = getIntent().getStringExtra("custom_code");
        } else {
            setTitleRightImage(R.drawable.right_add);
        }


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
        final TableHeader1Adapter tableHeader1Adapter = new TableHeader1Adapter(this, getResources().getStringArray(R.array.header_title_activity));
        tableViewEx.tableView.setHeaderAdapter(tableHeader1Adapter);
        TableColumnWeightModel columnModel = new TableColumnWeightModel(5);
        columnModel.setColumnWeight(0, 1);
        columnModel.setColumnWeight(1, 2);
        columnModel.setColumnWeight(2, 1);
        columnModel.setColumnWeight(3, 1);
        columnModel.setColumnWeight(4, 1);
        tableViewEx.tableView.setColumnModel(columnModel);

        adapter = new ActivityPlanTableDataAdapter(this, responseList);
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
                Intent mIntent = new Intent();
                mIntent.setClass(ActivityPlanActivityMain.this, ActivityPlanUpdateActivity.class);
                mIntent.putExtra(ActivityPlanUpdateActivity.TAG, responseList.get(rowIndex));
                if (adapter.getBillStatus() == 1) {

                } else {
                    mIntent.putExtra(ActivityPlanUpdateActivity.TAG_2, false);
                }
                ActivityPlanActivityMain.this.startActivity(mIntent);
            }
        });
    }

    private void initRadio() {
        rgRank.setOnCheckedChangeListener(this);
        rgRank.check(R.id.rbBtn1);

    }

//    @Override
//    public void onRetry() {
//        progresser.showProgress();
//        initData();
//    }

    private void initData() {
        mPresenter.showData(status,customerCode,startTime,endTime,new ResponseListener() {
            @Override
            public void onSuccess(NetResponse response) {
                responseList = (List<ActivityAddResponse>) response.getResult();
                int select = adapter.getBillStatus();
                adapter = new ActivityPlanTableDataAdapter(mContext, responseList);
                adapter.setBillStatus(select);
                tableViewEx.tableView.setDataAdapter(adapter);
//                adapter.notifyDataSetChanged();
                tableViewEx.setLoadMoreEnabled(false);
            }

            @Override
            public void onFaild(NetResponse response) {
            }
        });
    }

    private void initView() {
        RxView.clicks(btnStartTime)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        TimeSelectDialog dialog = new TimeSelectDialog(ActivityPlanActivityMain.this, new TimeSelectDialog.TimeSelectListener() {
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
                        TimeSelectDialog dialog = new TimeSelectDialog(ActivityPlanActivityMain.this, new TimeSelectDialog.TimeSelectListener() {
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
//                        if (!TextUtils.isEmpty(startTime) && TextUtils.isEmpty(endTime)) {
//                            ToastUtil.toast(mContext,"请填写结束时间!");
//                        }

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
        int select = adapter.getBillStatus();
        responseList.clear();
        adapter = new ActivityPlanTableDataAdapter(mContext, responseList);
        adapter.setBillStatus(select);
        tableViewEx.tableView.setDataAdapter(adapter);

        int id = group.getCheckedRadioButtonId();
        switch (id) {
            case R.id.rbBtn1://待处理
                status = STATUS_1;
                adapter.setBillStatus(1);
                break;
            case R.id.rbBtn2://未完成
                status = STATUS_2;
                adapter.setBillStatus(2);
                break;
            case R.id.rbBtn3://历史单据
                status = STATUS_3;
                adapter.setBillStatus(3);
                break;
        }
        btnStartTime.setText(R.string.start_time);
        btnEndTime.setText(R.string.end_time);
        startTime = "";
        endTime = "";
        initData();
    }


//    @Override
//    public void showMoreContent(List<Goods> listGoodsMore) {
//        if (listGoodsMore.isEmpty()) {
//            Log.w(TAG, "No more data!");
//            tableViewEx.setLoadMoreEnabled(false);
//            return;
//        }
//        tableViewEx.setLoadMoreEnabled(true);
//        mListGoods.addAll(listGoodsMore);
//        adapter.notifyDataSetChanged();
//    }

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
        return R.layout.activity_activity_deta;
    }

    @Override
    protected void initEventAndData() {

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

}
