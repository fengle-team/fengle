package com.yunqi.fengle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.SpinnerBean;
import com.yunqi.fengle.model.request.VisitingUpdateRequest;
import com.yunqi.fengle.model.response.ActivityAddResponse;
import com.yunqi.fengle.model.response.VisitingPlanResponse;
import com.yunqi.fengle.presenter.ActivityPlanDetailPresenter;
import com.yunqi.fengle.presenter.ActivityPlanPresenter;
import com.yunqi.fengle.presenter.contract.ActivityPlanContract;
import com.yunqi.fengle.ui.adapter.ActivityPlanAdapter;
import com.yunqi.fengle.ui.fragment.dialog.SpinnerDialogFragment;
import com.yunqi.fengle.ui.swipe.SwipyRefreshLayout;
import com.yunqi.fengle.ui.swipe.SwipyRefreshLayoutDirection;
import com.yunqi.fengle.ui.view.RecycleViewDivider;
import com.yunqi.fengle.ui.view.TimeSelectDialog;
import com.yunqi.fengle.ui.view.UnderLineTextNewPlanEx;
import com.yunqi.fengle.util.DateUtil;
import com.yunqi.fengle.util.DialogHelper;
import com.yunqi.fengle.util.RxBus;
import com.yunqi.fengle.util.ToastUtil;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 17:00
 * @Description: 活动计划 --- > 修改计划
 *              {@link ActivitiesManagerActivity}
 */

public class ActivityPlanDetailActivity extends BaseActivity<ActivityPlanDetailPresenter> implements ActivityPlanContract.View{

    public static final String TAG_DETAIL = "detail";

    @BindView(R.id.etClientName)
    UnderLineTextNewPlanEx etClientName;
    @BindView(R.id.etResponsibleName)
    UnderLineTextNewPlanEx etResponsibleName;
    @BindView(R.id.etStartTime)
    UnderLineTextNewPlanEx etStartTime;
    @BindView(R.id.etEndTime)
    UnderLineTextNewPlanEx etEndTime;
    @BindView(R.id.tvStatus)
    UnderLineTextNewPlanEx tvStatus;

    private VisitingPlanResponse data;

    private SpinnerBean spinnerStatus = new SpinnerBean();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("客户拜访");
        setTitleRight("提交");
        initData();
    }


    @Override
    protected void onTitleRightClicked(View v) {
        if (TextUtils.isEmpty(etStartTime.getText().toString()) || TextUtils.isEmpty(etEndTime.getText().toString()) ||
                TextUtils.isEmpty(tvStatus.getText().toString())) {
            ToastUtil.toast(this,"请填写完整信息");
            return;
        }

        if (!DateUtil.compareTime(etStartTime.getText().toString(), etEndTime.getText().toString())) {
            ToastUtil.toast(mContext, "结束时间必须晚于开始时间");
            return;
        }

        progresser.showProgress();
        final VisitingUpdateRequest request = new VisitingUpdateRequest();
        request.setId(data.getId() + "");
        request.setStatus(spinnerStatus.getKey());
        request.setStart_time(etStartTime.getText().toString());
        request.setEndtime(etEndTime.getText().toString());
        mPresenter.updateStatus(request, new ResponseListener() {
            @Override
            public void onSuccess() {
                progresser.showContent();
                RxBus.get().post(VistingAddVisteActivity.RX_TAG,true);
                ToastUtil.toast(mContext,"提交成功");
                ActivityPlanDetailActivity.this.finish();
            }

            @Override
            public void onFaild(NetResponse response) {
                progresser.showContent();
                ToastUtil.toast(mContext,response.getMsg());
            }
        });




    }

    private void initData() {
        if (getIntent().hasExtra(TAG_DETAIL)) {
            data = (VisitingPlanResponse) getIntent().getSerializableExtra(TAG_DETAIL);
        }
        if (data == null) {
            ToastUtil.toast(this,"...");
            finish();
            return;
        }
        spinnerStatus.addSpinner("1","未完成");
        spinnerStatus.addSpinner("2","已完成");

        etClientName.setText(data.getClient_name());
        etResponsibleName.setText(data.getResponsible_name());
    }

    @OnClick(R.id.llStatus)
    public void onStatusClick() {
        DialogHelper.showSpinnerDialog(this, spinnerStatus, new SpinnerDialogFragment.OnSpinnerDialogListener() {
            @Override
            public void onItemSelected(int position, SpinnerBean bean) {
                spinnerStatus.setKey(bean.getKey());
                spinnerStatus.setValue(bean.getValue());
                tvStatus.setText(spinnerStatus.getValue());
            }
        });
    }
    @OnClick(R.id.llStartTime)
    public void onStartTimeClick() {
        new TimeSelectDialog(this, new TimeSelectDialog.TimeSelectListener() {
            @Override
            public void onTimeSelected(long ltime, String strTime) {
                etStartTime.setText(strTime);
            }
        }).show();

    }

    @OnClick(R.id.llEndTime)
    public void onEndTimeClick() {
        new TimeSelectDialog(this, new TimeSelectDialog.TimeSelectListener() {
            @Override
            public void onTimeSelected(long ltime, String strTime) {
                etEndTime.setText(strTime);
            }
        }).show();
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
        return R.layout.activity_activity_plan_detail;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
