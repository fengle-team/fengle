package com.yunqi.fengle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.Area;
import com.yunqi.fengle.model.bean.SpinnerBean;
import com.yunqi.fengle.model.request.ActivityAddPlanRequest;
import com.yunqi.fengle.model.response.CustomersResponse;
import com.yunqi.fengle.presenter.ActivityNewPlanPresenter;
import com.yunqi.fengle.presenter.contract.ActivityNewPlanContract;
import com.yunqi.fengle.ui.fragment.dialog.SpinnerDialogFragment;
import com.yunqi.fengle.ui.view.TimeSelectDialog;
import com.yunqi.fengle.ui.view.UnderLineEditNewPlanEx;
import com.yunqi.fengle.ui.view.UnderLineTextNewPlanEx;
import com.yunqi.fengle.util.DateUtil;
import com.yunqi.fengle.util.DialogHelper;
import com.yunqi.fengle.util.StringUtil;
import com.yunqi.fengle.util.ToastUtil;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 17:33
 * @Description: 活动管理--->活动计划 {@link ActivityPlanActivity}
 */

public class ActivityNewPlanActivity extends BaseActivity<ActivityNewPlanPresenter> implements ActivityNewPlanContract.View{

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

    @BindView(R.id.etRegion)
    UnderLineTextNewPlanEx etRegion;
    @BindView(R.id.etShopName)
    UnderLineEditNewPlanEx etShopName;
    @BindView(R.id.etApplyReason)
    UnderLineEditNewPlanEx etApplyReason;
    @BindView(R.id.etActionPlan)
    UnderLineEditNewPlanEx etActionPlan;
    @BindView(R.id.etBaoxiaoBudget)
    UnderLineEditNewPlanEx etBaoxiaoBudget;
    @BindView(R.id.etOtherSupport)
    UnderLineEditNewPlanEx etOtherSupport;
    @BindView(R.id.etApplyBudget)
    UnderLineEditNewPlanEx etApplyBudget;
//
    @BindView(R.id.etApplyName)
    UnderLineEditNewPlanEx etApplyName;
    @BindView(R.id.etRemark)
    UnderLineEditNewPlanEx etRemark;


    CustomersResponse response;

    private SpinnerBean spinnerAction = new SpinnerBean();//活动类型
    private SpinnerBean spinnerBaoxiao = new SpinnerBean();//报销类型



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("我的计划");
        setTitleRight("提交");

        initData();
    }

    private void initData() {
        etApplyName.setText(App.getInstance().getUserInfo().real_name);

        progresser.showProgress();
        mPresenter.getData(new ResponseListener() {
            @Override
            public void onSuccess() {
                spinnerAction.format(mPresenter.spinnerAction);
                spinnerBaoxiao.format(mPresenter.spinnerReimburse);
                progresser.showContent();
            }

            @Override
            public void onFaild(NetResponse response) {
                progresser.showContent();
                ToastUtil.toast(mContext,response.getMsg());
            }
        });
    }

    @Override
    protected void onTitleRightClicked(View v) {
        if (StringUtil.isViewEmpty(etRegion, etStartTime, etEndTime, etClientName, etShopName, etActionType
                , etApplyBudget, etBaoxiaoBudget, etBaoxiaoType, etApplyName)) {
            ToastUtil.toast(this, "请填写完整信息.");
            return;
        }

        if (!DateUtil.compareTime(etStartTime.getText().toString(), etEndTime.getText().toString())) {
            ToastUtil.toast(mContext, "结束时间必须晚于开始时间");
            return;
        }

        ActivityAddPlanRequest request = new ActivityAddPlanRequest();
        request.setUserid(App.getInstance().getUserInfo().id);
        request.setRegion(etRegion.getText().toString());
        request.setStart_time(etStartTime.getText().toString());
        request.setEnd_time(etEndTime.getText().toString());
        request.setClient_name(etClientName.getText().toString());
        request.setShop_name(etShopName.getText().toString());
        request.setAction_type(spinnerAction.getKey());
        request.setApply_reason(etApplyReason.getText().toString());
        request.setAction_plan(etActionPlan.getText().toString());
        request.setApply_budget(etApplyBudget.getText().toString());
        request.setBaoxiao_budget(etBaoxiaoBudget.getText().toString());
        request.setBaoxiao_type(spinnerBaoxiao.getKey());
        request.setOther_support(etOtherSupport.getText().toString());
        request.setApply_name(etApplyName.getText().toString());
        request.setRemark(etRemark.getText().toString());

        progresser.showProgress();
        mPresenter.activityNewPlan(request, new ResponseListener() {
            @Override
            public void onSuccess() {
                progresser.showContent();
                ToastUtil.toast(ActivityNewPlanActivity.this,"增加成功.");
                finish();
            }

            @Override
            public void onFaild(NetResponse response) {
                progresser.showContent();
                ToastUtil.toast(mContext,response.getMsg());
            }
        });
    }

    @OnClick(R.id.llStartTime)
    public void onStartTime() {
        new TimeSelectDialog(this, new TimeSelectDialog.TimeSelectListener() {
            @Override
            public void onTimeSelected(long ltime, String strTime) {
                etStartTime.setText(strTime);
            }
        }).show();
    }

    @OnClick(R.id.llEndTime)
    public void onEndTime() {
        new TimeSelectDialog(this, new TimeSelectDialog.TimeSelectListener() {
            @Override
            public void onTimeSelected(long ltime, String strTime) {
                etEndTime.setText(strTime);
            }
        }).show();
    }

    @OnClick(R.id.llClientName)
    public void onClientName() {
        Intent mIntent = new Intent();
        mIntent.setClass(this, VisitingAddCustomerActivity3.class);
        startActivityForResult(mIntent,1);
    }


    @OnClick(R.id.llRegion)
    public void onllRegion() {
        Intent intent = new Intent(this, AreaQueryActivity.class);
        startActivityForResult(intent, 111);
    }


    @OnClick(R.id.llActionType)
    public void onActionType() {
        DialogHelper.showSpinnerDialog(this, spinnerAction, new SpinnerDialogFragment.OnSpinnerDialogListener() {
            @Override
            public void onItemSelected(int position, SpinnerBean bean) {
                spinnerAction.setKey(bean.getKey());
                spinnerAction.setValue(bean.getValue());
                etActionType.setText(bean.getValue());
            }
        });
    }

    @OnClick(R.id.llBaoxiaoType)
    public void onBaoxiaoType() {
        DialogHelper.showSpinnerDialog(this, spinnerBaoxiao, new SpinnerDialogFragment.OnSpinnerDialogListener() {
            @Override
            public void onItemSelected(int position, SpinnerBean bean) {
                spinnerBaoxiao.setKey(bean.getKey());
                spinnerBaoxiao.setValue(bean.getValue());
                etBaoxiaoType.setText(bean.getValue());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 111 && resultCode == RESULT_OK) {
            Area area = (Area) data.getSerializableExtra("SelectArea");
            etRegion.setText(area.name + "");
            return;
        }

        if (resultCode == RESULT_OK) {
            response = (CustomersResponse) data.getSerializableExtra(VisitingAddCustomerActivity3.TAG_RESULT);
            etClientName.setText(response.getName());
        }
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
        return R.layout.activity_activity_new_plan2;
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
