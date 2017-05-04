package com.yunqi.fengle.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.request.AddLinkmanRequest;
import com.yunqi.fengle.model.response.CustomersResponse;
import com.yunqi.fengle.presenter.NewContactsPresenter;
import com.yunqi.fengle.util.ToastUtil;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import butterknife.BindView;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 00:17
 * @Description:新增联系人
 */

public class NewContactsActivity extends BaseActivity<NewContactsPresenter> {

    @BindView(R.id.tvPost)
    TextView tvPost;
    @BindView(R.id.tvDept)
    TextView tvDept;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvClientName)
    TextView tvClientName;

    CustomersResponse customerModel;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("新建联系人");
        setTitleRight("提交");

        customerModel = (CustomersResponse) getIntent().getSerializableExtra("tag");
        tvClientName.setText(customerModel.getName());
    }


    @Override
    protected void onTitleRightClicked(View v) {

        if (TextUtils.isEmpty(tvPost.getText().toString())
                || TextUtils.isEmpty(tvDept.getText().toString())
                || TextUtils.isEmpty(tvName.getText().toString()) || TextUtils.isEmpty(tvClientName.getText().toString())) {
            ToastUtil.toast(mContext,"请填写完整数据");
            return;
        }
        final AddLinkmanRequest request = new AddLinkmanRequest();
        request.setLinkman_post(tvPost.getText().toString());
        request.setLinkman_dept(tvDept.getText().toString());
        request.setLinkman_name(tvName.getText().toString());
        request.setCustom_name(tvClientName.getText().toString());
        request.setCustom_code(customerModel.getCustom_code());

        progresser.showProgress();
        mPresenter.addLinkman(request, new ResponseListener() {
            @Override
            public void onSuccess() {
                progresser.showContent();
                ToastUtil.toast(mContext,"添加成功");
                NewContactsActivity.this.finish();
            }

            @Override
            public void onFaild(NetResponse response) {
                progresser.showContent();
                ToastUtil.toast(mContext,response.getMsg());
            }
        });

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
        return R.layout.activity_new_contacts;
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
