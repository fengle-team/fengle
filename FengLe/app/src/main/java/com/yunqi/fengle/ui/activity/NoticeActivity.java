package com.yunqi.fengle.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.response.NoticeResponse;
import com.yunqi.fengle.presenter.NoticePresenter;
import com.yunqi.fengle.presenter.contract.NoticeContract;
import com.yunqi.fengle.ui.adapter.MessageAdapter;
import com.yunqi.fengle.ui.adapter.NoticeAdapter;
import com.yunqi.fengle.ui.view.RecycleViewDivider;
import com.yunqi.fengle.util.LogEx;
import com.yunqi.fengle.util.ToastUtil;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 12:08
 * @Description: {@link MyMessageActivity} 下的公告
 */

public class NoticeActivity extends BaseActivity<NoticePresenter> implements NoticeContract.View{

    @BindView(R.id.rvList)
    RecyclerView rvList;

    List<NoticeResponse> noticeList = new ArrayList<>();

    private NoticeAdapter adapter ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("通知公告");
        initRecyclerView();

        initData();
    }

    private void initData() {
        progresser.showProgress();
        mPresenter.getNotice(new ResponseListener() {
            @Override
            public void onSuccess(NetResponse response) {
                List<NoticeResponse> noticeList = (List<NoticeResponse>) response.getResult();
                adapter.setNewData(noticeList);
                progresser.showContent();
            }

            @Override
            public void onFaild(NetResponse response) {
                ToastUtil.toast(mContext,response.getMsg());
                progresser.showContent();
            }
        });
    }

    private void initRecyclerView() {

        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new RecycleViewDivider(this,RecycleViewDivider.VERTICAL_LIST));

        adapter = new NoticeAdapter(noticeList);
        rvList.setAdapter(adapter);
    }

    @Override
    protected int getViewModel() {
        return MODE_VIEW_02;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_message;
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
