package com.yunqi.fengle.ui.fragment;

import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseFragment;
import com.yunqi.fengle.ui.adapter.RegionalHeaderAdapter;
import com.yunqi.fengle.ui.adapter.TableHeader1Adapter;
import com.yunqi.fengle.ui.view.TableViewEx;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 13:56
 * @Description:业务员排名
 *              {@link com.yunqi.fengle.ui.activity.AchievementManagerActivity}
 */

public class SalerRankingFragment extends BaseFragment{

    private TableViewEx tableView;

    public static final SalerRankingFragment newInstance() {
        SalerRankingFragment f = new SalerRankingFragment();
        return f;
    }

    @Override
    protected void init() {
        tableView = (TableViewEx) mView.findViewById(R.id.tableView);

        final TableHeader1Adapter tableHeader1Adapter = new TableHeader1Adapter(getContext(), getResources().getStringArray(R.array.header_title_saler));
        tableView.setHeaderAdapter(tableHeader1Adapter);

        List<Object> test = new ArrayList<>();
        test.add("23");
        test.add("23");
        test.add("23");
        test.add("23");
        test.add("23");
        tableView.setDataAdapter(new RegionalHeaderAdapter(mActivity,test));
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_regional_ranking;
    }

    @Override
    protected void initEventAndData() {

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
}
