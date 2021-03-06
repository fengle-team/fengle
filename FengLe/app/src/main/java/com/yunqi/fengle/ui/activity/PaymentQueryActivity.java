package com.yunqi.fengle.ui.activity;


import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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
    @BindView(R.id.edit_keyword)
    EditText editKeyword;
    @BindView(R.id.txt_total_amount)
    TextView txtTotalAmount;
    @BindView(R.id.txt_no_data)
    TextView txtNoData;
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

    private int queryType = 0;//0：是按照今日/本月查询  1：按照时间过滤查询
    private float totalAmount = 0;//回款总金额
    private String keyword = "";
    private String custom_code = "";

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
        userId = App.getInstance().getUserInfo().id;
        custom_code=getIntent().getStringExtra("custom_code");
        if(custom_code==null){
            custom_code="";
        }
        setToolBar(toolbar, getString(R.string.module_payment_query));
        initRadioGroup();
        initRecyclerView();
        setWidgetListener();
        mPresenter.queryPayment(userId,custom_code, status, keyword, type, page);
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
        mPresenter.queryPayment(userId,custom_code, status, keyword, startTime, endTime, page);
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
                        if (lstartTime <= 0) {
                            ToastUtil.showNoticeToast(PaymentQueryActivity.this, getString(R.string.warming_no_start_time));
                            return;
                        }
                        if (lendTime <= 0) {
                            ToastUtil.showNoticeToast(PaymentQueryActivity.this, getString(R.string.warming_no_end_time));
                            return;
                        }
                        queryType = 1;
                        keyword=editKeyword.getText().toString();
                        loadFirstPageData();
                    }
                });
    }


    @Override
    public void showLoading() {
        txtNoData.setVisibility(View.VISIBLE);
        txtNoData.setText("加载中...");
    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void showError(String msg) {
        txtNoData.setVisibility(View.VISIBLE);
        txtNoData.setText("数据加载失败...");
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
                page = 1;
                if (queryType == 0) {
                    mPresenter.queryPayment(userId,custom_code, status, keyword, type, page);
                } else {
                    loadFirstPageData();
                }

            }
        }, 500);
    }

    @Override
    public void onLoadMoreRequested() {

        mPresenter.queryPayment(userId,custom_code, status, keyword, startTime, endTime, ++page);
    }

    @Override
    public void showContent(List<Payment> listPayment) {
        if(listPayment.isEmpty()){
            txtNoData.setText("暂无数据");
            txtNoData.setVisibility(View.VISIBLE);
            swipeLayout.setVisibility(View.GONE);
        }
        else{
            swipeLayout.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);
        }
        swipeLayout.setRefreshing(false);
        adapter.setEnableLoadMore(true);
        mlistPayment.clear();
        mlistPayment.addAll(listPayment);
        adapter.notifyDataSetChanged();
        adapter.loadMoreComplete();
        totalAmount = 0;
        for (Payment payment : listPayment) {
            totalAmount += payment.huikuan_amount;
        }
        txtTotalAmount.setText("合计:" + totalAmount + "元");
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
        for (Payment payment : listPaymentMore) {
            totalAmount += payment.huikuan_amount;
        }
        txtTotalAmount.setText("合计:" + totalAmount + "元");
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
        resetData();
        page = 1;
        queryType = 0;
        mPresenter.queryPayment(userId,custom_code, status, keyword, type, page);
    }

    private void resetData() {
        editKeyword.setText("");
        keyword = "";
        startTime = "";
        lstartTime = 0;
        btnStartTime.setText(R.string.start_time);
        endTime = "";
        lendTime = 0;
        btnEndTime.setText(R.string.end_time);
    }
}
