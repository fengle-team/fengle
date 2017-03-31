package com.yunqi.fengle.ui.activity;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.Customer;
import com.yunqi.fengle.model.bean.FukuanType;
import com.yunqi.fengle.model.bean.PaymentType;
import com.yunqi.fengle.model.request.PaymentAddRequest;
import com.yunqi.fengle.presenter.AddPaymentDeclarationPresenter;
import com.yunqi.fengle.presenter.contract.AddPaymentDeclarationContract;
import com.yunqi.fengle.ui.view.BottomOpraterPopWindow;
import com.yunqi.fengle.ui.view.TimeSelectDialog;
import com.yunqi.fengle.util.FileUtil;
import com.yunqi.fengle.util.ImageTools;
import com.yunqi.fengle.util.ToastUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 添加回款申报
 */
public class AddPaymentDeclarationActivity extends BaseActivity<AddPaymentDeclarationPresenter> implements AddPaymentDeclarationContract.View, View.OnFocusChangeListener {

    private static final int REQUEST_CODE_PICK_IMAGE = 1;
    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 2;
    private static final int REQUEST_CODE_CUSTOMER_QUERY = 3;
    private static final int REQUEST_CODE_PAYMENT_TYPE = 4;
    private static final int REQUEST_CODE_FUKUAN_TYPE = 5;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_select_customer)
    Button btnSelectCustomer;
    @BindView(R.id.llayout_remittance_date)
    LinearLayout llayoutRemittanceDate;
    @BindView(R.id.txt_remittance_date)
    TextView txtRemittanceDate;
    @BindView(R.id.edit_remitter)
    EditText editRemitter;
    @BindView(R.id.edit_remark)
    EditText editRemark;
    @BindView(R.id.edit_amount)
    EditText editAmount;
    @BindView(R.id.btn_payment_type)
    Button btnPaymentType;
    @BindView(R.id.btn_fukui_type)
    Button btnFukuaiType;
    @BindView(R.id.btn_upload)
    Button btnUpload;
    @BindView(R.id.img_show)
    ImageView imgShow;
    private String userId = "";
    private String person_code = "";
    private String remitterName = "";//汇款人姓名
    private double remittanceAmount = 0;//汇款金额
    private String imgUrl = "";
    private BottomOpraterPopWindow popWindow;
    private SweetAlertDialog loadingDialog;
    String sPath = "";
    private String remittanceDate = "";
    private static final int SCALE = 5;//照片缩小比例
    private PaymentType selectPaymentType;//汇款类型
    private FukuanType selectFukuanType;//付款类型
    private Customer selectCustomer;
    private String remark;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_add_payment_declaration;
    }

    @Override
    protected void initEventAndData() {
        userId= App.getInstance().getUserInfo().id;
        person_code=App.getInstance().getUserInfo().user_code;
        Button btnRight = (Button) toolbar.findViewById(R.id.btn_right);
        btnRight.setText(R.string.operater);
        btnRight.setVisibility(View.VISIBLE);
        setToolBar(toolbar, getString(R.string.module_add_payment_declaration));
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomOpraterPopWindow();
            }
        });
        setWidgetListener();
    }

    private void setWidgetListener() {
        RxView.clicks(btnSelectCustomer)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent intent = new Intent(AddPaymentDeclarationActivity.this, CustomerQueryActivity.class);
                        intent.putExtra("module", 1);
                        startActivityForResult(intent, REQUEST_CODE_CUSTOMER_QUERY);
                    }
                });
        RxView.clicks(llayoutRemittanceDate)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        TimeSelectDialog dialog = new TimeSelectDialog(AddPaymentDeclarationActivity.this, new TimeSelectDialog.TimeSelectListener() {
                            @Override
                            public void onTimeSelected(long ltime, String strTime) {
                                remittanceDate = strTime;
                                txtRemittanceDate.setText(strTime);
                            }
                        });
                        dialog.show();
                    }
                });
        RxView.clicks(btnUpload)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        showPicturePopupWindow();
                    }
                });
        RxView.clicks(btnPaymentType)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent intent = new Intent(AddPaymentDeclarationActivity.this, PaymentTypeActivity.class);
                        if (selectPaymentType != null) {
                            intent.putExtra("selectPaymentTypeId", selectPaymentType.id);
                        }
                        startActivityForResult(intent, REQUEST_CODE_PAYMENT_TYPE);
                    }
                });
        RxView.clicks(btnFukuaiType)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent intent = new Intent(AddPaymentDeclarationActivity.this, FukuanTypeActivity.class);
                        if (selectFukuanType != null) {
                            intent.putExtra("selectFukuanTypeId", selectFukuanType.id);
                        }
                        startActivityForResult(intent, REQUEST_CODE_FUKUAN_TYPE);
                    }
                });
    }


    @Override
    public void showLoading() {
        loadingDialog = new SweetAlertDialog(AddPaymentDeclarationActivity.this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("正在添加...");
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
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            if (uri == null) {
                //use bundle to get data
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Bitmap photo = (Bitmap) bundle.get("data"); //get bitmap
                    showPhotoImage(photo);
//                    Bitmap smallBitmap = ImageTools.zoomBitmap(photo, photo.getWidth() / SCALE, photo.getHeight() / SCALE);
//                    //释放原始图片占用的内存，防止out of memory异常发生
//                    photo.recycle();
//                    imgShow.setImageBitmap(smallBitmap);
//                    String cacheFilePath = FileUtil.getSDPath(AddPaymentDeclarationActivity.this) + File.separator + "fengle" + File.separator;
//                    sPath = FileUtil.createNewFile(cacheFilePath + "temp" + File.separator);
//                    saveImage(photo, sPath);
                    //spath :生成图片取个名字和路径包含类型
                } else {
                    ToastUtil.showErrorToast(this, "获取图片失败！");
                }
            } else {
                ContentResolver cr=getContentResolver();
                showPhotoImage(cr,uri);
            }
        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA && resultCode == RESULT_OK) {
            //将保存在本地的图片取出并缩小后显示在界面上
            showCameraImage();

//            Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/image.jpg");
//            Bitmap newBitmap = ImageTools.zoomBitmap(bitmap, bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);
//            //由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
//            bitmap.recycle();
//
//            //将处理过的图片显示在界面上，并保存到本地
//            imgShow.setImageBitmap(newBitmap);
//            sPath = ImageTools.savePhotoToSDCard(newBitmap, Environment.getExternalStorageDirectory().getAbsolutePath(), String.valueOf(System.currentTimeMillis()));
        } else if (requestCode == REQUEST_CODE_CUSTOMER_QUERY && resultCode == RESULT_OK) {
            selectCustomer = (Customer) data.getSerializableExtra("customer");
            btnSelectCustomer.setText(selectCustomer.name);
        }
        else if (requestCode == REQUEST_CODE_PAYMENT_TYPE && resultCode == RESULT_OK) {
            selectPaymentType = (PaymentType) data.getSerializableExtra("SelectPaymentType");
            btnPaymentType.setText(selectPaymentType.name);
        }
        else if (requestCode == REQUEST_CODE_FUKUAN_TYPE && resultCode == RESULT_OK) {
            selectFukuanType = (FukuanType) data.getSerializableExtra("SelectFukuanType");
            btnFukuaiType.setText(selectFukuanType.name);
        }
    }

    private void showPhotoImage(final Bitmap photo){
        Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap smallBitmap = ImageTools.zoomBitmap(photo, photo.getWidth() / SCALE, photo.getHeight() / SCALE);
                //释放原始图片占用的内存，防止out of memory异常发生
                photo.recycle();
                String cacheFilePath = FileUtil.getSDPath(AddPaymentDeclarationActivity.this) + File.separator + "fengle" + File.separator;
                sPath = FileUtil.createNewFile(cacheFilePath + "temp" + File.separator);
                saveImage(photo, sPath);
                subscriber.onNext(smallBitmap);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onNext(Bitmap bitmap) {
                        imgShow.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showErrorToast(AddPaymentDeclarationActivity.this, "获取图片失败！");
                    }
                });
    }
    private void showPhotoImage(final ContentResolver cr,final Uri uri){
        Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                //为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
                Bitmap photo = null;
                try {
                    photo = MediaStore.Images.Media.getBitmap(cr, uri);
                    Bitmap smallBitmap = ImageTools.zoomBitmap(photo, photo.getWidth() / SCALE, photo.getHeight() / SCALE);
                    //释放原始图片占用的内存，防止out of memory异常发生
                    photo.recycle();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(uri,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    sPath = cursor.getString(columnIndex);
                    subscriber.onNext(smallBitmap);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onNext(Bitmap bitmap) {
                        imgShow.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showErrorToast(AddPaymentDeclarationActivity.this, "获取图片失败！");
                    }
                });
    }



    private void showCameraImage(){
        Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/image.jpg");
                Bitmap newBitmap = ImageTools.zoomBitmap(bitmap, bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);
                bitmap.recycle();
                String cacheFilePath = FileUtil.getSDPath(AddPaymentDeclarationActivity.this) + File.separator + "fengle" + File.separator;
                sPath = ImageTools.savePhotoToSDCard(newBitmap, cacheFilePath, String.valueOf(System.currentTimeMillis()));
                subscriber.onNext(newBitmap);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<Bitmap>() {
            @Override
            public void onNext(Bitmap bitmap) {
                imgShow.setImageBitmap(bitmap);
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }
        });
    }

    public void saveImage(Bitmap photo, String spath) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(spath, false));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess() {
        ToastUtil.showHookToast(this, getString(R.string.wariming_add_success));
        setResult(Activity.RESULT_OK);
        finish();
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
                    case R.id.btn_commit: {
                        if(selectCustomer==null){
                            ToastUtil.showNoticeToast(AddPaymentDeclarationActivity.this, "请选择客户！");
                            return;
                        }
                        if(TextUtils.isEmpty(remittanceDate)){
                            ToastUtil.showNoticeToast(AddPaymentDeclarationActivity.this, "请选择汇款日期！");
                            return;
                        }
                        if(selectFukuanType==null){
                            ToastUtil.showNoticeToast(AddPaymentDeclarationActivity.this, "请选择付款类型！");
                            return;
                        }
                        if(selectPaymentType==null){
                            ToastUtil.showNoticeToast(AddPaymentDeclarationActivity.this, "请选择回款类型！");
                            return;
                        }
                        remitterName = editRemitter.getText().toString();
                        if (TextUtils.isEmpty(remitterName)) {
                            ToastUtil.showNoticeToast(AddPaymentDeclarationActivity.this, "请输入汇款人名称！");
                            return;
                        }
                        String strAmount = editAmount.getText().toString();
                        try {
                            remittanceAmount = Double.parseDouble(strAmount);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            ToastUtil.showNoticeToast(AddPaymentDeclarationActivity.this, "请输入正确的金额!");
                            return;
                        }
                        if (remittanceAmount <= 0) {
                            ToastUtil.showNoticeToast(AddPaymentDeclarationActivity.this, "请输入汇款金额！");
                            return;
                        }
                        if (TextUtils.isEmpty(sPath)) {
                            ToastUtil.showNoticeToast(AddPaymentDeclarationActivity.this, "请上传单据图片！");
                            return;
                        }
                        remark=editRemark.getText().toString();
                        PaymentAddRequest request = new PaymentAddRequest();
                        request.userid = userId;
                        request.remark = remark;
                        request.pay_type=selectFukuanType.name;
                        request.person_code=person_code;
                        request.client_code = selectCustomer.custom_code;
                        request.client_name=selectCustomer.name;
                        request.huikuan_type = selectPaymentType.name;
                        request.huikuan_time = remittanceDate;
                        request.huikuan_name = remitterName;
                        request.huikuan_amount = remittanceAmount;
                        request.images = imgUrl;
                        mPresenter.addPayment(request, sPath);
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

    /**
     * 弹出底部操作PopupWindow
     */
    public void showPicturePopupWindow() {
        popWindow = new BottomOpraterPopWindow(this, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 隐藏弹出窗口
                popWindow.dismiss();
                switch (v.getId()) {
                    case R.id.btn_commit:// 拍照
                        getImageFromCamera();
                        break;
                    case R.id.btn_temporary:// 相册选择图片
                        getImageFromAlbum();
                        break;
                    case R.id.btn_cancel:// 取消
                        break;
                    default:
                        break;
                }
            }
        });
        popWindow.setPopWindowTexts(getResources().getStringArray(R.array.oprater_take_photo));
        popWindow.showAtLocation(findViewById(R.id.main_layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    protected void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    protected void getImageFromCamera() {
        //指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
            Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(getImageByCamera, REQUEST_CODE_CAPTURE_CAMEIA);
        } else {
            ToastUtil.showErrorToast(this, "请确认已经插入SD卡");
        }
    }


}
