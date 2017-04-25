package com.yunqi.fengle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioGroup;

import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.SpinnerBean;
import com.yunqi.fengle.model.response.ActivityAddResponse;
import com.yunqi.fengle.presenter.ActivityPlanPresenter;
import com.yunqi.fengle.presenter.ActivityPlanUpdatePresenter;
import com.yunqi.fengle.presenter.contract.ActivityPlanContract;
import com.yunqi.fengle.ui.adapter.ActivityPlanManagerAdapter;
import com.yunqi.fengle.ui.fragment.dialog.SpinnerDialogFragment;
import com.yunqi.fengle.ui.swipe.SwipyRefreshLayout;
import com.yunqi.fengle.ui.swipe.SwipyRefreshLayoutDirection;
import com.yunqi.fengle.ui.view.RecycleViewDivider;
import com.yunqi.fengle.ui.view.UnderLineEditNewPlanContentEx;
import com.yunqi.fengle.ui.view.UnderLineEditNewPlanEx;
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
 * @Description: 活动计划
 *              {@link ActivitiesManagerActivity}
 */

public class ActivityPlanUpdateActivity extends BaseActivity<ActivityPlanUpdatePresenter> {

    public static final String TAG = "tag";
    public static final String TAG_2 = "tag2";

    @BindView(R.id.etStartTime)
    UnderLineTextNewPlanEx etStartTime;
    @BindView(R.id.etEndTime)
    UnderLineTextNewPlanEx etEndTime;
    @BindView(R.id.etBaoxiaoType)
    UnderLineTextNewPlanEx etBaoxiaoType;
    @BindView(R.id.etActionType)
    UnderLineTextNewPlanEx etActionType;
    @BindView(R.id.etClientName)
    UnderLineTextNewPlanEx etClientName;

    @BindView(R.id.tvStatus)
    UnderLineTextNewPlanEx tvStatus;

    @BindView(R.id.etRegion)
    UnderLineTextNewPlanEx etRegion;
    @BindView(R.id.etShopName)
    UnderLineTextNewPlanEx etShopName;
    @BindView(R.id.etApplyReason)
    UnderLineTextNewPlanEx etApplyReason;
    @BindView(R.id.etActionPlan)
    UnderLineTextNewPlanEx etActionPlan;
    @BindView(R.id.etBaoxiaoBudget)
    UnderLineTextNewPlanEx etBaoxiaoBudget;
    @BindView(R.id.etOtherSupport)
    UnderLineTextNewPlanEx etOtherSupport;
    @BindView(R.id.etApplyBudget)
    UnderLineTextNewPlanEx etApplyBudget;
    //
    @BindView(R.id.etApplyName)
    UnderLineTextNewPlanEx etApplyName;
    @BindView(R.id.etRemark)
    UnderLineTextNewPlanEx etRemark;

    ActivityAddResponse response;

    private SpinnerBean spinnerStatus = new SpinnerBean();//活动类型


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("活动计划");


        if (getIntent().hasExtra(TAG_2)) {
            findViewById(R.id.llStatus).setVisibility(View.GONE);
        } else {
            setTitleRight("提交");
        }

        spinnerStatus.addSpinner("3","审核通过");
        spinnerStatus.addSpinner("4","审核驳回");

        initData();
        initView();


    }

    @Override
    protected void onTitleRightClicked(View v) {
        if (tvStatus.getText().equals("")) {
            ToastUtil.toast(mContext,"请选择状态");
            return;
        }

        progresser.showProgress();
        mPresenter.updatePlanUpdateStatus(App.getInstance().getUserInfo().id, response.getOrder_code(), spinnerStatus.getKey(), new ResponseListener() {
            @Override
            public void onSuccess() {
                progresser.showContent();
                ToastUtil.toast(mContext,"审核成功");
                RxBus.get().post(ActivityPlanManagerActivity.RX_TAG,true);
                ActivityPlanUpdateActivity.this.finish();
            }

            @Override
            public void onFaild(NetResponse response) {
                progresser.showContent();
                ToastUtil.toast(mContext,response.getMsg());
            }
        });

    }

    private void initView() {
        etStartTime.setText(DateUtil.formatB(response.getStart_time()));
        etEndTime.setText(DateUtil.formatB(response.getEnd_time()));
        etBaoxiaoType.setText(response.getBaoxiao_type() + "");
        etActionType.setText(response.getAction_type() + "");
        etClientName.setText(response.getClient_name());
        etRegion.setText(response.getRegion());
        etShopName.setText(response.getShop_name());
        etApplyReason.setText(response.getApply_reason());
        etActionPlan.setText(response.getAction_plan());
        etBaoxiaoBudget.setText(response.getBaoxiao_budget() + "");
        etOtherSupport.setText(response.getOther_support());
        etApplyBudget.setText(response.getApply_budget() + "");
        etApplyName.setText(response.getApply_name());
        etRemark.setText(response.getRemark());


    }

    private void initData() {
        if (!getIntent().hasExtra(TAG)) {
            ToastUtil.toast(mContext,"null");
            finish();
            return;
        }
        response = (ActivityAddResponse) getIntent().getSerializableExtra(TAG);
    }

    @OnClick(R.id.llStatus)
    public void llStatus() {
        DialogHelper.showSpinnerDialog(this, spinnerStatus, new SpinnerDialogFragment.OnSpinnerDialogListener() {
            @Override
            public void onItemSelected(int position, SpinnerBean bean) {
                spinnerStatus.setKey(bean.getKey());
                spinnerStatus.setValue(bean.getValue());
                tvStatus.setText(bean.getValue());
            }
        });
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getViewModel() {
        return MODE_VIEW_02;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_activity_new_plan_update_status;
    }

    @Override
    protected void initEventAndData() {

    }

}
