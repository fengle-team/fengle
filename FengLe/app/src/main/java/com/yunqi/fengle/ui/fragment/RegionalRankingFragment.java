package com.yunqi.fengle.ui.fragment;

import android.util.Log;

import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseFragment;
import com.yunqi.fengle.model.bean.InvoiceApply;
import com.yunqi.fengle.model.response.RegionalRankingResponse;
import com.yunqi.fengle.model.response.SaleRankingResponse;
import com.yunqi.fengle.presenter.RegionalRankePresenter;
import com.yunqi.fengle.presenter.contract.RegionalRankeContract;
import com.yunqi.fengle.ui.adapter.DeliveryTableDataAdapter;
import com.yunqi.fengle.ui.adapter.RegionalHeaderAdapter;
import com.yunqi.fengle.ui.adapter.SaleHeaderAdapter;
import com.yunqi.fengle.ui.adapter.TableHeader1Adapter;
import com.yunqi.fengle.ui.adapter.VisitingCustomerAdapter;
import com.yunqi.fengle.ui.view.ExTableView;
import com.yunqi.fengle.ui.view.TableViewEx;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.codecrafters.tableview.model.TableColumnWeightModel;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 13:45
 * @Description:大区排名
 */

public class RegionalRankingFragment extends BaseFragment<RegionalRankePresenter> implements RegionalRankeContract.View{
    ExTableView tableViewEx;

    private RegionalHeaderAdapter adapter;

    private List<RegionalRankingResponse> dataList;

    int page = 1;

    public static final RegionalRankingFragment newInstance() {
        RegionalRankingFragment f = new RegionalRankingFragment();
        return f;
    }

    @Override
    protected void init() {
        tableViewEx = (ExTableView) mView.findViewById(R.id.tableViewEx);

        TableColumnWeightModel columnModel = new TableColumnWeightModel(4);
        tableViewEx.tableView.setColumnCount(6);

        final TableHeader1Adapter tableHeader1Adapter = new TableHeader1Adapter(getContext(), getResources().getStringArray(R.array.header_title_regional));
        tableViewEx.tableView.setHeaderAdapter(tableHeader1Adapter);

        dataList = new ArrayList<>();

        adapter = new RegionalHeaderAdapter(mActivity, dataList);
        tableViewEx.tableView.setDataAdapter(adapter);


        tableViewEx.setOnLoadMoreListener(new ExTableView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getRegionalRanke(++page,new ResponseListener() {
                    @Override
                    public void onSuccess() {
                        super.onSuccess();
                    }

                    @Override
                    public void onFaild(NetResponse response) {
                        super.onFaild(response);
                    }
                });
//                mPresenter.queryInvoiceApply(userId,keyword, mStatus, lastStartTime, lastEndTime, ++page);
            }
        });
        tableViewEx.setOnLoadRetryListener(new ExTableView.OnLoadRetryListener() {
            @Override
            public void onLoadRetry() {
                mPresenter.getRegionalRanke(page, new ResponseListener() {
                    @Override
                    public void onSuccess() {
                        super.onSuccess();
                    }

                    @Override
                    public void onFaild(NetResponse response) {
                        super.onFaild(response);
                    }
                });
//                mPresenter.queryInvoiceApply(userId,keyword, mStatus, lastStartTime, lastEndTime, page);
            }
        });



    }


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_regional_ranking;
    }

    @Override
    protected void initEventAndData() {
        mPresenter.getRegionalRanke(page, new ResponseListener() {
            @Override
            public void onSuccess() {
                super.onSuccess();
            }

            @Override
            public void onFaild(NetResponse response) {
                super.onFaild(response);
            }
        });
    }

    @Override
    public void showLoading() {
        tableViewEx.showLoading();
    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void showError(String msg) {
        tableViewEx.loadingFail();
    }




    @Override
    public void showContentRegional(List<RegionalRankingResponse> listRegional) {
        if (listRegional.isEmpty()) {
            tableViewEx.setEmptyData();
            dataList.clear();
            adapter.notifyDataSetChanged();
            return;
        }
        tableViewEx.setLoadMoreEnabled(true);
        dataList.clear();
        dataList.addAll(listRegional);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMoreContentRegional(List<RegionalRankingResponse> listRegional) {
        if (listRegional.isEmpty()) {
            tableViewEx.setLoadMoreEnabled(false);
            return;
        }
        tableViewEx.setLoadMoreEnabled(true);
        dataList.addAll(listRegional);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showContentSale(List<SaleRankingResponse> listInvoiceApply) {

    }

    @Override
    public void showMoreContentSale(List<SaleRankingResponse> listInvoiceApplyMore) {

    }
}
