package com.yunqi.fengle.ui.fragment;

import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseFragment;
import com.yunqi.fengle.model.response.RegionalRankingResponse;
import com.yunqi.fengle.model.response.SaleRankingResponse;
import com.yunqi.fengle.presenter.RegionalRankePresenter;
import com.yunqi.fengle.presenter.contract.RegionalRankeContract;
import com.yunqi.fengle.ui.adapter.RegionalHeaderAdapter;
import com.yunqi.fengle.ui.adapter.SaleHeaderAdapter;
import com.yunqi.fengle.ui.adapter.TableHeader1Adapter;
import com.yunqi.fengle.ui.view.ExTableView;
import com.yunqi.fengle.ui.view.TableViewEx;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import java.util.ArrayList;
import java.util.List;

import de.codecrafters.tableview.model.TableColumnWeightModel;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 13:56
 * @Description:业务员排名
 *              {@link com.yunqi.fengle.ui.activity.AchievementManagerActivity}
 */

public class SalerRankingFragment extends BaseFragment<RegionalRankePresenter> implements RegionalRankeContract.View {

    ExTableView tableViewEx;

    private SaleHeaderAdapter adapter;

    private List<SaleRankingResponse> dataList;

    int page = 1;

    public static final SalerRankingFragment newInstance() {
        SalerRankingFragment f = new SalerRankingFragment();
        return f;
    }

    @Override
    protected void init() {
        tableViewEx = (ExTableView) mView.findViewById(R.id.tableViewEx);

        TableColumnWeightModel columnModel = new TableColumnWeightModel(4);
        tableViewEx.tableView.setColumnCount(6);

        final TableHeader1Adapter tableHeader1Adapter = new TableHeader1Adapter(getContext(), getResources().getStringArray(R.array.header_title_sale));
        tableViewEx.tableView.setHeaderAdapter(tableHeader1Adapter);

        dataList = new ArrayList<>();

        adapter = new SaleHeaderAdapter(mActivity, dataList);
        tableViewEx.tableView.setDataAdapter(adapter);


        tableViewEx.setOnLoadMoreListener(new ExTableView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getSaleRanke(++page,new ResponseListener() {
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
                mPresenter.getSaleRanke(page, new ResponseListener() {
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
        mPresenter.getSaleRanke(page, new ResponseListener() {
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

    }

    @Override
    public void showMoreContentRegional(List<RegionalRankingResponse> listRegional) {

    }

    @Override
    public void showContentSale(List<SaleRankingResponse> listSale) {
        if (listSale.isEmpty()) {
            tableViewEx.setEmptyData();
            dataList.clear();
            adapter.notifyDataSetChanged();
            return;
        }
        tableViewEx.setLoadMoreEnabled(true);
        dataList.clear();
        dataList.addAll(listSale);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMoreContentSale(List<SaleRankingResponse> listSale) {
        if (listSale.isEmpty()) {
            tableViewEx.setLoadMoreEnabled(false);
            return;
        }
        tableViewEx.setLoadMoreEnabled(true);
        dataList.addAll(listSale);
        adapter.notifyDataSetChanged();
    }
}
