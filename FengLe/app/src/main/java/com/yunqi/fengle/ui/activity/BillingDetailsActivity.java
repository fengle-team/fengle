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
import com.yunqi.fengle.model.bean.BillingApply;
import com.yunqi.fengle.model.bean.BillingDetail;
import com.yunqi.fengle.model.bean.Goods;
import com.yunqi.fengle.model.bean.GoodsAndWarehouse;
import com.yunqi.fengle.model.bean.StatusInfo;
import com.yunqi.fengle.model.request.BillUpdateRequest;
import com.yunqi.fengle.presenter.BillingDetailsPresenter;
import com.yunqi.fengle.presenter.contract.BillingDetailsContract;
import com.yunqi.fengle.ui.adapter.BillingDetailTableDataAdapter;
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
 * 开票申请详情
 */
public class BillingDetailsActivity extends BaseActivity<BillingDetailsPresenter> implements BillingDetailsContract.View{
    private static final int SELECT_GOODS_REQUEST_CODE = 2;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tableView)
    TableView tableView;
    @BindView(R.id.txt_customer)
    TextView txtCustomer;
    @BindView(R.id.txt_status)
    TextView txtStatus;
    @BindView(R.id.llayout_status)
    LinearLayout llayoutStatus;
    @BindView(R.id.txt_remark)
    TextView txtRemark;
    @BindView(R.id.txt_code)
    TextView txtCode;
    @BindView(R.id.txt_code_tag)
    TextView txtCodeTag;
    @BindView(R.id.btn_select_goods)
    Button btnSelectGoods;
    @BindView(R.id.txt_preview)
    TextView txtPreview;

    BottomOpraterPopWindow popWindow;
    private int id;
    private int status = 0;
    private BillingApply billingApply;
    private int position;
    private BillingDetailTableDataAdapter adapter;
    private List<BillingDetail> mlistBillingDetail;
    private int positionGoods;
    private int type;
    private int bill_status = 0;//单据在列表中所处的状态 1:待处理 2：未完成 3：历史单据
    private boolean isEditor=false;
    private String strStatus = "";

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_bill_details;
    }

    @Override
    protected void initEventAndData() {
//        role= App.getInstance().getUserInfo().role;
        billingApply = (BillingApply) getIntent().getExtras().getSerializable("BillingApply");
        bill_status =  getIntent().getExtras().getInt("status");
        position = getIntent().getExtras().getInt("position");
        status = billingApply.status;
        id = billingApply.id;
        initData();
        setWidgetListener();
    }

    private void initData() {
        txtCodeTag.setText("生成开票单号：");
        txtCustomer.setText(billingApply.client_name);
        txtRemark.setText(billingApply.remark);
        txtCode.setText(billingApply.order_code);
        boolean hideOperater=true;
        switch (bill_status){
            case 1:
                type = 1;
                hideOperater = false;
                break;
            case 2:
                type = 0;
                hideOperater = false;
                break;
            case 3:
                hideOperater = true;
                break;
        }
        if (hideOperater) {
            setToolBar(toolbar, getString(R.string.module_billing_detail));
        } else {
            setToolBar(toolbar, getString(R.string.module_billing_detail), getString(R.string.operater), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showBottomOpraterPopWindow(type);
                }
            });
        }
        switch (status) {
            case 1:
                strStatus = getString(R.string.bill_status_1);
                isEditor=true;
                break;
            case 2:
                String id = App.getInstance().getUserInfo().id;
                //如果单据是本人提交的，则是未完成状态
                if (id.equals(billingApply.userid)) {
                    if(bill_status==1){
                        strStatus = getString(R.string.bill_status_2);
                    }
                    else{
                        strStatus = getString(R.string.bill_status_undone);
                    }
                } else {
                    if(bill_status==3){
                        strStatus = getString(R.string.bill_status_5);
                    }
                    else {
                        strStatus = getString(R.string.bill_status_2);
                    }
                }
                break;
            case 3:
                if (billingApply.u8_order != null) {
                    if ("待修改".equals(billingApply.u8_order.define1)) {
                        strStatus = getString(R.string.bill_status_7);
                    } else {
                        strStatus = getString(R.string.bill_status_3);
                    }
                } else {
                    strStatus = getString(R.string.bill_status_3);
                }
                break;
            case 4:
                strStatus = getString(R.string.bill_status_4);
                if(bill_status==2){
                    isEditor=true;
                }
                break;
            default:
                strStatus = getString(R.string.bill_status_unknown);
                break;
        }
        if(isEditor){
            btnSelectGoods.setVisibility(View.VISIBLE);
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
        mPresenter.getBillingDetails(billingApply.id);
    }

    private void setWidgetListener() {
        tableView.addDataClickListener(new TableDataClickListener<BillingDetail>() {
            @Override
            public void onDataClicked(final int rowIndex, BillingDetail BillingDetail) {
                if(!isEditor){
                    ToastUtil.showNoticeToast(BillingDetailsActivity.this,"单据已提交，不可操作！");
                    return;
                }
                DialogHelper.showDialog(BillingDetailsActivity.this, "确定删除?", new SimpleDialogFragment.OnSimpleDialogListener() {
                    @Override
                    public void onOk() {
                        positionGoods = rowIndex;
                        int id = mlistBillingDetail.get(rowIndex).id;
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
                        Intent intent = new Intent(BillingDetailsActivity.this, GoodsQueryActivity.class);
                        if(mlistBillingDetail!=null&&!mlistBillingDetail.isEmpty()){
                            ArrayList<GoodsAndWarehouse> goodsArray=new ArrayList<>();
                            for (BillingDetail billingDetail:mlistBillingDetail){
                                GoodsAndWarehouse goodsAndWarehouse=new GoodsAndWarehouse();
                                goodsAndWarehouse.goods=billingDetail;
                                goodsArray.add(goodsAndWarehouse);
                            }
                            intent.putExtra("goodsArray",goodsArray);
                        }
                        intent.putExtra("customer_code",billingApply.client_code);
                        intent.putExtra("module",BillingDetailsActivity.this.getClass().getName());
                        startActivityForResult(intent, SELECT_GOODS_REQUEST_CODE);
                    }
                });
        RxView.clicks(txtPreview)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent intent = new Intent(BillingDetailsActivity.this, StatusDetailActivity.class);
                        intent.putExtra("order_code", billingApply.order_code);
                        if (billingApply.u8_order != null) {
                            if (billingApply.u8_order.states != null && !TextUtils.isEmpty(billingApply.u8_order.states)) {
                                StatusInfo statusInfo=new StatusInfo();
                                statusInfo.record=billingApply.u8_order.huizhi1;
                                statusInfo.create_time=billingApply.u8_order.ddate;
                                intent.putExtra("LastStatus", statusInfo);
                            }
                            else if("待修改".equals(billingApply.u8_order.define1)){
                                StatusInfo statusInfo = new StatusInfo();
                                statusInfo.record =getString(R.string.bill_status_7);
                                statusInfo.create_time = billingApply.u8_order.ddate;
                                intent.putExtra("LastStatus", statusInfo);
                            }
                        }
                        startActivity(intent);
                    }
                });
    }

    public void showBottomOpraterPopWindow(final int type) {
        popWindow = new BottomOpraterPopWindow(this, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 隐藏弹出窗口
                popWindow.dismiss();
                if (type == 0) {
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
                            break;
                        case R.id.btn_cancel:// 取消

                            break;
                        default:
                            break;
                    }
                } else {
                    String userid = App.getInstance().getUserInfo().id;
                    String orderCode = billingApply.order_code;
                    switch (v.getId()) {
                        case R.id.btn_commit:// 待审核
                            mPresenter.approval(userid, orderCode, 3);
                            break;
                        case R.id.btn_temporary:// 审核驳回
                            mPresenter.approval(userid, orderCode, 4);
                            break;
                        case R.id.btn_cancel:// 取消

                            break;
                        default:
                            break;
                    }
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
    private void deleteBill(){
        DialogHelper.showDialog(this, "确定删除?", new SimpleDialogFragment.OnSimpleDialogListener() {
            @Override
            public void onOk() {
                mPresenter.delete(id);
            }
        });
    }

    private void updateBillStatus(int status){
        if (mlistBillingDetail == null || mlistBillingDetail.isEmpty()) {
            ToastUtil.showNoticeToast(this, getString(R.string.warimg_unselect_goods));
            return;
        }
        BillUpdateRequest request=new BillUpdateRequest();
        request.id=id;
        List<Goods> listGoods=new ArrayList<>();
        for (BillingDetail billingDetail:mlistBillingDetail){
            listGoods.add(billingDetail);
        }
        request.goods_array=listGoods;
        request.order_code=billingApply.order_code;
        request.status=status;
        mPresenter.updateStatus(request);
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


    @Override
    public void showContent(BillingApply billingApply) {
        txtCustomer.setText(billingApply.client_name);
        mlistBillingDetail = billingApply.detail;
        adapter = new BillingDetailTableDataAdapter(this, mlistBillingDetail);
        tableView.setDataAdapter(adapter);
    }

    @Override
    public void onSuccess(int opraterType) {
        if (opraterType == 0) {
            ToastUtil.showHookToast(this, "单据更新状态成功！");
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
            mlistBillingDetail.remove(positionGoods);
            adapter.notifyDataSetChanged();
        }
        else if (opraterType == 3) {
            ToastUtil.showHookToast(this, "审批成功！");
            Intent intent = new Intent();
            intent.putExtra("status", status);
            setResult(ReturnRequestActivity.UPDATE_DETAIL_RESULT_CODE, intent);
            finish();
        }
        else if (opraterType == 4) {
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
            for (GoodsAndWarehouse goodsAndWarehouse:goodsArray){
                BillingDetail billingDetail=new BillingDetail();
                billingDetail.warehouse_code=goodsAndWarehouse.goods.warehouse_code;
                billingDetail.goods_warehouse=goodsAndWarehouse.goods.goods_warehouse;
                billingDetail.goods_num=goodsAndWarehouse.goods.goods_num;
                billingDetail.goods_id=goodsAndWarehouse.goods.goods_id;
                billingDetail.goods_code=goodsAndWarehouse.goods.goods_code;
                billingDetail.goods_name=goodsAndWarehouse.goods.goods_name;
                billingDetail.goods_standard=goodsAndWarehouse.goods.goods_standard;
                billingDetail.goods_units_num=goodsAndWarehouse.goods.goods_units_num;
                billingDetail.goods_price=goodsAndWarehouse.goods.goods_price;
                mlistBillingDetail.add(billingDetail);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
