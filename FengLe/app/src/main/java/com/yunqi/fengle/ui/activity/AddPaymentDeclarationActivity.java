package com.yunqi.fengle.ui.activity;


import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jakewharton.rxbinding.view.RxView;
import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.PictureConfig;
import com.yalantis.ucrop.entity.LocalMedia;
import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.BankCaption;
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
import com.yunqi.fengle.util.LogEx;
import com.yunqi.fengle.util.ToastUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    private static final int CAMERA_REQUEST_CODE = 6;
    private static final int REQUEST_CODE_BANK_CODE= 7;
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
    @BindView(R.id.btn_bank_code)
    Button btnBankCode;
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
    private BankCaption selectBankCaption;//会计科目
    private FukuanType selectFukuanType;//付款类型
    private Customer selectCustomer;
    private String remark;
    private List<LocalMedia> selectMedia = new ArrayList<>();

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
        userId = App.getInstance().getUserInfo().id;
        person_code = App.getInstance().getUserInfo().user_code;
        setToolBar(toolbar, getString(R.string.module_add_payment_declaration), getString(R.string.operater), new View.OnClickListener() {
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
                        open3rdCamera();
//                        showPicturePopupWindow();
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
        RxView.clicks(btnBankCode)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent intent = new Intent(AddPaymentDeclarationActivity.this, BankCodeActivity.class);
                        if (selectBankCaption != null) {
                            intent.putExtra("selectBankCode", selectBankCaption.bank_code);
                        }
                        startActivityForResult(intent, REQUEST_CODE_BANK_CODE);
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
                ContentResolver cr = getContentResolver();
                showPhotoImage(cr, uri);
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
        } else if (requestCode == REQUEST_CODE_PAYMENT_TYPE && resultCode == RESULT_OK) {
            selectPaymentType = (PaymentType) data.getSerializableExtra("SelectPaymentType");
            btnPaymentType.setText(selectPaymentType.name);
        } else if (requestCode == REQUEST_CODE_FUKUAN_TYPE && resultCode == RESULT_OK) {
            selectFukuanType = (FukuanType) data.getSerializableExtra("SelectFukuanType");
            btnFukuaiType.setText(selectFukuanType.name);
        }
        else if (requestCode == REQUEST_CODE_BANK_CODE && resultCode == RESULT_OK) {
            selectBankCaption = (BankCaption) data.getSerializableExtra("SelectBankCaption");
            btnBankCode.setText(selectBankCaption.bank_name);
        }
    }

    private void showPhotoImage(final Bitmap photo) {
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

    private void showPhotoImage(final ContentResolver cr, final Uri uri) {
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


    private void showCameraImage() {
        Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                String cacheFilePath = FileUtil.getSDPath(AddPaymentDeclarationActivity.this)+File.separator+"fengle";
                Bitmap bitmap = BitmapFactory.decodeFile(cacheFilePath + File.separator+"temp.jpg");
                Bitmap newBitmap = ImageTools.zoomBitmap(bitmap, bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);
                bitmap.recycle();
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
                        if (selectCustomer == null) {
                            ToastUtil.showNoticeToast(AddPaymentDeclarationActivity.this, "请选择客户！");
                            return;
                        }
                        if (TextUtils.isEmpty(remittanceDate)) {
                            ToastUtil.showNoticeToast(AddPaymentDeclarationActivity.this, "请选择汇款日期！");
                            return;
                        }
                        if (selectFukuanType == null) {
                            ToastUtil.showNoticeToast(AddPaymentDeclarationActivity.this, "请选择付款类型！");
                            return;
                        }
                        if (selectPaymentType == null) {
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
                        if (selectBankCaption == null) {
                            ToastUtil.showNoticeToast(AddPaymentDeclarationActivity.this, "请输入会计科目！");
                            return;
                        }
//                        if (TextUtils.isEmpty(sPath)) {
//                            ToastUtil.showNoticeToast(AddPaymentDeclarationActivity.this, "请上传单据图片！");
//                            return;
//                        }
                        remark = editRemark.getText().toString();
                        PaymentAddRequest request = new PaymentAddRequest();
                        request.userid = userId;
                        request.remark = remark;
                        request.pay_type = selectFukuanType.name;
                        request.pay_type_code = selectFukuanType.code;
                        request.bank_caption_code=selectBankCaption.bank_code;
                        request.bank_caption_name=selectBankCaption.bank_name;
                        request.person_code = person_code;
                        request.client_code = selectCustomer.custom_code;
                        request.client_name = selectCustomer.name;
                        request.huikuan_type = selectPaymentType.name;
                        request.huikuan_type_code = selectPaymentType.code;
                        request.huikuan_time = remittanceDate;
                        request.huikuan_name = remitterName;
                        request.huikuan_amount = remittanceAmount;
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
                        open3rdCamera();
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {  //针对Android7.0，需要通过FileProvider封装过的路径，提供给外部调用
//                            checkPremission();
//                        } else { //7.0以下，如果直接拿到相机返回的intent值，拿到的则是拍照的原图大小，很容易发生OOM，所以我们同样将返回的地址，保存到指定路径，返回到Activity时，去指定路径获取，压缩图片
//                            openCamera();
//                        }
//                        getImageFromCamera();
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


    private void open3rdCamera(){
        int selector = R.drawable.select_cb;
        FunctionConfig config = new FunctionConfig();
        config.setEnablePixelCompress(true);
        config.setEnableQualityCompress(true);
        config.setMaxSelectNum(1);
        config.setCheckNumMode(true);
        config.setCompressQuality(100);
        config.setImageSpanCount(4);
        config.setEnablePreview(true);
//        config.setSelectMedia(selectMedia);
        config.setCheckedBoxDrawable(selector);
        // 先初始化参数配置，在启动相册
        PictureConfig.init(config);
        PictureConfig.getPictureConfig().openPhoto(this, resultCallback);
    }

    /**
     * 图片回调方法0
     */
    private PictureConfig.OnSelectResultCallback resultCallback = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            selectMedia = resultList;
            if (selectMedia != null&&selectMedia.size()>0) {
                sPath=selectMedia.get(0).getPath();
                Glide.with(mContext)
                        .load(selectMedia.get(0).getPath())
                        .asBitmap().centerCrop()
                        .placeholder(R.color.color_f6)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imgShow);
            }
        }
    };

    protected void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    private void checkPremission() {
        final String permission = Manifest.permission.CAMERA;  //相机权限
        final String permission1 = Manifest.permission.WRITE_EXTERNAL_STORAGE; //写入数据权限
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, permission1) != PackageManager.PERMISSION_GRANTED) {  //先判断是否被赋予权限，没有则申请权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {  //给出权限申请说明
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_REQUEST_CODE);
            } else { //直接申请权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_REQUEST_CODE); //申请权限，可同时申请多个权限，并根据用户是否赋予权限进行判断
            }
        } else {  //赋予过权限，则直接调用相机拍照
            openCamera();
        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {  //申请权限的返回值
            case CAMERA_REQUEST_CODE:
                int length = grantResults.length;
                final boolean isGranted = length >= 1 && PackageManager.PERMISSION_GRANTED == grantResults[length - 1];
                if (isGranted) {  //如果用户赋予权限，则调用相机
                    openCamera();
                } else { //未赋予权限，则做出对应提示

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private void openCamera() {  //调用相机拍照
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Uri imageUri;
            Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {  //针对Android7.0，需要通过FileProvider封装过的路径，提供给外部调用
//                imageUri = FileProvider.getUriForFile(this, getPackageName(), mTmpFile);//通过FileProvider创建一个content类型的Uri，进行封装
                sPath=FileUtil.getSDPath(this)+File.separator+"fengle";
                File path = new File(sPath);
                if (!path.exists()) {
                    path.mkdirs();
                }
                File file = new File(sPath, "temp.jpg");
                if (file.exists()) {
                    file.delete();
                }
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                imageUri= getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
            } else { //7.0以下，如果直接拿到相机返回的intent值，拿到的则是拍照的原图大小，很容易发生OOM，所以我们同样将返回的地址，保存到指定路径，返回到Activity时，去指定路径获取，压缩图片
                imageUri = Uri.fromFile(new File(FileUtil.getSDPath(this)+File.separator+"fengle", "temp.jpg"));
            }
            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(getImageByCamera, REQUEST_CODE_CAPTURE_CAMEIA);
        } else {
            ToastUtil.showErrorToast(this, "请确认已经插入SD卡");
        }

    }


}
