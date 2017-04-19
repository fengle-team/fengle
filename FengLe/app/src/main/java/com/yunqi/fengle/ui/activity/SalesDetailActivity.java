package com.yunqi.fengle.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jakewharton.rxbinding.view.RxView;
import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.Goods;
import com.yunqi.fengle.model.bean.SaleInfo;
import com.yunqi.fengle.presenter.SaleDetailPresenter;
import com.yunqi.fengle.presenter.StockQueryPresenter;
import com.yunqi.fengle.presenter.contract.SaleDetailContract;
import com.yunqi.fengle.presenter.contract.StockQueryContract;
import com.yunqi.fengle.ui.adapter.CustomerTableDataAdapter;
import com.yunqi.fengle.ui.adapter.SaleTableDataAdapter;
import com.yunqi.fengle.ui.adapter.TableHeader1Adapter;
import com.yunqi.fengle.ui.view.ExTableView;
import com.yunqi.fengle.ui.view.TableViewEx;
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
 * 销售明细
 */
public class SalesDetailActivity extends BaseActivity<SaleDetailPresenter> implements SaleDetailContract.View {

    public final static int REQUEST_CODE = 1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tableViewEx)
    ExTableView tableViewEx;
    @BindView(R.id.btn_query)
    Button btnQuery;
    @BindView(R.id.btn_start_time)
    Button btnStartTime;
    @BindView(R.id.btn_end_time)
    Button btnEndTime;
    private String startTime = "";
    private String endTime = "";
    private String lastStartTime = "";
    private String lastEndTime = "";
    private List<SaleInfo> mlistSaleInfo=new ArrayList<>();
    private SaleTableDataAdapter adapter;
    private long lstartTime = 0;
    private long lendTime = 0;
    private String ccuscode;//客户编码
    private String cpersoncode;//业务员编码
    private String ccdcode;//客户地区编码

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_sales_detail;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(toolbar, getString(R.string.module_sales_detail));
        cpersoncode= App.getInstance().getUserInfo().user_code;
        ccuscode=getIntent().getStringExtra("customer_code");
        ccdcode=getIntent().getStringExtra("ccdcode");
        final TableHeader1Adapter tableHeader1Adapter = new TableHeader1Adapter(this, getResources().getStringArray(R.array.header_title_sales_detail));
        tableViewEx.tableView.setHeaderAdapter(tableHeader1Adapter);
        tableViewEx.tableView.setColumnCount(4);
        TableColumnWeightModel columnModel = new TableColumnWeightModel(4);
        columnModel.setColumnWeight(0, 3);
        columnModel.setColumnWeight(1, 2);
        columnModel.setColumnWeight(2, 2);
        columnModel.setColumnWeight(3, 1);
        tableViewEx.tableView.setColumnModel(columnModel);
        tableViewEx.setMode(ExTableView.Mode.ONLY_LIST);
        setWidgetListener();
    }

    private void setWidgetListener() {
        tableViewEx.tableView.addDataClickListener(new TableDataClickListener<SaleInfo>() {
            @Override
            public void onDataClicked(int rowIndex, SaleInfo saleInfo) {
                    Intent intent=new Intent(SalesDetailActivity.this,GoodsSaleDetailActivity.class);
                    intent.putExtra("saleInfo",saleInfo);
                    startActivity(intent);
            }
        });
        RxView.clicks(btnQuery)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (lstartTime <= 0 || lendTime <=0 ) {
                            ToastUtil.showNoticeToast(SalesDetailActivity.this, getString(R.string.warming_no_start_time_or_end_time));
                            return;
                        }
                        if (lstartTime >= lendTime) {
                            ToastUtil.showNoticeToast(SalesDetailActivity.this, getString(R.string.warming_time_select));
                            return;
                        }
                        lastStartTime = startTime;
                        lastEndTime = endTime;
                        mPresenter.querySales(startTime,endTime,ccuscode,cpersoncode,ccdcode);
                    }
                });
        RxView.clicks(btnStartTime)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        TimeSelectDialog dialog = new TimeSelectDialog(SalesDetailActivity.this, new TimeSelectDialog.TimeSelectListener() {
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
                        TimeSelectDialog dialog = new TimeSelectDialog(SalesDetailActivity.this, new TimeSelectDialog.TimeSelectListener() {
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
        tableViewEx.setOnLoadRetryListener(new ExTableView.OnLoadRetryListener() {
            @Override
            public void onLoadRetry() {
                mPresenter.querySales(lastStartTime,lastEndTime,ccuscode,cpersoncode,ccdcode);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {

        }
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
    public void showContent(List<SaleInfo> listSaleInfo) {
        if(listSaleInfo.size()>0){
            tableViewEx.setRecordCount(listSaleInfo.size());
        }
        else {
            tableViewEx.setEmptyData();
        }
        mlistSaleInfo=listSaleInfo;
        adapter = new SaleTableDataAdapter(this, mlistSaleInfo);
        tableViewEx.tableView.setDataAdapter(adapter);
    }
}
