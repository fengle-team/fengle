package com.yunqi.fengle.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.PictureConfig;
import com.yalantis.ucrop.entity.LocalMedia;
import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.Area;
import com.yunqi.fengle.model.bean.SpinnerBean;
import com.yunqi.fengle.model.request.AddMaintainRequest;
import com.yunqi.fengle.model.request.TypeRequest;
import com.yunqi.fengle.model.response.CustomersResponse;
import com.yunqi.fengle.presenter.AddMaintainPresenter;
import com.yunqi.fengle.presenter.contract.AddmaintainContract;
import com.yunqi.fengle.ui.adapter.GridImageAdapter;
import com.yunqi.fengle.ui.fragment.dialog.SpinnerDialogFragment;
import com.yunqi.fengle.ui.view.FullyGridLayoutManager;
import com.yunqi.fengle.ui.view.RecycleViewDivider;
import com.yunqi.fengle.ui.view.TimeSelectDialog;
import com.yunqi.fengle.ui.view.UnderLineEditTextEx;
import com.yunqi.fengle.ui.view.UnderLineTextEx;
import com.yunqi.fengle.util.DateUtil;
import com.yunqi.fengle.util.DialogHelper;
import com.yunqi.fengle.util.LogEx;
import com.yunqi.fengle.util.StringUtil;
import com.yunqi.fengle.util.ToastUtil;
import com.yunqi.fengle.util.map.LocationModel;
import com.yunqi.fengle.util.map.MapLocationMgr;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author: Huangweicai
 * @date 2017-02-17 22:35
 * @Description:添加维护
 */

public class AddMaintainActivity extends BaseActivity<AddMaintainPresenter> implements AddmaintainContract.View, GridImageAdapter.OnAddPicClickListener {

    public static final String TAG_NAME = "tagName";

    @BindView(R.id.rvPhoto)
    RecyclerView rvPhoto;
    @BindView(R.id.etLocation)
    UnderLineEditTextEx etLocation;

    @BindView(R.id.etClientName)
    UnderLineTextEx etClientName;
    @BindView(R.id.etStartTime)
    UnderLineTextEx etStartTime;
    @BindView(R.id.etEndTime)
    UnderLineTextEx etEndTime;
    @BindView(R.id.clientReceptionist)
    UnderLineEditTextEx clientReceptionist;
    @BindView(R.id.etPhone)
    UnderLineEditTextEx etPhone;
    @BindView(R.id.actionType)
    UnderLineTextEx actionType;
    @BindView(R.id.etFeedback)
    UnderLineEditTextEx etFeedback;
    @BindView(R.id.rgGroup)
    RadioGroup rgGroup;
    @BindView(R.id.rbNormal)
    RadioButton rbNormal;
    @BindView(R.id.rbImportant)
    RadioButton rbImportant;

    private SpinnerBean spinnerAction = new SpinnerBean();//活动类型



    private GridImageAdapter adapter;
    private MapLocationMgr mapLocationMgr;
    private LocationModel locationModel;

    private List<LocalMedia> selectMedia = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("添加维护");
        setTitleRight("提交");

        mapLocationMgr = new MapLocationMgr();
        initRecyclerView();
        initView();
        rgGroup.check(R.id.rbNormal);
        getData();
        initData();
    }

    private void initData() {
//        spinnerAction.addSpinner("1","活动类型001");
//        spinnerAction.addSpinner("2","活动类型002");
//        spinnerAction.addSpinner("3","活动类型003");
        if (getIntent().hasExtra(TAG_NAME)) {
            String clientName = getIntent().getStringExtra(TAG_NAME);
            etClientName.setText(clientName);
//            etClientName.setClickable(false);
            return;
        }
        findViewById(R.id.llClientName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent();
                mIntent.setClass(AddMaintainActivity.this, VisitingAddCustomerActivity3.class);
                startActivityForResult(mIntent,1);
            }
        });
    }

    public void getData() {

        mPresenter.getData(new ResponseListener() {
            @Override
            public void onSuccess(NetResponse response) {
                List<TypeRequest> reponseList = (List<TypeRequest>) response.getResult();
                spinnerAction.format(reponseList);
            }

            @Override
            public void onFaild(NetResponse response) {

            }
        });
    }

    private void initView() {
        etLocation.setOnClickListener(this);
    }

    private void initRecyclerView() {
        rvPhoto.setLayoutManager(new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false));
        adapter = new GridImageAdapter(this, this);

        rvPhoto.setAdapter(adapter);

    }

    @Override
    protected void onTitleRightClicked(View v) {
        if (selectMedia == null || selectMedia.size() == 0) {
            ToastUtil.toast(this,"未选择图片!");
            return;
        }
        ArrayList<String> fileList = new ArrayList<>();
        for (LocalMedia bean : selectMedia) {
            fileList.add(bean.getPath());
        }
        if (StringUtil.isViewEmpty(etClientName, etStartTime, etEndTime, clientReceptionist, etPhone, actionType
                , etFeedback)) {
            ToastUtil.toast(this, "请填写完整信息.");
            return;
        }

        if (!DateUtil.compareTime(etStartTime.getText().toString(), etEndTime.getText().toString())) {
            ToastUtil.toast(mContext, "结束时间必须晚于开始时间");
            return;
        }

        String type = AddMaintainRequest.TYPE_NORMAL;//设置问题类型
        if (rgGroup.getCheckedRadioButtonId() != R.id.rbNormal) {
            type = AddMaintainRequest.TYPE_IMPORTANT;
        }

        final AddMaintainRequest request = new AddMaintainRequest();
        request.setUserid(App.getInstance().getUserInfo().id);
        request.setClient_name(etClientName.getText().toString());
        request.setStart_time(etStartTime.getText().toString());
        request.setEnd_time(etEndTime.getText().toString());
        request.setClient_receptionist(clientReceptionist.getText().toString());
        request.setPhone(etPhone.getText().toString());
        request.setImages(fileList);
        request.setAction_type(spinnerAction.getKey());
        request.setAddress(locationModel.getAddress());
        request.setLat(locationModel.getLatitude() + "");
        request.setLng(locationModel.getLongitude() + "");
        request.setFeedback(etFeedback.getText().toString());
        request.setType(type);
        progresser.showProgress();
        mPresenter.doAddMaintain(request, new ResponseListener() {
            @Override
            public void onSuccess() {
                progresser.showContent();
                ToastUtil.toast(AddMaintainActivity.this,"提交成功!");
                AddMaintainActivity.this.finish();
            }

            @Override
            public void onFaild(NetResponse response) {
                progresser.showContent();
                ToastUtil.toast(AddMaintainActivity.this,response.msg);
            }
        });
    }


    @OnClick(R.id.rlStartTime)
    public void clickStartTime() {
        new TimeSelectDialog(this, new TimeSelectDialog.TimeSelectListener() {
            @Override
            public void onTimeSelected(long ltime, String strTime) {
                etStartTime.setText(strTime);
            }
        }).show();
    }


    @OnClick(R.id.etStartTime)
    public void clicketStartTime() {
        new TimeSelectDialog(this, new TimeSelectDialog.TimeSelectListener() {
            @Override
            public void onTimeSelected(long ltime, String strTime) {
                etStartTime.setText(strTime);
            }
        }).show();
    }

    @OnClick(R.id.rlEndTime)
    public void clickEndTime() {
        new TimeSelectDialog(this, new TimeSelectDialog.TimeSelectListener() {
            @Override
            public void onTimeSelected(long ltime, String strTime) {
                etEndTime.setText(strTime);
            }
        }).show();
    }

//    @OnClick(R.id.llClientName)
//    public void clickClientName() {
//        Intent mIntent = new Intent();
//        mIntent.setClass(this, VisitingAddCustomerActivity3.class);
//        startActivityForResult(mIntent,1);
//    }

    @OnClick(R.id.llActionType)
    public void clickActionType() {
        DialogHelper.showSpinnerDialog(this, spinnerAction, new SpinnerDialogFragment.OnSpinnerDialogListener() {
            @Override
            public void onItemSelected(int position, SpinnerBean bean) {
                spinnerAction.setKey(bean.getKey());
                spinnerAction.setValue(bean.getValue());
                actionType.setText(bean.getValue());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            CustomersResponse response = (CustomersResponse) data.getSerializableExtra(VisitingAddCustomerActivity3.TAG_RESULT);
            etClientName.setText(response.getName());
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.etLocation://定位
                doLocation();
                break;

        }
    }

    private void doLocation() {
        mapLocationMgr.start(new ResponseListener() {
            @Override
            public void onSuccess(NetResponse response) {
                locationModel = (LocationModel) response.getResult();
                etLocation.setText(locationModel.getAddress());
            }

            @Override
            public void onFaild(NetResponse response) {
                ToastUtil.toast(AddMaintainActivity.this,response.getMsg());
            }
        });
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
                selectMedia.remove(position);
                adapter.notifyItemRemoved(position);
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
        return R.layout.activity_add_maintain;
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
        mapLocationMgr.onDestory();
        super.onDestroy();
    }

}
