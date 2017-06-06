package com.yunqi.fengle.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.UserBean;
import com.yunqi.fengle.presenter.UnderQueryPresenter;
import com.yunqi.fengle.presenter.contract.UnderQueryContract;
import com.yunqi.fengle.ui.adapter.UserAdapter;
import com.yunqi.fengle.ui.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 直属下级查询
 */
public class UnderQueryActivity extends BaseActivity<UnderQueryPresenter> implements UnderQueryContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<UserBean> mListUser = new ArrayList<>();
    private UserAdapter madapter;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_under_query;
    }

    @Override
    protected void initEventAndData() {
        setToolBar(toolbar, getString(R.string.module_under_query));
        initRecyclerView();
        mPresenter.queryUnder(App.getInstance().getUserInfo().id);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, RecycleViewDivider.VERTICAL_LIST));

        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent();
                intent.putExtra("UnderUser",mListUser.get(position));
                madapter.notifyDataSetChanged();
                setResult(1,intent);
                finish();
            }
        });
        madapter = new UserAdapter(mListUser);
        recyclerView.setAdapter(madapter);
    }


    @Override
    public void showLoading() {
        super.showLoading("加载中...");
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
    public void showContent(List<UserBean> listUser) {
        if (listUser.isEmpty()) {
            Log.w(TAG, "No data!");
            return;
        }
        mListUser.clear();
        mListUser.addAll(listUser);
        madapter.notifyDataSetChanged();
    }

}
