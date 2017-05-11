package com.yunqi.fengle.ui.activity;


import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
import de.codecrafters.tableview.listeners.TableHeaderClickListener;
import de.codecrafters.tableview.model.TableColumnWeightModel;

/**
 * 客户分析
 */
public class CustomerAnalysisActivity extends BaseActivity<CustomerAnalysisPresenter> implements CustomerAnalysisContract.View,RadioGroup.OnCheckedChangeListener{
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tableViewEx)
    ExTableView tableViewEx;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.radioBtn1)
    RadioButton radioBtn1;
    @BindView(R.id.radioBtn2)
    RadioButton radioBtn2;
    private List<CustomerAnalysis> mListCustomerAnalysis = new ArrayList<>();
    CustomerAnalysisTableDataAdapter adapter;
    private int page = 1;
    private int type=0;//默认为0表示新客户
    private String user_code;

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
        user_code= App.getInstance().getUserInfo().user_code;
        setToolBar(toolbar, getString(R.string.module_customer_analysis));
        final TableHeader1Adapter tableHeader1Adapter = new TableHeader1Adapter(this, getResources().getStringArray(R.array.header_title_customer_analysis));
        tableViewEx.tableView.setHeaderAdapter(tableHeader1Adapter);
        adapter = new CustomerAnalysisTableDataAdapter(this, mListCustomerAnalysis);
        TableColumnWeightModel columnModel = new TableColumnWeightModel(5);
        columnModel.setColumnWeight(0, 1);
        columnModel.setColumnWeight(1, 2);
        columnModel.setColumnWeight(2, 1);
        columnModel.setColumnWeight(3, 1);
        columnModel.setColumnWeight(4, 1);
        tableViewEx.tableView.setColumnModel(columnModel);
        tableViewEx.tableView.setDataAdapter(adapter);
        tableViewEx.setMode(ExTableView.Mode.ONLY_LIST);
        initRadioGroup();
        setWigetListener();
        loadData();
    }
    private void loadData(){
        mPresenter.queryCustomerAnalysis(user_code,type);
    }

    private void initRadioGroup() {
        radioGroup.check(R.id.radioBtn1);

    }

    private void setWigetListener() {
        radioGroup.setOnCheckedChangeListener(this);
        tableViewEx.tableView.addHeaderClickListener(new TableHeaderClickListener() {
            @Override
            public void onHeaderClicked(int columnIndex) {

            }
        });
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

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        int id = radioGroup.getCheckedRadioButtonId();
        switch (id) {
            case R.id.radioBtn1://新客户
                type=0;
                break;
            case R.id.radioBtn2://老客户
                type=1;
                break;
        }
        loadData();
    }
}
