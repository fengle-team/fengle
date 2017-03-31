package com.yunqi.fengle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.PictureConfig;
import com.yalantis.ucrop.entity.LocalMedia;
import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.Area;
import com.yunqi.fengle.model.bean.SpinnerBean;
import com.yunqi.fengle.model.request.ActivitySummaryRequest;
import com.yunqi.fengle.model.request.TypeRequest;
import com.yunqi.fengle.model.response.CustomersResponse;
import com.yunqi.fengle.presenter.ActivitySummaryPresenter;
import com.yunqi.fengle.ui.adapter.GridImageAdapter;
import com.yunqi.fengle.ui.fragment.dialog.SpinnerDialogFragment;
import com.yunqi.fengle.ui.view.FullyGridLayoutManager;
import com.yunqi.fengle.ui.view.TimeSelectDialog;
import com.yunqi.fengle.ui.view.UnderLineEditNewPlanEx;
import com.yunqi.fengle.ui.view.UnderLineTextNewPlanEx;
import com.yunqi.fengle.util.DateUtil;
import com.yunqi.fengle.util.DialogHelper;
import com.yunqi.fengle.util.LogEx;
import com.yunqi.fengle.util.StringUtil;
import com.yunqi.fengle.util.ToastUtil;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author: Huangweicai
 * @date 2017-02-18 17:00
 * @Description: 活动总结 {@link ActivitiesManagerActivity}
 */

public class ActivitySummaryActivity extends BaseActivity<ActivitySummaryPresenter> implements GridImageAdapter.OnAddPicClickListener {

    @BindView(R.id.rvPhoto)
    RecyclerView rvPhoto;
    @BindView(R.id.etRegion)
    UnderLineTextNewPlanEx etRegion;
    @BindView(R.id.etClientName)
    UnderLineTextNewPlanEx etClientName;
    @BindView(R.id.etStartTime)
    UnderLineTextNewPlanEx etStartTime;
    @BindView(R.id.etEndTime)
    UnderLineTextNewPlanEx etEndTime;
    @BindView(R.id.etActionType)
    UnderLineTextNewPlanEx etActionType;
    @BindView(R.id.etShopName)
    UnderLineEditNewPlanEx etShopName;
    @BindView(R.id.etActionSubmit)
    UnderLineEditNewPlanEx etActionSubmit;
    @BindView(R.id.etActionSummary)
    UnderLineEditNewPlanEx etActionSummary;

    private SpinnerBean spinnerAction = new SpinnerBean();//活动类型

    CustomersResponse response;


    private GridImageAdapter adapter;

    private List<LocalMedia> selectMedia = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("活动总结");
        setTitleRight("提交");

        initRecyclerView();

        initData();
    }

    private void initData() {
//        spinnerAction.addSpinner("1", "活动类型001");
//        spinnerAction.addSpinner("2", "活动类型002");
//        spinnerAction.addSpinner("3", "活动类型003");

        progresser.showProgress();
        mPresenter.getData(new ResponseListener() {
            @Override
            public void onSuccess(NetResponse response) {
                List<TypeRequest> requestList = (List<TypeRequest>) response.getResult();
                spinnerAction.format(requestList);
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
        if (selectMedia == null || selectMedia.size() == 0) {
            ToastUtil.toast(this, "未选择图片!");
            return;
        }
        ArrayList<String> fileList = new ArrayList<>();
        for (LocalMedia bean : selectMedia) {
            fileList.add(bean.getPath());
        }
        if (StringUtil.isViewEmpty(etRegion, etStartTime, etEndTime, etClientName, etActionType, etShopName
                , etActionSubmit, etActionSummary)) {
            ToastUtil.toast(this, "请填写完整信息.");
            return;
        }

        if (!DateUtil.compareTime(etStartTime.getText().toString(), etEndTime.getText().toString())) {
            ToastUtil.toast(mContext, "结束时间必须晚于开始时间");
            return;
        }

        ActivitySummaryRequest request = new ActivitySummaryRequest();

        request.setRegion(etRegion.getText().toString());
        request.setStart_time(etStartTime.getText().toString());
        request.setEnd_time(etEndTime.getText().toString());
        request.setClient_name(etClientName.getText().toString());
        request.setAction_type(spinnerAction.getKey());
        request.setShop_name(etShopName.getText().toString());
        request.setAction_submit(etActionSubmit.getText().toString());
        request.setSummary(etActionSummary.getText().toString());
        request.setClient_id(response.getId() + "");
        request.setImage_urls(fileList);
        request.setUserid(App.getInstance().getUserInfo().id);

        progresser.showProgress();
        mPresenter.addSummary(request, new ResponseListener() {
            @Override
            public void onSuccess() {
                ToastUtil.toast(mContext, "提交成功");
                progresser.showContent();
                ActivitySummaryActivity.this.finish();
            }

            @Override
            public void onFaild(NetResponse response) {
                ToastUtil.toast(mContext, response.getMsg());
                progresser.showContent();
            }
        });

    }

    private void initRecyclerView() {
        rvPhoto.setLayoutManager(new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false));
        adapter = new GridImageAdapter(this, this);

        rvPhoto.setAdapter(adapter);

    }


    @Override
    public void onAddPicClick(int type, int position) {
        switch (type) {
            case 0:
                // 进入相册
                int selector = R.drawable.select_cb;
                FunctionConfig config = new FunctionConfig();
                config.setEnablePixelCompress(true);
                config.setEnableQualityCompress(true);
                config.setMaxSelectNum(5);
                config.setCheckNumMode(true);
                config.setCompressQuality(100);
                config.setImageSpanCount(4);
                config.setEnablePreview(true);
                config.setSelectMedia(selectMedia);
                config.setCheckedBoxDrawable(selector);
                // 先初始化参数配置，在启动相册
                PictureConfig.init(config);
                PictureConfig.getPictureConfig().openPhoto(mContext, resultCallback);

                break;
            case 1:
                // 删除图片
                if (position >= 0 && position < selectMedia.size()) {
                    selectMedia.remove(position);
                    adapter.notifyItemRemoved(position);
                }
                break;
        }
    }

    /**
     * 图片回调方法
     */
    private PictureConfig.OnSelectResultCallback resultCallback = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            selectMedia = resultList;
            LogEx.i(selectMedia.size() + "");
            if (selectMedia != null) {
                adapter.setList(selectMedia);
                adapter.notifyDataSetChanged();
            }
        }
    };


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

    @OnClick(R.id.llEndTime)
    public void onEndTime() {
        new TimeSelectDialog(this, new TimeSelectDialog.TimeSelectListener() {
            @Override
            public void onTimeSelected(long ltime, String strTime) {
                etEndTime.setText(strTime);
            }
        }).show();
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


    @OnClick(R.id.llClientName)
    public void onClientName2() {
        Intent mIntent = new Intent();
        mIntent.setClass(this, VisitingAddCustomerActivity3.class);
        startActivityForResult(mIntent, 1);
    }

    @OnClick(R.id.llRegion)
    public void onllRegion() {
        Intent intent = new Intent(this, AreaQueryActivity.class);
        startActivityForResult(intent, 111);
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
        return R.layout.activity_activity_summary2;
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
