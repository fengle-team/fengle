package com.yunqi.fengle.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.StatusInfo;
import com.yunqi.fengle.presenter.StatusDetailPresenter;
import com.yunqi.fengle.presenter.contract.StatusDetailContract;
import com.yunqi.fengle.ui.adapter.StatusAdapter;
import com.yunqi.fengle.ui.view.RecycleViewDivider;
import com.yunqi.fengle.util.TimeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;


/**
 * 状态详情
 */
public class StatusDetailActivity extends BaseActivity<StatusDetailPresenter> implements StatusDetailContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private StatusAdapter adapter;
    private List<StatusInfo> mlistStatus = new ArrayList<>();
    private StatusInfo lastStatus;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_status_detail;
    }

    @Override
    protected void initEventAndData() {
        String order_code = getIntent().getStringExtra("order_code");
        lastStatus = (StatusInfo) getIntent().getSerializableExtra("LastStatus");
        setToolBar(toolbar, getString(R.string.module_status_detail));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, RecycleViewDivider.VERTICAL_LIST));
        adapter = new StatusAdapter(mlistStatus);
        recyclerView.setAdapter(adapter);
        mPresenter.queryStatusDetail(order_code);
    }


    @Override
    public void showContent(List<StatusInfo> listStatus) {
        if (listStatus.isEmpty()) {
            Log.w(TAG, "No data!");
            return;
        }
        mlistStatus.clear();
        mlistStatus.addAll(listStatus);
        if (lastStatus != null) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss.SSS\'Z\'");
            try {
                Date date = sf.parse(lastStatus.create_time);
                String time= TimeUtil.format(date,"yyyy-MM-dd hh:mm:ss");
                lastStatus.create_time=time;
                mlistStatus.add(lastStatus);
            } catch (ParseException e) {
                e.printStackTrace();
                mlistStatus.add(lastStatus);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
