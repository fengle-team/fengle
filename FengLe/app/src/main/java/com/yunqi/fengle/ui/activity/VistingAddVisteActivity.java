package com.yunqi.fengle.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.SpinnerBean;
import com.yunqi.fengle.model.request.VisitingAddRequest;
import com.yunqi.fengle.model.response.CustomersResponse;
import com.yunqi.fengle.presenter.VisitingAddVistePresenter;
import com.yunqi.fengle.presenter.contract.VisitingPlanContract;
import com.yunqi.fengle.ui.fragment.dialog.SpinnerDialogFragment;
import com.yunqi.fengle.ui.view.TimeSelectDialog;
import com.yunqi.fengle.ui.view.UnderLineEditNewPlanEx;
import com.yunqi.fengle.ui.view.UnderLineEditNormalEx;
import com.yunqi.fengle.ui.view.UnderLineTextNewPlanEx;
import com.yunqi.fengle.util.DialogHelper;
import com.yunqi.fengle.util.RxBus;
import com.yunqi.fengle.util.ToastUtil;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author: Huangweicai
 * @date 2017-03-07 14:51
 * @Description:添加拜访计划
 *              {@link VisitingPlanActivity} 主界面
 *              {@link VisitingAddCustomerActivity} 主界面--->选择客户
 */
public class VistingAddVisteActivity extends BaseActivity<VisitingAddVistePresenter> implements VisitingPlanContract.View{

//    @BindView(R.id.rvVisPlan)
//    RecyclerView rvVisPlan;

    public static final String TAG_SELECTED = "tagSelected";

    public static final String RX_TAG = "rxTag";

    CustomersResponse selectedData;

    VisitingAddRequest request;

    @BindView(R.id.tvContact)
    UnderLineEditNewPlanEx tvContact;
    @BindView(R.id.tvTime)
    UnderLineTextNewPlanEx tvTime;
    @BindView(R.id.tvStatus)
    UnderLineTextNewPlanEx tvStatus;
    @BindView(R.id.tvCompanyName)
    UnderLineTextNewPlanEx tvCompanyName;
    @BindView(R.id.tvSale)
    UnderLineTextNewPlanEx tvSale;
    @BindView(R.id.etReason)
    UnderLineEditNewPlanEx etReason;

    SpinnerBean spinnerStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("客户拜访");
        setTitleRight("提交");

        initData();
    }

    private void initData() {
        if (getIntent().hasExtra(TAG_SELECTED)) {
            selectedData = (CustomersResponse) getIntent().getSerializableExtra(TAG_SELECTED);
            tvCompanyName.setText(selectedData.getCompany_name());
        }
        tvSale.setText(App.getInstance().getUserInfo().real_name);

        spinnerStatus = new SpinnerBean();
        spinnerStatus.addSpinner("1","未完成");
        spinnerStatus.addSpinner("2","已完成");

        spinnerStatus.setKey("1");

        tvStatus.setText(spinnerStatus.getValue());
    }


    @OnClick(R.id.llTime)
    public void onTimeClick() {
        new TimeSelectDialog(this, new TimeSelectDialog.TimeSelectListener() {
            @Override
            public void onTimeSelected(long ltime, String strTime) {
                tvTime.setText(strTime);
            }
        }).show();
    }

    @OnClick(R.id.llStatus)
    public void onStatusClick() {
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
    protected void onTitleRightClicked(View v) {
        if (TextUtils.isEmpty(tvContact.getText().toString()) || TextUtils.isEmpty(etReason.getText().toString()) || TextUtils.isEmpty(tvTime.getText().toString())) {
            ToastUtil.toast(mContext,"请填写完整信息");
            return;
        }

        request = new VisitingAddRequest();
        request.setClient_name(selectedData.getCompany_name());
        request.setPlan_time(tvTime.getText().toString());
        request.setUserid(App.getInstance().getUserInfo().id);
        request.setReason(etReason.getText().toString());
        request.setResponsible_name(tvContact.getText().toString());
        request.setStatus(spinnerStatus.getKey());

        progresser.showProgress();
        mPresenter.addVistePlan(request, new ResponseListener() {
            @Override
            public void onSuccess() {
                ToastUtil.toast(mContext, "提交成功");
                progresser.showContent();
                RxBus.get().post(RX_TAG,true);
                VistingAddVisteActivity.this.finish();
            }

            @Override
            public void onFaild(NetResponse response) {
                progresser.showContent();
                ToastUtil.toast(mContext, response.getMsg());
            }
        });
    }

//    private void initRecyclerView() {
//        rvVisPlan.setLayoutManager(new LinearLayoutManager(this));
//        rvVisPlan.addItemDecoration(new RecycleViewDivider(this,RecycleViewDivider.VERTICAL_LIST));
//
//        List<Object> test = new ArrayList<>();
//        test.add("11");
//        test.add("11");
//        test.add("11");
//        rvVisPlan.setAdapter(new VisitingAddVisteAdapter(test));
//    }


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
        return R.layout.item_visiting_add_visite2;
    }
//    @Override
//    protected int getLayout() {
//        return R.layout.activity_visiting_add_plan;
//    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void showLoading() {

    }
}
