package com.yunqi.fengle.ui.activity;


import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jakewharton.rxbinding.view.RxView;
import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.Payment;
import com.yunqi.fengle.presenter.PaymentQueryPresenter;
import com.yunqi.fengle.presenter.contract.PaymentQueryContract;
import com.yunqi.fengle.ui.adapter.PlaymentAdapter;
import com.yunqi.fengle.ui.fragment.RegionalRankingFragment;
import com.yunqi.fengle.ui.fragment.SalerRankingFragment;
import com.yunqi.fengle.ui.view.RecycleViewDivider;
import com.yunqi.fengle.ui.view.TimeSelectDialog;
import com.yunqi.fengle.util.LogEx;
import com.yunqi.fengle.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;


/**
 * 回款查询
 */
public class PaymentQueryActivity extends BaseActivity<PaymentQueryPresenter> implements PaymentQueryContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.radioBtn1)
    RadioButton radioBtn1;
    @BindView(R.id.radioBtn2)
    RadioButton radioBtn2;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btn_start_time)
    Button btnStartTime;
    @BindView(R.id.btn_end_time)
    Button btnEndTime;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.btn_query)
    Button btnQuery;
    PlaymentAdapter adapter;
    int index = 1;
    private long lstartTime = 0;
    private long lendTime = 0;
    private String startTime;
    private String endTime;
    private int status = 2;
    private int type = 1;
    private String userId = "";
    private int page = 1;
    private List<Payment> mlistPayment = new ArrayList<>();

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_payment_query;
    }

    @Override
    protected void initEventAndData() {
        userId= App.getInstance().getUserInfo().id;
        setToolBar(toolbar, getString(R.string.module_payment_query));
        initRadioGroup();
        initRecyclerView();
        setWidgetListener();
        mPresenter.queryPayment(userId, status, type, page);
    }

    private void initRadioGroup() {
        radioBtn1.setText(R.string.today_payment);
        radioBtn2.setText(R.string.current_month_payment);
        radioGroup.setOnCheckedChangeListener(this);
        radioGroup.check(R.id.radioBtn1);
    }


    private void initRecyclerView() {
        swipeLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, RecycleViewDivider.VERTICAL_LIST));

        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        adapter = new PlaymentAdapter(mlistPayment);
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(this);
    }


    public void loadFirstPageData() {
        page = 1;
        if (lstartTime > 0 && lendTime > 0 && lstartTime >= lendTime) {
            ToastUtil.showNoticeToast(PaymentQueryActivity.this, getString(R.string.warming_time_select));
            return;
        }
        mPresenter.queryPayment(userId, status, startTime, endTime, page);
    }


    private void setWidgetListener() {
        RxView.clicks(btnStartTime)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        TimeSelectDialog dialog = new TimeSelectDialog(PaymentQueryActivity.this, new TimeSelectDialog.TimeSelectListener() {
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
                        TimeSelectDialog dialog = new TimeSelectDialog(PaymentQueryActivity.this, new TimeSelectDialog.TimeSelectListener() {
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
        RxView.clicks(btnQuery)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (lstartTime <= 0 ) {
                            ToastUtil.showNoticeToast(PaymentQueryActivity.this, getString(R.string.warming_no_start_time));
                            return;
                        }
                        if (lendTime<= 0) {
                            ToastUtil.showNoticeToast(PaymentQueryActivity.this, getString(R.string.warming_no_end_time));
                            return;
                        }
                        loadFirstPageData();
                    }
                });
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
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadFirstPageData();
            }
        }, 500);
    }

    @Override
    public void onLoadMoreRequested() {

        mPresenter.queryPayment(userId, status, startTime, endTime, ++page);
    }

    @Override
    public void showContent(List<Payment> listPayment) {
        swipeLayout.setRefreshing(false);
        adapter.setEnableLoadMore(true);
        mlistPayment.clear();
        mlistPayment.addAll(listPayment);
        adapter.setNewData(mlistPayment);
        adapter.loadMoreComplete();
    }

    @Override
    public void showMoreContent(List<Payment> listPaymentMore) {
        if (listPaymentMore.isEmpty()) {
            adapter.loadMoreEnd();
            adapter.setEnableLoadMore(false);
            return;
        }
        mlistPayment.addAll(listPaymentMore);
        adapter.loadMoreComplete();
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        int id = radioGroup.getCheckedRadioButtonId();
        switch (id) {
            case R.id.radioBtn1://今日回款
                type = 1;
                break;
            case R.id.radioBtn2://本月回款
                type = 2;
                break;
        }
        resetTime();
        page=1;
        mPresenter.queryPayment(userId, status, type, page);
    }

    private void resetTime() {
        startTime="";
        lstartTime=0;
        btnStartTime.setText(R.string.start_time);
        endTime="";
        lendTime=0;
        btnEndTime.setText(R.string.end_time);
    }
}
