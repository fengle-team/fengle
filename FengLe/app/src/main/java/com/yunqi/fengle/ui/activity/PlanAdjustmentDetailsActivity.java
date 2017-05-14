package com.yunqi.fengle.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.DeliveryDetail;
import com.yunqi.fengle.model.bean.Goods;
import com.yunqi.fengle.model.bean.GoodsAndWarehouse;
import com.yunqi.fengle.model.bean.PlanAdjustmentApply;
import com.yunqi.fengle.model.bean.PlanAdjustmentDetail;
import com.yunqi.fengle.model.bean.StatusInfo;
import com.yunqi.fengle.model.request.BillUpdateRequest;
import com.yunqi.fengle.presenter.PlanAdjustmentDetailsPresenter;
import com.yunqi.fengle.presenter.contract.PlanAdjustmentDetailsContract;
import com.yunqi.fengle.ui.adapter.PlanAdjustmentDetailTableDataAdapter;
import com.yunqi.fengle.ui.adapter.TableHeader1Adapter;
import com.yunqi.fengle.ui.fragment.dialog.SimpleDialogFragment;
import com.yunqi.fengle.ui.view.BottomOpraterPopWindow;
import com.yunqi.fengle.util.DialogHelper;
import com.yunqi.fengle.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import rx.functions.Action1;

/**
 * 计划调剂详情
 */
public class PlanAdjustmentDetailsActivity extends BaseActivity<PlanAdjustmentDetailsPresenter> implements PlanAdjustmentDetailsContract.View {
    private static final int SELECT_GOODS_REQUEST_CODE = 2;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tableView)
    TableView tableView;
    @BindView(R.id.txt_out_area)
    TextView txtOutArea;
    @BindView(R.id.txt_in_area)
    TextView txtInArea;
    @BindView(R.id.txt_status)
    TextView txtStatus;
    @BindView(R.id.llayout_status)
    LinearLayout llayoutStatus;
    @BindView(R.id.txt_remark)
    TextView txtRemark;
    @BindView(R.id.txt_code)
    TextView txtCode;
    @BindView(R.id.btn_select_goods)
    Button btnSelectGoods;
    @BindView(R.id.txt_preview)
    TextView txtPreview;

    BottomOpraterPopWindow popWindow;
    private int id;
    private int status = 0;
    private PlanAdjustmentApply planAdjustmentApply;
    private int position;
    private int mStatus;
    private PlanAdjustmentDetailTableDataAdapter adapter;
    private List<PlanAdjustmentDetail> mlistPlanAdjustmentDetail;
    private int positionGoods;
    private boolean isEditor;
    private String strStatus = "";

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_plan_adjustment_details;
    }

    @Override
    protected void initEventAndData() {
        userBean = App.getInstance().getUserInfo();
        planAdjustmentApply = (PlanAdjustmentApply) getIntent().getExtras().getSerializable("PlanAdjustmentApply");
        position = getIntent().getExtras().getInt("position");
        mStatus = getIntent().getExtras().getInt("status");
        status = planAdjustmentApply.status;
        id = planAdjustmentApply.id;
        if (mStatus == 1 || mStatus == 2) {
            setToolBar(toolbar, getString(R.string.module_plan_adjustment_detail), getString(R.string.operater), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showBottomOpraterPopWindow();
                }
            });
        }
        else {
            setToolBar(toolbar, getString(R.string.module_plan_adjustment_detail));
        }
        initData();
        setWidgetListener();
    }

    private void initData() {
        txtOutArea.setText(planAdjustmentApply.from_area_detail.name);
        txtInArea.setText(planAdjustmentApply.to_area_detail.name);
        txtRemark.setText(planAdjustmentApply.remark);
        txtCode.setText(planAdjustmentApply.order_code);
        switch (status) {
            case 1:
                strStatus = getString(R.string.bill_status_1);
                isEditor = true;
                break;
            case 2: {
                String id = App.getInstance().getUserInfo().id;
                //如果单据是本人提交的，则是未完成状态
                if (id.equals(planAdjustmentApply.userid)) {
                    if (mStatus == 1) {
                        strStatus = getString(R.string.bill_status_2);
                    } else {
                        strStatus = getString(R.string.bill_status_undone);
                    }
                } else {
                    if (mStatus == 3) {
                        strStatus = getString(R.string.bill_status_5);
                    } else {
                        strStatus = getString(R.string.bill_status_2);
                    }
                }
            }
            break;
            case 3:
                strStatus = getString(R.string.bill_status_3);
                break;
            case 4:
                strStatus = getString(R.string.bill_status_4);
                if (mStatus == 2) {
                    isEditor = true;
                }
                break;
            default:
                strStatus = getString(R.string.bill_status_unknown);
                break;
        }
        txtStatus.setText(strStatus);
        final TableHeader1Adapter tableHeader1Adapter = new TableHeader1Adapter(this, getResources().getStringArray(R.array.header_title_add_delivey_request));
        tableView.setHeaderAdapter(tableHeader1Adapter);
        TableColumnWeightModel columnModel = new TableColumnWeightModel(5);
        columnModel.setColumnWeight(0, 2);
        columnModel.setColumnWeight(1, 1);
        columnModel.setColumnWeight(2, 1);
        columnModel.setColumnWeight(3, 1);
        columnModel.setColumnWeight(4, 1);
        tableView.setColumnModel(columnModel);
        tableView.addDataClickListener(new TableDataClickListener<PlanAdjustmentDetail>() {
            @Override
            public void onDataClicked(int rowIndex, PlanAdjustmentDetail planAdjustmentDetail) {

            }
        });
        if (isEditor) {
            btnSelectGoods.setVisibility(View.VISIBLE);
        }
        showData(planAdjustmentApply);
    }

    private void setWidgetListener() {
        tableView.addDataClickListener(new TableDataClickListener<PlanAdjustmentDetail>() {
            @Override
            public void onDataClicked(final int rowIndex, PlanAdjustmentDetail planAdjustmentDetail) {
                if (!isEditor) {
                    ToastUtil.showNoticeToast(PlanAdjustmentDetailsActivity.this, "单据已提交，不可操作！");
                    return;
                }
                DialogHelper.showDialog(PlanAdjustmentDetailsActivity.this, "确定删除?", new SimpleDialogFragment.OnSimpleDialogListener() {
                    @Override
                    public void onOk() {
                        positionGoods = rowIndex;
                        int id = mlistPlanAdjustmentDetail.get(rowIndex).id;
                        mPresenter.delSelectedGoods(id);
                    }
                });
            }
        });
        RxView.clicks(btnSelectGoods)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent intent = new Intent(PlanAdjustmentDetailsActivity.this, GoodsQueryActivity.class);
                        intent.putExtra("customer_code",App.getInstance().getUserInfo().user_code);
                        intent.putExtra("area_code",planAdjustmentApply.from_area_detail.area_code);
                        if (mlistPlanAdjustmentDetail != null && !mlistPlanAdjustmentDetail.isEmpty()) {
                            ArrayList<GoodsAndWarehouse> goodsArray = new ArrayList<>();
                            for (PlanAdjustmentDetail planAdjustmentDetail : mlistPlanAdjustmentDetail) {
                                GoodsAndWarehouse goodsAndWarehouse = new GoodsAndWarehouse();
                                goodsAndWarehouse.goods = planAdjustmentDetail;
                                goodsArray.add(goodsAndWarehouse);
                            }
                            intent.putExtra("goodsArray", goodsArray);
                        }
                        intent.putExtra("module", PlanAdjustmentDetailsActivity.this.getClass().getName());
                        startActivityForResult(intent, SELECT_GOODS_REQUEST_CODE);
                    }
                });
        RxView.clicks(txtPreview)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent intent = new Intent(PlanAdjustmentDetailsActivity.this, StatusDetailActivity.class);
                        intent.putExtra("order_code", planAdjustmentApply.order_code);
                        StatusInfo statusInfo=new StatusInfo();
                        statusInfo.record="生成单据号："+planAdjustmentApply.order_code;
                        statusInfo.create_time=planAdjustmentApply.create_time;
                        intent.putExtra("LastStatus", statusInfo);
                        startActivity(intent);
                    }
                });
    }

    private void updateBillStatus(int status) {
        if (mlistPlanAdjustmentDetail == null || mlistPlanAdjustmentDetail.isEmpty()) {
            ToastUtil.showNoticeToast(this, getString(R.string.warimg_unselect_goods));
            return;
        }
        //驳回再次提交
        BillUpdateRequest request = new BillUpdateRequest();
        request.id = planAdjustmentApply.id;
        request.order_code = planAdjustmentApply.order_code;
        request.status = status;
        List<Goods> listGoods = new ArrayList<>();
        for (PlanAdjustmentDetail planAdjustmentDetail : mlistPlanAdjustmentDetail) {
            listGoods.add(planAdjustmentDetail);
        }
        request.goods_array = listGoods;
        mPresenter.updateStatus(request);
    }


    public void showBottomOpraterPopWindow() {
        popWindow = new BottomOpraterPopWindow(this, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 隐藏弹出窗口
                popWindow.dismiss();
                switch (v.getId()) {
                    case R.id.btn_commit:
                        //未完成(撤回)
                        if (getString(R.string.bill_status_undone).equals(strStatus)) {
                            updateBillStatus(1);
                        }
                        //驳回和暂存(提交)
                        else if (getString(R.string.bill_status_4).equals(strStatus) || getString(R.string.bill_status_1).equals(strStatus)) {
                            updateBillStatus(2);
                        }
                        //待审核(审核)
                        else if (getString(R.string.bill_status_2).equals(strStatus)) {
                            mPresenter.approval(App.getInstance().getUserInfo().id, planAdjustmentApply.id+"", 3);
                        }
                        break;
                    case R.id.btn_temporary:
                        //未完成(删除)
                        if (getString(R.string.bill_status_undone).equals(strStatus)) {
                            deleteBill();
                        }
                        //驳回(删除)
                        else if (getString(R.string.bill_status_4).equals(strStatus)) {
                            deleteBill();
                        }
                        //暂存(删除)
                        else if (getString(R.string.bill_status_1).equals(strStatus)) {
                            deleteBill();
                        }
                        //待审核(驳回)
                        else if (getString(R.string.bill_status_2).equals(strStatus)) {
                            mPresenter.approval(App.getInstance().getUserInfo().id, planAdjustmentApply.id+"", 4);
                        }
                        break;
                    case R.id.btn_cancel:// 取消

                        break;
                    default:
                        break;
                }

            }
        });
        //未完成
        if (getString(R.string.bill_status_undone).equals(strStatus)) {
            popWindow.setPopWindowTexts(getResources().getStringArray(R.array.oprater_return));
        }
        //驳回
        else if (getString(R.string.bill_status_4).equals(strStatus)) {
            popWindow.setPopWindowTexts(getResources().getStringArray(R.array.oprater_bohui));
        }
        //提交待审核
        else if (getString(R.string.bill_status_2).equals(strStatus)) {
            popWindow.setPopWindowTexts(getResources().getStringArray(R.array.oprater_audit));
        }
        //暂存
        else if (getString(R.string.bill_status_1).equals(strStatus)) {
            popWindow.setPopWindowTexts(getResources().getStringArray(R.array.oprater_tempary));
        }
        popWindow.showAtLocation(findViewById(R.id.main_layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private void deleteBill() {
        DialogHelper.showDialog(this, "确定删除?", new SimpleDialogFragment.OnSimpleDialogListener() {
            @Override
            public void onOk() {
                mPresenter.delete(id);
            }
        });
    }

    @Override
    public void showLoading() {
        loadingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("加载中...");
        loadingDialog.show();
        loadingDialog.setCancelable(false);
    }

    @Override
    public void cancelLoading() {
        loadingDialog.dismiss();
    }

    @Override
    public void showError(String msg) {
        ToastUtil.showErrorToast(this, msg);
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


    private void showData(PlanAdjustmentApply planAdjustmentApply) {
        txtOutArea.setText(planAdjustmentApply.from_area_detail.name);
        txtInArea.setText(planAdjustmentApply.to_area_detail.name);
        mlistPlanAdjustmentDetail = planAdjustmentApply.detail;
        adapter = new PlanAdjustmentDetailTableDataAdapter(this, mlistPlanAdjustmentDetail);
        tableView.setDataAdapter(adapter);
    }

    @Override
    public void showContent(PlanAdjustmentApply planAdjustmentApply) {
        txtOutArea.setText(planAdjustmentApply.from_area_detail.name);
        txtInArea.setText(planAdjustmentApply.to_area_detail.name);
        mlistPlanAdjustmentDetail = planAdjustmentApply.detail;
        adapter = new PlanAdjustmentDetailTableDataAdapter(this, mlistPlanAdjustmentDetail);
        tableView.setDataAdapter(adapter);
    }

    @Override
    public void onSuccess(int opraterType) {
        if (opraterType == 0) {
            ToastUtil.showHookToast(this, "单据状态更新成功！");
            Intent intent = new Intent();
            intent.putExtra("status", status);
            setResult(ReturnRequestActivity.APPROVAL_DETAIL_RESULT_CODE, intent);
            finish();
        } else if (opraterType == 1) {
            ToastUtil.showHookToast(this, "单据删除成功！");
            Intent intent = new Intent();
            intent.putExtra("position", position);
            setResult(ReturnRequestActivity.DEL_DETAIL_RESULT_CODE, intent);
            finish();
        } else if (opraterType == 2) {
            ToastUtil.showHookToast(this, "所选货物删除成功！");
            mlistPlanAdjustmentDetail.remove(positionGoods);
            adapter.notifyDataSetChanged();
        } else if (opraterType == 3) {
            ToastUtil.showHookToast(this, "审批成功！");
            Intent intent = new Intent();
            intent.putExtra("status", status);
            setResult(ReturnRequestActivity.UPDATE_DETAIL_RESULT_CODE, intent);
            finish();
        } else if (opraterType == 4) {
            ToastUtil.showHookToast(this, "单据已驳回！");
            Intent intent = new Intent();
            intent.putExtra("status", status);
            setResult(ReturnRequestActivity.UPDATE_DETAIL_RESULT_CODE, intent);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_GOODS_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            ArrayList<GoodsAndWarehouse> goodsArray = (ArrayList<GoodsAndWarehouse>) data.getSerializableExtra("listSelectGoods");
            for (GoodsAndWarehouse goodsAndWarehouse : goodsArray) {
                PlanAdjustmentDetail planAdjustmentDetail = new PlanAdjustmentDetail();
                planAdjustmentDetail.warehouse_code = goodsAndWarehouse.goods.warehouse_code;
                planAdjustmentDetail.goods_warehouse = goodsAndWarehouse.goods.goods_warehouse;
                planAdjustmentDetail.goods_num = goodsAndWarehouse.goods.goods_num;
                planAdjustmentDetail.goods_id = goodsAndWarehouse.goods.goods_id;
                planAdjustmentDetail.goods_code = goodsAndWarehouse.goods.goods_code;
                planAdjustmentDetail.goods_name = goodsAndWarehouse.goods.goods_name;
                planAdjustmentDetail.goods_standard = goodsAndWarehouse.goods.goods_standard;
                planAdjustmentDetail.goods_units_num = goodsAndWarehouse.goods.goods_units_num;
                planAdjustmentDetail.goods_price = goodsAndWarehouse.goods.goods_price;
                mlistPlanAdjustmentDetail.add(planAdjustmentDetail);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
