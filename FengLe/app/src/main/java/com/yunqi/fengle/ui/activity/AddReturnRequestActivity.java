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
import com.yunqi.fengle.model.bean.Customer;
import com.yunqi.fengle.model.bean.Goods;
import com.yunqi.fengle.model.bean.GoodsAndWarehouse;
import com.yunqi.fengle.model.request.BillAddRequest;
import com.yunqi.fengle.presenter.AddReturnPresenter;
import com.yunqi.fengle.presenter.contract.AddReturnContract;
import com.yunqi.fengle.ui.adapter.GoodsAndWarehouseTableDataAdapter;
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
import rx.functions.Action1;

/**
 * 添加退货申请
 */
public class AddReturnRequestActivity extends BaseActivity<AddReturnPresenter> implements AddReturnContract.View {
    private static final int SELECT_CUSTOMER_REQUEST_CODE = 1;
    private static final int SELECT_GOODS_REQUEST_CODE = 2;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tableView)
    TableView tableView;
    @BindView(R.id.btn_select_customer)
    Button btnSelectCustomer;
    @BindView(R.id.rlayout_select_customer)
    RelativeLayout rlayoutSelectCustomer;
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
    private Customer selectCustomer;
    private GoodsAndWarehouseTableDataAdapter adapter;


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_add_delivery_request;
    }

    @Override
    protected void initEventAndData() {
        userId= App.getInstance().getUserInfo().id;
        Button btnRight = (Button) toolbar.findViewById(R.id.btn_right);
        btnRight.setText(R.string.operater);
        btnRight.setVisibility(View.VISIBLE);
        setToolBar(toolbar, getString(R.string.module_add_return_request));
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomOpraterPopWindow();
            }
        });
        final TableHeader1Adapter tableHeader1Adapter = new TableHeader1Adapter(this, getResources().getStringArray(R.array.header_title_add_delivey_request));
        tableView.setHeaderAdapter(tableHeader1Adapter);
        setWidgetListener();
    }

    private void setWidgetListener() {
        RxView.clicks(rlayoutSelectCustomer)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if(goodsArray.size()>0){
                            DialogHelper.showDialog(AddReturnRequestActivity.this, getString(R.string.warimg_selected_goods_clear), new SimpleDialogFragment.OnSimpleDialogListener() {
                                @Override
                                public void onOk() {
                                    goodsArray.clear();
                                    adapter.notifyDataSetChanged();
                                    jump2SelectCustomer();
                                }
                            });
                            return;
                        }
                        jump2SelectCustomer();
                    }
                });
        RxView.clicks(rlayoutSelectGoods)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if(selectCustomer==null){
                            ToastUtil.showNoticeToast(AddReturnRequestActivity.this,getString(R.string.warimg_unselect_customer));
                            return;
                        }
                        Intent intent = new Intent(AddReturnRequestActivity.this, GoodsQueryActivity.class);
                        intent.putExtra("userid",selectCustomer.id);
                        if(!goodsArray.isEmpty()){
                            intent.putExtra("goodsArray",goodsArray);
                        }
                        startActivityForResult(intent, 2);
                    }
                });
        tableView.addDataClickListener(new TableDataClickListener<GoodsAndWarehouse>() {
            @Override
            public void onDataClicked(final int rowIndex, GoodsAndWarehouse goodsAndWarehouse) {
                DialogHelper.showDialog(AddReturnRequestActivity.this, "确定删除?", new SimpleDialogFragment.OnSimpleDialogListener() {
                    @Override
                    public void onOk() {
                        goodsArray.remove(rowIndex);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
    private void jump2SelectCustomer(){
        Intent intent = new Intent(AddReturnRequestActivity.this, CustomerQueryActivity.class);
        intent.putExtra("module", 1);
        startActivityForResult(intent, 1);
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
                        if (selectCustomer == null) {
                            ToastUtil.showNoticeToast(AddReturnRequestActivity.this,getString(R.string.warimg_unselect_customer));
                            return;
                        }
                        mStatus = 2;
                        addBill();
                    }
                    break;
                    case R.id.btn_temporary:// 暂存
                    {
                        if (selectCustomer == null) {
                            ToastUtil.showNoticeToast(AddReturnRequestActivity.this,getString(R.string.warimg_unselect_customer));
                            return;
                        }
                        mStatus = 1;
                        addBill();
                    }
                    break;
                    case R.id.btn_cancel:// 放弃
                        break;
                    default:
                        break;
                }
            }
        });
        popWindow.showAtLocation(findViewById(R.id.main_layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private void addBill(){
        BillAddRequest request = new BillAddRequest();
        request.userid = userId;
        request.client_code = selectCustomer.custom_code;
        request.client_name = selectCustomer.name;
        request.remark = editRemark.getText().toString();
        request.status = mStatus;
        List<Goods> listGoods=new ArrayList<>();
        for (GoodsAndWarehouse goodsAndWarehouse:goodsArray){
            listGoods.add(goodsAndWarehouse.goods);
        }
        request.goods_array = listGoods;
        mPresenter.addReturn(request);
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
        if (requestCode == SELECT_CUSTOMER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            selectCustomer = (Customer) data.getSerializableExtra("customer");
            btnSelectCustomer.setText(selectCustomer.name);
        } else if (requestCode == SELECT_GOODS_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            ArrayList<GoodsAndWarehouse> goodsArray = (ArrayList<GoodsAndWarehouse>) data.getSerializableExtra("listSelectGoods");
            this.goodsArray.addAll(goodsArray);
            adapter = new GoodsAndWarehouseTableDataAdapter(this, this.goodsArray);
            tableView.setDataAdapter(adapter);
        }
    }
}
