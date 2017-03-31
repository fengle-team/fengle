package com.yunqi.fengle.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;
import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.Customer;
import com.yunqi.fengle.presenter.CustomerQueryPresenter;
import com.yunqi.fengle.presenter.contract.CustomerQueryContract;
import com.yunqi.fengle.ui.adapter.CustomerTableDataAdapter;
import com.yunqi.fengle.ui.adapter.TableHeader1Adapter;
import com.yunqi.fengle.ui.fragment.dialog.SimpleDialogFragment;
import com.yunqi.fengle.ui.view.ContactSelectDialog;
import com.yunqi.fengle.ui.view.ExTableView;
import com.yunqi.fengle.util.DialogHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import rx.functions.Action1;


/**
 * 客户查询
 */
public class CustomerQueryActivity extends BaseActivity<CustomerQueryPresenter> implements CustomerQueryContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tableViewEx)
    ExTableView tableViewEx;
    @BindView(R.id.edit_cusomer_keyword)
    EditText editCusomerKeyword;
    @BindView(R.id.btn_query)
    Button btnQuery;
    private List<Customer> mListCustomer = new ArrayList<>();
    CustomerTableDataAdapter adapter;
    private int page = 1;
    private String user_code = "";
    private int module = 0;
    private String keyword = "";

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_customer_query;
    }

    @Override
    protected void initEventAndData() {
        user_code= App.getInstance().getUserInfo().user_code;
        module = getIntent().getIntExtra("module", 0);
        setToolBar(toolbar, getString(R.string.module_customer_query));
        setWigetListener();
        final TableHeader1Adapter tableHeader1Adapter = new TableHeader1Adapter(this, getResources().getStringArray(R.array.header_title_customer));
        tableViewEx.tableView.setHeaderAdapter(tableHeader1Adapter);
        tableViewEx.tableView.setColumnCount(2);
        TableColumnWeightModel columnModel = new TableColumnWeightModel(2);
        columnModel.setColumnWeight(0, 2);
        columnModel.setColumnWeight(1, 6);
        tableViewEx.tableView.setColumnModel(columnModel);
        adapter = new CustomerTableDataAdapter(this, mListCustomer);
        tableViewEx.tableView.setDataAdapter(adapter);
        tableViewEx.setOnLoadMoreListener(new ExTableView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                mPresenter.queryCustomer(keyword, user_code, ++page);
            }
        });

        mPresenter.queryCustomer(keyword, user_code, page);
    }

    private void setWigetListener() {
        RxView.clicks(btnQuery)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        page = 1;
                        keyword = editCusomerKeyword.getText().toString();
                        mPresenter.queryCustomer(keyword, user_code, page);
                    }
                });
        tableViewEx.tableView.addDataClickListener(new TableDataClickListener<Customer>() {
            @Override
            public void onDataClicked(int rowIndex, final Customer customer) {
                //往来查询
                if (module == 0) {
                    ContactSelectDialog dialog = new ContactSelectDialog(CustomerQueryActivity.this, new ContactSelectDialog.OnSelectListener() {
                        @Override
                        public void onSelect(int type) {
                            Intent intent = null;
                            if (type == 0) {
                                intent = new Intent(CustomerQueryActivity.this, CustomerContactActivity.class);
                            } else {
                                intent = new Intent(CustomerQueryActivity.this, SalesDetailActivity.class);
                            }
                            intent.putExtra("customerId", customer.id);
                            startActivity(intent);
                        }
                    });
                    dialog.show();
                } else {

                    DialogHelper.showDialog(CustomerQueryActivity.this, customer.name, new SimpleDialogFragment.OnSimpleDialogListener() {
                        @Override
                        public void onOk() {
                            Intent intent=new Intent();
                            intent.putExtra("customer",customer);
                            setResult(Activity.RESULT_OK,intent);
                            finish();
                        }
                    });
                }

            }
        });

        tableViewEx.setOnLoadMoreListener(new ExTableView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.queryCustomer(keyword, user_code, ++page);
            }
        });
        tableViewEx.setOnLoadRetryListener(new ExTableView.OnLoadRetryListener() {
            @Override
            public void onLoadRetry() {
                mPresenter.queryCustomer(keyword, user_code, page);
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
    public void showContent(List<Customer> listCustomer) {
        if (listCustomer.isEmpty()) {
            Log.w(TAG, "No data!");
            tableViewEx.setEmptyData();
            return;
        }
        tableViewEx.setLoadMoreEnabled(true);
        mListCustomer.clear();
        mListCustomer.addAll(listCustomer);
        adapter = new CustomerTableDataAdapter(this, mListCustomer);
        tableViewEx.tableView.setDataAdapter(adapter);
    }

    @Override
    public void showMoreContent(List<Customer> listCustomerMore) {
        if (listCustomerMore.isEmpty()) {
            tableViewEx.setLoadMoreEnabled(false);
            Log.w(TAG, "No more data!");
            return;
        }
        tableViewEx.setLoadMoreEnabled(true);
        mListCustomer.addAll(listCustomerMore);
        adapter.notifyDataSetChanged();
    }
}
