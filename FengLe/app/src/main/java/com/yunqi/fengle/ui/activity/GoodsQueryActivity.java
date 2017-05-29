package com.yunqi.fengle.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.jakewharton.rxbinding.view.RxView;
import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.Goods;
import com.yunqi.fengle.model.bean.GoodsAndWarehouse;
import com.yunqi.fengle.model.bean.Warehouse;
import com.yunqi.fengle.presenter.GoodsQueryPresenter;
import com.yunqi.fengle.presenter.contract.GoodsQueryContract;
import com.yunqi.fengle.ui.adapter.GoodsTableDataAdapter;
import com.yunqi.fengle.ui.adapter.TableHeader1Adapter;
import com.yunqi.fengle.ui.fragment.dialog.SimpleDialogFragment;
import com.yunqi.fengle.ui.view.ExTableView;
import com.yunqi.fengle.ui.view.InputDialog;
import com.yunqi.fengle.ui.view.InputDialog1;
import com.yunqi.fengle.util.DialogHelper;
import com.yunqi.fengle.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import rx.functions.Action1;

/**
 * 货物查询
 */
public class GoodsQueryActivity extends BaseActivity<GoodsQueryPresenter> implements GoodsQueryContract.View {
    private static final int WAREHOUSE_SELECT_REQUEST_CODE = 1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tableViewEx)
    ExTableView tableViewEx;
    @BindView(R.id.edit_goods_keyword)
    EditText editGoodsKeyword;
    @BindView(R.id.btn_query)
    Button btnQuery;
    @BindView(R.id.btn_select_warehouse)
    Button btnSelectWarehouse;
    @BindView(R.id.rlayout_select_warehouse)
    RelativeLayout rlayoutSelectWarehouse;
    private List<Goods> mListGoods = new ArrayList<>();
    GoodsTableDataAdapter adapter;
    private int page = 1;
    private String keyword = "";
    private Warehouse selectedWarehouse;
    private ArrayList<GoodsAndWarehouse> listSelectGoods = new ArrayList<>();
    private int type = 0;//0：表示根据用户查询货物 1：表示根据仓库查询货物
    private String module = "";
    private String customer_code = "";
    private String warehouse_code = "";
    private String maxGoodsNumTip;
    private String hintGoodsNum;
    private String user_code = "";
    public ArrayList<GoodsAndWarehouse> goodsArray;
    private boolean isTransfer = false;
    private String area_code = "";
    private boolean isPromotion;
    private int iModule = 0;
    private boolean isPlanAdjustment=false;
    private Warehouse fromWarehouse;//从详情或者添加模块携带进来的

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_goods_query;
    }

    @Override
    protected void initEventAndData() {
        goodsArray = (ArrayList<GoodsAndWarehouse>) getIntent().getSerializableExtra("goodsArray");
        if (goodsArray != null && !goodsArray.isEmpty()) {
            fromWarehouse = goodsArray.get(0).warehouse;
            if(fromWarehouse!=null){
                selectedWarehouse = fromWarehouse;
                btnSelectWarehouse.setText(fromWarehouse.name);
                warehouse_code=fromWarehouse.warehouse_code;
            }
        }
        type = getIntent().getIntExtra("type", 0);
        module = getIntent().getStringExtra("module");
        user_code = App.getInstance().getUserInfo().user_code;
        area_code = getIntent().getStringExtra("area_code");
        customer_code = getIntent().getStringExtra("customer_code");
//        customer_code = "205070004";
        if (module.equals(AddDeliveryRequestActivity.class.getName()) || module.equals(DeliveryDetailsActivity.class.getName())) {
            maxGoodsNumTip = getString(R.string.tip_max_goods_num_delivery);
            hintGoodsNum = getString(R.string.hint_edit_num_delivery);
            customer_code = "";
            area_code=App.getInstance().getUserInfo().area_code;
        } else if (module.equals(AddTransferRequestActivity.class.getName()) || module.equals(TransferDetailsActivity.class.getName())) {
            maxGoodsNumTip = getString(R.string.tip_max_goods_num_transfer);
            hintGoodsNum = getString(R.string.hint_edit_num_transfer);
            isTransfer = true;
            user_code = "";
        } else if (module.equals(AddReturnRequestActivity.class.getName()) || module.equals(ReturnDetailsActivity.class.getName())) {
            maxGoodsNumTip = getString(R.string.tip_max_goods_num_return);
            hintGoodsNum = getString(R.string.hint_edit_num_return);
        } else if (module.equals(AddBillingRequestActivity.class.getName()) || module.equals(BillingDetailsActivity.class.getName())) {
            maxGoodsNumTip = getString(R.string.tip_max_goods_num_bill);
            hintGoodsNum = getString(R.string.hint_edit_num_bill);
        } else if (module.equals(AddPlanAdjustmentRequestActivity.class.getName()) || module.equals(PlanAdjustmentDetailsActivity.class.getName())) {
            maxGoodsNumTip = getString(R.string.tip_max_goods_num_plan);
            hintGoodsNum = getString(R.string.hint_edit_num_plan);
            isPlanAdjustment=true;
        }
        isPromotion = getIntent().getBooleanExtra("isPromotion", false);
        if (isPromotion) {
            warehouse_code = "x001";
            setToolBar(toolbar, getString(R.string.module_promotion_goods_query));
        } else {
            setToolBar(toolbar, getString(R.string.module_goods_query));
        }

        setWigetListener();
        String[] heads;
        TableColumnWeightModel columnModel;
        if (isPromotion) {
            iModule = 2;
            heads = getResources().getStringArray(R.array.header_title_goods_query_promotion);
            columnModel = new TableColumnWeightModel(4);
            columnModel.setColumnWeight(0, 1);
            columnModel.setColumnWeight(1, 2);
            columnModel.setColumnWeight(2, 1);
            columnModel.setColumnWeight(3, 1);
        } else {
            //表示发货
            if (type == 1 && !isPromotion) {
                iModule = 1;
                heads = getResources().getStringArray(R.array.header_title_goods_query_delivery);
                rlayoutSelectWarehouse.setVisibility(View.VISIBLE);
                columnModel = new TableColumnWeightModel(6);
                columnModel.setColumnWeight(0, 1);
                columnModel.setColumnWeight(1, 2);
                columnModel.setColumnWeight(2, 1);
                columnModel.setColumnWeight(3, 1);
                columnModel.setColumnWeight(4, 1);
                columnModel.setColumnWeight(5, 1);
            } else {
                iModule = 0;
                heads = getResources().getStringArray(R.array.header_title_goods_query);
                columnModel = new TableColumnWeightModel(5);
                columnModel.setColumnWeight(0, 1);
                columnModel.setColumnWeight(1, 2);
                columnModel.setColumnWeight(2, 1);
                columnModel.setColumnWeight(3, 1);
                columnModel.setColumnWeight(4, 1);
            }
        }
        final TableHeader1Adapter tableHeader1Adapter = new TableHeader1Adapter(this, heads);
        tableViewEx.tableView.setHeaderAdapter(tableHeader1Adapter);
        tableViewEx.tableView.setColumnModel(columnModel);
        loadData();
    }

    private void loadData() {
        mPresenter.queryGoods(area_code, keyword, customer_code, user_code, warehouse_code, page,isPromotion,isPlanAdjustment);
    }

    @Override
    public void onBackPressedSupport() {
        if (!listSelectGoods.isEmpty()) {
            Intent intent = new Intent();
            intent.putExtra("listSelectGoods", listSelectGoods);
            setResult(Activity.RESULT_OK, intent);
        }
        finish();
    }

    private void setWigetListener() {
        tableViewEx.setOnLoadMoreListener(new ExTableView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                ++page;
                loadData();
            }
        });

        tableViewEx.setOnLoadRetryListener(new ExTableView.OnLoadRetryListener() {
            @Override
            public void onLoadRetry() {
                loadData();
            }
        });
        RxView.clicks(btnSelectWarehouse)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (fromWarehouse != null) {
                            ToastUtil.showNoticeToast(GoodsQueryActivity.this, "仓库已选择，不可更改！");
                            return;
                        }
                        if (listSelectGoods != null && !listSelectGoods.isEmpty()) {
                            DialogHelper.showDialog(GoodsQueryActivity.this, getString(R.string.warimg_selected_stock), new SimpleDialogFragment.OnSimpleDialogListener() {
                                @Override
                                public void onOk() {
                                    listSelectGoods.clear();
                                    Intent intent = new Intent(GoodsQueryActivity.this, WarehouseQueryActivity.class);
                                    startActivityForResult(intent, WAREHOUSE_SELECT_REQUEST_CODE);
                                }
                            });
                        } else {
                            Intent intent = new Intent(GoodsQueryActivity.this, WarehouseQueryActivity.class);
                            startActivityForResult(intent, WAREHOUSE_SELECT_REQUEST_CODE);
                        }
                    }
                });
        RxView.clicks(btnQuery)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        page = 1;
                        keyword = editGoodsKeyword.getText().toString();
                        loadData();
                    }
                });
        tableViewEx.tableView.addDataClickListener(new TableDataClickListener<Goods>() {
            @Override
            public void onDataClicked(int rowIndex, final Goods goods) {
                if (type == 1 && selectedWarehouse == null && !isPromotion) {
                    ToastUtil.showNoticeToast(GoodsQueryActivity.this, "请先选择仓库！");
                    return;
                }
                int goods_num = (int) goods.goods_num;
                //检查之前是否已经有选择过的货物，如果有需要重新计算库存
                if (goodsArray != null) {
                    for (GoodsAndWarehouse goodsAndWarehouse : goodsArray) {
                        if (goods.goods_code.equals(goodsAndWarehouse.goods.goods_code)) {
                            goods_num -= goodsAndWarehouse.goods.goods_num;
                        }
                    }
                } else {
                    goodsArray = new ArrayList<GoodsAndWarehouse>();
                }
                if (type == 1 && !isPromotion) {
                    if (goods.goods_plan <= 0) {
                        ToastUtil.showNoticeToast(GoodsQueryActivity.this, "暂无计划，不可操作！");
                        return;
                    }
                }
                if (goods_num > 0) {
                    InputDialog dialog = new InputDialog(GoodsQueryActivity.this, goods_num, maxGoodsNumTip, hintGoodsNum, goods, new InputDialog.OnConfirmListener() {
                        @Override
                        public void onText(int num) {
                            final GoodsAndWarehouse goodsAndWarehouse = new GoodsAndWarehouse();
                            Goods selectGoods = new Goods();
                            selectGoods.goods_num = num;
                            selectGoods.goods_id = goods.goods_id;
                            selectGoods.goods_code = goods.goods_code;
                            selectGoods.goods_name = goods.goods_name;
                            selectGoods.goods_standard = goods.goods_standard;
                            if (goods.goods_units_num <= 1) {
                                goods.goods_units_num = 1;
                            }
                            float goods_units_num = num / goods.goods_units_num;
                            selectGoods.goods_units_num = goods_units_num;
                            selectGoods.goods_price = goods.goods_price;
                            selectGoods.warehouse_code = goods.warehouse_code;
                            goodsAndWarehouse.goods = selectGoods;
                            goodsAndWarehouse.warehouse = selectedWarehouse;
                            if (isTransfer) {
                                InputDialog1 dialog1 = new InputDialog1(GoodsQueryActivity.this, "运费", "请输入运费", new InputDialog1.OnConfirmListener() {
                                    @Override
                                    public void onText(String txt) {
                                        goodsAndWarehouse.goods.freight = txt;
                                        listSelectGoods.add(goodsAndWarehouse);
                                        goodsArray.addAll(listSelectGoods);
                                        showConfirmDialog();
                                    }
                                });
                                dialog1.show();
                            } else {
                                listSelectGoods.add(goodsAndWarehouse);
                                goodsArray.addAll(listSelectGoods);
                                showConfirmDialog();
                            }
                        }
                    });
                    dialog.setModule(iModule);
                    dialog.show();
//                    DialogHelper.showDialog(GoodsQueryActivity.this, "最多可发数量：" + goods.goods_num, new SimpleDialogFragment.OnSimpleDialogListener() {
//                        @Override
//                        public void onOk() {
//
//                        }
//                    });
                } else {
                    DialogHelper.showDialog(GoodsQueryActivity.this, "货物库存量不足，与制剂销售部门联系，电话:0551-6363911?", new SimpleDialogFragment.OnSimpleDialogListener() {
                        @Override
                        public void onOk() {
                            try {
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                intent.setData(Uri.parse("tel:0551-6363911"));
                                startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
//                if (adapter.isExsit(goods)) {
//                    adapter.removeSelect(goods);
//                } else {
//                    adapter.addSelect(goods);
//                }
//                adapter.notifyDataSetChanged();
            }
        });
    }

    private void showConfirmDialog() {
        DialogHelper.showDialog(GoodsQueryActivity.this, "添加成功，是否继续添加货物？", "继续添加", "返回单据", new SimpleDialogFragment.OnSimpleDialogListener() {
            @Override
            public void onOk() {

            }

        }, new SimpleDialogFragment.OnBackDialogListener() {

            @Override
            public void onBack() {
                Intent intent = new Intent();
                intent.putExtra("listSelectGoods", listSelectGoods);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }


    @Override
    public void showLoading() {
        tableViewEx.showLoading();
    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void showError(String msg) {
        tableViewEx.loadingFail();
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
    public void showContent(List<Goods> listGoods) {
        mListGoods = listGoods;
        adapter = new GoodsTableDataAdapter(this, mListGoods);
        adapter.setModule(iModule);
        tableViewEx.tableView.setDataAdapter(adapter);
        if (mListGoods.isEmpty()) {
            tableViewEx.setEmptyData();
        } else {
            tableViewEx.setLoadMoreEnabled(true);
        }
    }

    @Override
    public void showMoreContent(List<Goods> listGoodsMore) {
        if (listGoodsMore.isEmpty()) {
            Log.w(TAG, "No more data!");
            tableViewEx.setLoadMoreEnabled(false);
            return;
        }
        tableViewEx.setLoadMoreEnabled(true);
        mListGoods.addAll(listGoodsMore);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WAREHOUSE_SELECT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            selectedWarehouse = (Warehouse) data.getSerializableExtra("SelectWarehouse");
            warehouse_code = selectedWarehouse.warehouse_code;
            btnSelectWarehouse.setText(selectedWarehouse.name);
            page = 1;
            loadData();
        }
    }
}
