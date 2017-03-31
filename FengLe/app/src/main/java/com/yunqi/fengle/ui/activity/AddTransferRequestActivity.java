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
import com.yunqi.fengle.model.request.TransferAddRequest;
import com.yunqi.fengle.presenter.AddTransferPresenter;
import com.yunqi.fengle.presenter.contract.AddTransferContract;
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
 * 添加调货申请
 */
public class AddTransferRequestActivity extends BaseActivity<AddTransferPresenter> implements AddTransferContract.View {
    private static final int OUT_CUSTOMER_REQUEST_CODE = 1;
    private static final int SELECT_GOODS_REQUEST_CODE = 2;
    private static final int IN_CUSTOMER_REQUEST_CODE = 3;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tableView)
    TableView tableView;
    @BindView(R.id.btn_out_customer)
    Button btnOutCustomer;
    @BindView(R.id.rlayout_out_customer)
    RelativeLayout rlayoutOutCustomer;
    @BindView(R.id.btn_in_customer)
    Button btnInCustomer;
    @BindView(R.id.rlayout_in_customer)
    RelativeLayout rlayoutInCustomer;
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
    private Customer outCustomer;
    private Customer inCustomer;
    private GoodsAndWarehouseTableDataAdapter adapter;
    private float freight=10;//运费


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_add_transfer_request;
    }

    @Override
    protected void initEventAndData() {
        userId= App.getInstance().getUserInfo().id;
        Button btnRight = (Button) toolbar.findViewById(R.id.btn_right);
        btnRight.setText(R.string.operater);
        btnRight.setVisibility(View.VISIBLE);
        setToolBar(toolbar, getString(R.string.module_add_transfer_request));
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
        RxView.clicks(rlayoutOutCustomer)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if(goodsArray.size()>0){
                            DialogHelper.showDialog(AddTransferRequestActivity.this, getString(R.string.warimg_selected_goods_clear), new SimpleDialogFragment.OnSimpleDialogListener() {
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
        RxView.clicks(rlayoutInCustomer)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent intent = new Intent(AddTransferRequestActivity.this, CustomerQueryActivity.class);
                        intent.putExtra("module", 1);
                        startActivityForResult(intent, IN_CUSTOMER_REQUEST_CODE);
                    }
                });
        RxView.clicks(rlayoutSelectGoods)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if(outCustomer==null){
                            ToastUtil.showNoticeToast(AddTransferRequestActivity.this,getString(R.string.warimg_unselect_customer));
                            return;
                        }
                        Intent intent = new Intent(AddTransferRequestActivity.this, GoodsQueryActivity.class);
                        intent.putExtra("userid",outCustomer.id);
                        if(!goodsArray.isEmpty()){
                            intent.putExtra("goodsArray",goodsArray);
                        }
                        startActivityForResult(intent, 2);
                    }
                });
        tableView.addDataClickListener(new TableDataClickListener<GoodsAndWarehouse>() {
            @Override
            public void onDataClicked(final int rowIndex, GoodsAndWarehouse goodsAndWarehouse) {
                DialogHelper.showDialog(AddTransferRequestActivity.this, "确定删除?", new SimpleDialogFragment.OnSimpleDialogListener() {
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
        Intent intent = new Intent(AddTransferRequestActivity.this, CustomerQueryActivity.class);
        intent.putExtra("module", 1);
        startActivityForResult(intent, OUT_CUSTOMER_REQUEST_CODE);
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
                        if (outCustomer == null||inCustomer==null) {
                            ToastUtil.showNoticeToast(AddTransferRequestActivity.this,getString(R.string.warimg_unselect_in_or_out_customer));
                            return;
                        }
                        mStatus =2;
                        addBill();
                    }
                    break;
                    case R.id.btn_temporary:// 暂存
                    {
                        if (outCustomer == null||inCustomer==null) {
                            ToastUtil.showNoticeToast(AddTransferRequestActivity.this,getString(R.string.warimg_unselect_in_or_out_customer));
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

    private void addBill() {
        TransferAddRequest request = new TransferAddRequest();
        request.userid = userId;
        request.client_code_from = outCustomer.custom_code;
        request.client_code_to = inCustomer.custom_code;
        request.client_name_from = outCustomer.name;
        request.client_name_to = inCustomer.name;
        request.remark = editRemark.getText().toString();
        request.status = mStatus;
        List<Goods> listGoods=new ArrayList<>();
        for (GoodsAndWarehouse goodsAndWarehouse:goodsArray){
            goodsAndWarehouse.goods.freight=freight;
            listGoods.add(goodsAndWarehouse.goods);
        }
        request.goods_array = listGoods;
        mPresenter.addTransfer(request);
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
        if (requestCode == OUT_CUSTOMER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            outCustomer = (Customer) data.getSerializableExtra("customer");
            btnOutCustomer.setText(outCustomer.name);
        } else if (requestCode == SELECT_GOODS_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            ArrayList<GoodsAndWarehouse> goodsArray = (ArrayList<GoodsAndWarehouse>) data.getSerializableExtra("listSelectGoods");
            this.goodsArray.addAll(goodsArray);
            adapter = new GoodsAndWarehouseTableDataAdapter(this, this.goodsArray);
            tableView.setDataAdapter(adapter);
        }
        else if (requestCode == IN_CUSTOMER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            inCustomer = (Customer) data.getSerializableExtra("customer");
            btnInCustomer.setText(inCustomer.name);
        }
    }
}
