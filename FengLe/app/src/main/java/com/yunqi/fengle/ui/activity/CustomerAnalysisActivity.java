package com.yunqi.fengle.ui.activity;


import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.CustomerAnalysis;
import com.yunqi.fengle.presenter.CustomerAnalysisPresenter;
import com.yunqi.fengle.presenter.contract.CustomerAnalysisContract;
import com.yunqi.fengle.ui.adapter.CustomerAnalysisTableDataAdapter;
import com.yunqi.fengle.ui.adapter.TableHeader1Adapter;
import com.yunqi.fengle.ui.view.ExTableView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnWeightModel;

/**
 * 客户分析
 */
public class CustomerAnalysisActivity extends BaseActivity<CustomerAnalysisPresenter> implements CustomerAnalysisContract.View{
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tableViewEx)
    ExTableView tableViewEx;
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
        tableViewEx.tableView.setHeaderAdapter(tableHeader1Adapter);
        adapter = new CustomerAnalysisTableDataAdapter(this, mListCustomerAnalysis);
        TableColumnWeightModel columnModel = new TableColumnWeightModel(6);
        columnModel.setColumnWeight(0, 2);
        columnModel.setColumnWeight(1, 2);
        columnModel.setColumnWeight(2, 1);
        columnModel.setColumnWeight(4, 2);
        columnModel.setColumnWeight(3, 2);
        columnModel.setColumnWeight(5, 1);
        tableViewEx.tableView.setColumnModel(columnModel);
        tableViewEx.tableView.setDataAdapter(adapter);
        tableViewEx.setMode(ExTableView.Mode.ONLY_LIST);
        String user_code = App.getInstance().getUserInfo().user_code;
        mPresenter.queryCustomerAnalysis(user_code);
    }

    private void setWigetListener() {

    }
    @Override
    public void showContent(List<CustomerAnalysis> listCustomerAnalysis) {
        if (listCustomerAnalysis.isEmpty()) {
            Log.w(TAG, "No data!");
            tableViewEx.setEmptyData();
            return;
        }
        mListCustomerAnalysis.clear();
        mListCustomerAnalysis.addAll(listCustomerAnalysis);
        adapter.notifyDataSetChanged();
    }
}
