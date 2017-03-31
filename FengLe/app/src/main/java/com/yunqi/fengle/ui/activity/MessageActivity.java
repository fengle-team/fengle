package com.yunqi.fengle.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.response.MessageResponse;
import com.yunqi.fengle.presenter.MessagePresenter;
import com.yunqi.fengle.presenter.contract.MessageContract;
import com.yunqi.fengle.ui.adapter.MessageAdapter;
import com.yunqi.fengle.ui.view.RecycleViewDivider;
import com.yunqi.fengle.util.ToastUtil;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 12:08
 * @Description: {@link MyMessageActivity} 下的消息
 */

public class MessageActivity extends BaseActivity<MessagePresenter> implements MessageContract.View{

    @BindView(R.id.rvList)
    RecyclerView rvList;

    MessageAdapter adapter;

    List<MessageResponse> dataList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("消息");
        initRecyclerView();
        initData();
    }

    private void initData() {
        progresser.showProgress();
        mPresenter.getMessage(new ResponseListener() {
            @Override
            public void onSuccess(NetResponse response) {
                progresser.showContent();
                adapter.setNewData((List<MessageResponse>) response.getResult());
            }

            @Override
            public void onFaild(NetResponse response) {
                progresser.showContent();
                ToastUtil.toast(mContext,response.getMsg());
            }
        });
    }

    private void initRecyclerView() {

        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(new RecycleViewDivider(this,RecycleViewDivider.VERTICAL_LIST));
        adapter = new MessageAdapter(dataList);
//        List<Object> test = new ArrayList<>();
//        test.add(1);
//        test.add(1);
//        test.add(1);
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
