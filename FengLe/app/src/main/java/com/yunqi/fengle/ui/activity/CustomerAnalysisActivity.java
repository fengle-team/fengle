package com.yunqi.fengle.ui.activity;


import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;
import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.Customer;
import com.yunqi.fengle.model.bean.CustomerAnalysis;
import com.yunqi.fengle.presenter.CustomerQueryPresenter;
import com.yunqi.fengle.presenter.contract.CustomerQueryContract;
import com.yunqi.fengle.ui.adapter.CustomerAnalysisTableDataAdapter;
import com.yunqi.fengle.ui.adapter.CustomerTableDataAdapter;
import com.yunqi.fengle.ui.adapter.TableHeader1Adapter;
import com.yunqi.fengle.ui.view.TableViewEx;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import rx.functions.Action1;

/**
 * 客户分析
 */
public class CustomerAnalysisActivity extends BaseActivity<CustomerQueryPresenter> implements CustomerQueryContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tableView)
    TableViewEx tableView;
    private List<CustomerAnalysis> mListCustomerAnalysis = new ArrayList<>();
    CustomerAnalysisTableDataAdapter adapter;
    private int page = 1;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_customer_analysis;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(toolbar, getString(R.string.module_customer_analysis));
        setWigetListener();
        final TableHeader1Adapter tableHeader1Adapter = new TableHeader1Adapter(this, getResources().getStringArray(R.array.header_title_customer_analysis));
        tableView.setHeaderAdapter(tableHeader1Adapter);
        mListCustomerAnalysis.add(new CustomerAnalysis("讯飞1",1001,3,230,1));
        mListCustomerAnalysis.add(new CustomerAnalysis("讯飞2",1002,1,231,1));
        mListCustomerAnalysis.add(new CustomerAnalysis("讯飞3",1003,2,230,2));
        mListCustomerAnalysis.add(new CustomerAnalysis("讯飞4",1004,4,1230,2));
        mListCustomerAnalysis.add(new CustomerAnalysis("讯飞5",1005,3,2230,2));
        mListCustomerAnalysis.add(new CustomerAnalysis("讯飞6",1006,7,11230,2));

        adapter = new CustomerAnalysisTableDataAdapter(this, mListCustomerAnalysis);

        TableColumnWeightModel columnModel = new TableColumnWeightModel(6);
        columnModel.setColumnWeight(0, 2);
        columnModel.setColumnWeight(1, 2);
        columnModel.setColumnWeight(2, 1);
        columnModel.setColumnWeight(3, 2);
        columnModel.setColumnWeight(4, 2);
        columnModel.setColumnWeight(5, 1);
        tableView.setColumnModel(columnModel);
        tableView.setDataAdapter(adapter);
//        tableView.setOnLoadMoreListener(new TableViewEx.OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                String keywords = editKeywords.getText().toString();
//                mPresenter.queryCustomer(keywords, ++page);
//            }
//        });
    }

    private void setWigetListener() {

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
    public void showContent(List<Customer> listCustomer) {

    }

    @Override
    public void showMoreContent(List<Customer> listCustomerMore) {

    }
}
