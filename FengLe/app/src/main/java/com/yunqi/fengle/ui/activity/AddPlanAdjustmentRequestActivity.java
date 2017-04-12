package com.yunqi.fengle.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.jakewharton.rxbinding.view.RxView;
import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.Area;
import com.yunqi.fengle.model.bean.Goods;
import com.yunqi.fengle.model.bean.GoodsAndWarehouse;
import com.yunqi.fengle.model.request.PlanAdjustmentAddRequest;
import com.yunqi.fengle.presenter.AddPlanAdjustmentPresenter;
import com.yunqi.fengle.presenter.contract.AddPlanAdjustmentContract;
import com.yunqi.fengle.ui.adapter.GoodsAndWarehouseTableDataAdapter;
import com.yunqi.fengle.ui.adapter.PlanGoodsAndWarehouseTableDataAdapter;
import com.yunqi.fengle.ui.adapter.TableHeader1Adapter;
import com.yunqi.fengle.ui.fragment.dialog.SimpleDialogFragment;
import com.yunqi.fengle.ui.view.BottomOpraterPopWindow;
import com.yunqi.fengle.util.DialogHelper;
import com.yunqi.fengle.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import rx.functions.Action1;

/**
 * 添加计划调剂申请
 */
public class AddPlanAdjustmentRequestActivity extends BaseActivity<AddPlanAdjustmentPresenter> implements AddPlanAdjustmentContract.View {
    private static final int OUT_AREA_REQUEST_CODE = 1;
    private static final int SELECT_GOODS_REQUEST_CODE = 2;
    private static final int IN_AREA_REQUEST_CODE = 3;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tableView)
    TableView tableView;
    @BindView(R.id.btn_out_area)
    Button btnOutArea;
    @BindView(R.id.rlayout_out_area)
    RelativeLayout rlayoutOutArea;
    @BindView(R.id.btn_in_area)
    Button btnInArea;
    @BindView(R.id.rlayout_in_area)
    RelativeLayout rlayoutInArea;
    @BindView(R.id.btn_select_goods)
    Button btnSelectGoods;
    @BindView(R.id.rlayout_select_goods)
    RelativeLayout rlayoutSelectGoods;
    @BindView(R.id.edit_remark)
    EditText editRemark;
    BottomOpraterPopWindow popWindow;


    private String userId = "";
    private int mStatus = 0;
    public String clientName = "";
    public ArrayList<GoodsAndWarehouse> goodsArray = new ArrayList<>();
    private Area outArea;
    private Area inArea;
    private PlanGoodsAndWarehouseTableDataAdapter adapter;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_add_plan_request;
    }

    @Override
    protected void initEventAndData() {
        userId= App.getInstance().getUserInfo().id;
        outArea=new Area();
        outArea.area_code=App.getInstance().getUserInfo().area_code;
        outArea.name=App.getInstance().getUserInfo().name;
        btnOutArea.setText(outArea.name);
        setToolBar(toolbar, getString(R.string.module_add_plan_adjustment), getString(R.string.operater), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomOpraterPopWindow();
            }
        });
        final TableHeader1Adapter tableHeader1Adapter = new TableHeader1Adapter(this, getResources().getStringArray(R.array.header_title_add_plan_request));
        tableView.setHeaderAdapter(tableHeader1Adapter);
        TableColumnWeightModel columnModel = new TableColumnWeightModel(5);
        columnModel.setColumnWeight(0, 2);
        columnModel.setColumnWeight(1, 1);
        columnModel.setColumnWeight(2, 1);
        columnModel.setColumnWeight(3, 1);
        columnModel.setColumnWeight(4, 1);
        tableView.setColumnModel(columnModel);
        setWidgetListener();
    }

    private void setWidgetListener() {
//        RxView.clicks(rlayoutOutArea)
//                .throttleFirst(1, TimeUnit.SECONDS)
//                .subscribe(new Action1<Void>() {
//                    @Override
//                    public void call(Void aVoid) {
//                        if(goodsArray.size()>0){
//                            DialogHelper.showDialog(AddPlanAdjustmentRequestActivity.this, getString(R.string.warimg_area_selected_goods_clear), new SimpleDialogFragment.OnSimpleDialogListener() {
//                                @Override
//                                public void onOk() {
//                                    goodsArray.clear();
//                                    adapter.notifyDataSetChanged();
//                                    jump2SelectArea(OUT_AREA_REQUEST_CODE);
//                                }
//                            });
//                            return;
//                        }
//                        jump2SelectArea(OUT_AREA_REQUEST_CODE);
//                    }
//                });
        RxView.clicks(rlayoutInArea)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        jump2SelectArea(IN_AREA_REQUEST_CODE);
                    }
                });
        RxView.clicks(rlayoutSelectGoods)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
//                        if(outArea==null){
//                            ToastUtil.showNoticeToast(AddPlanAdjustmentRequestActivity.this,getString(R.string.warimg_unselect_area));
//                            return;
//                        }
                        Intent intent = new Intent(AddPlanAdjustmentRequestActivity.this, GoodsQueryActivity.class);
                        intent.putExtra("userid",userId);
                        intent.putExtra("module",AddPlanAdjustmentRequestActivity.this.getClass().getName());
                        if(!goodsArray.isEmpty()){
                            intent.putExtra("goodsArray",goodsArray);
                        }
                        startActivityForResult(intent, SELECT_GOODS_REQUEST_CODE);
                    }
                });
        tableView.addDataClickListener(new TableDataClickListener<GoodsAndWarehouse>() {
            @Override
            public void onDataClicked(final int rowIndex, GoodsAndWarehouse goodsAndWarehouse) {
                DialogHelper.showDialog(AddPlanAdjustmentRequestActivity.this, "确定删除?", new SimpleDialogFragment.OnSimpleDialogListener() {
                    @Override
                    public void onOk() {
                        goodsArray.remove(rowIndex);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void jump2SelectArea(int requestCode){
        Intent intent = new Intent(this, AreaQueryActivity.class);
        startActivityForResult(intent, requestCode);
    }


    /**
     * 弹出底部操作PopupWindow
     */
    public void showBottomOpraterPopWindow() {
        popWindow = new BottomOpraterPopWindow(this, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 隐藏弹出窗口
                popWindow.dismiss();
                switch (v.getId()) {
                    case R.id.btn_commit:// 提交
                    {
                        addBill(2);
                    }
                    break;
                    case R.id.btn_cancel:// 放弃
                        break;
                    default:
                        break;
                }
            }
        });
        popWindow.setOpraterType(1);
        popWindow.showAtLocation(findViewById(R.id.main_layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private void addBill(int status) {
        if (outArea == null||inArea==null) {
            ToastUtil.showNoticeToast(AddPlanAdjustmentRequestActivity.this,getString(R.string.warimg_unselect_in_or_out_area));
            return;
        }
        if (goodsArray.isEmpty()) {
            ToastUtil.showNoticeToast(AddPlanAdjustmentRequestActivity.this,getString(R.string.warimg_unselect_goods));
            return;
        }
        mStatus =status;
        PlanAdjustmentAddRequest request = new PlanAdjustmentAddRequest();
        request.userid = userId;
        request.from_area_code = outArea.area_code;
        request.to_area_code = inArea.area_code;
        request.from_area_name = outArea.name;
        request.to_area_name = inArea.name;
        request.remark = editRemark.getText().toString();
        request.status = mStatus;
        List<Goods> listGoods=new ArrayList<>();
        for (GoodsAndWarehouse goodsAndWarehouse:goodsArray){
            listGoods.add(goodsAndWarehouse.goods);
        }
        request.goods_array = listGoods;
        mPresenter.addPlanAdjustment(request);
    }

    @Override
    public void showLoading() {
        super.showLoading("添加中...");
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
    public void onSuccess() {
        ToastUtil.showHookToast(this, getString(R.string.wariming_add_success));
        Intent intent = new Intent();
        intent.putExtra("status", mStatus);
        setResult(Activity.RESULT_OK, intent);
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OUT_AREA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            outArea = (Area) data.getSerializableExtra("SelectArea");
            btnOutArea.setText(outArea.name);
        } else if (requestCode == SELECT_GOODS_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            ArrayList<GoodsAndWarehouse> goodsArray = (ArrayList<GoodsAndWarehouse>) data.getSerializableExtra("listSelectGoods");
            this.goodsArray.addAll(goodsArray);
            adapter = new PlanGoodsAndWarehouseTableDataAdapter(this, this.goodsArray);
            tableView.setDataAdapter(adapter);
        }
        else if (requestCode == IN_AREA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            inArea = (Area) data.getSerializableExtra("SelectArea");
            btnInArea.setText(inArea.name);
        }
    }
}
