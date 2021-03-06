package com.yunqi.fengle.model.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.text.TextUtils;

import com.yunqi.fengle.BuildConfig;
import com.yunqi.fengle.constants.Constants;
import com.yunqi.fengle.model.bean.ADInfo;
import com.yunqi.fengle.model.bean.Area;
import com.yunqi.fengle.model.bean.BankCaption;
import com.yunqi.fengle.model.bean.BillingApply;
import com.yunqi.fengle.model.bean.Customer;
import com.yunqi.fengle.model.bean.CustomerAnalysis;
import com.yunqi.fengle.model.bean.CustomerContactDetail;
import com.yunqi.fengle.model.bean.FukuanType;
import com.yunqi.fengle.model.bean.Goods;
import com.yunqi.fengle.model.bean.GoodsSaleDetail;
import com.yunqi.fengle.model.bean.InvoiceApply;
import com.yunqi.fengle.model.bean.Module;
import com.yunqi.fengle.model.bean.Payment;
import com.yunqi.fengle.model.bean.PaymentType;
import com.yunqi.fengle.model.bean.PlanAdjustmentApply;
import com.yunqi.fengle.model.bean.ReturnApply;
import com.yunqi.fengle.model.bean.SaleInfo;
import com.yunqi.fengle.model.bean.SplashBean;
import com.yunqi.fengle.model.bean.StatusInfo;
import com.yunqi.fengle.model.bean.TransferApply;
import com.yunqi.fengle.model.bean.UserBean;
import com.yunqi.fengle.model.bean.Warehouse;
import com.yunqi.fengle.model.request.ActivityAddPlanRequest;
import com.yunqi.fengle.model.request.ActivityExpenseRequest;
import com.yunqi.fengle.model.request.ActivitySummaryRequest;
import com.yunqi.fengle.model.request.AddLinkmanRequest;
import com.yunqi.fengle.model.request.BillUpdateRequest;
import com.yunqi.fengle.model.request.DailySendRequest;
import com.yunqi.fengle.model.request.BillAddRequest;
import com.yunqi.fengle.model.request.PaymentAddRequest;
import com.yunqi.fengle.model.request.AddMaintainRequest;
import com.yunqi.fengle.model.request.PlanAdjustmentAddRequest;
import com.yunqi.fengle.model.request.SignAddRequest;
import com.yunqi.fengle.model.request.TransferAddRequest;
import com.yunqi.fengle.model.request.TypeRequest;
import com.yunqi.fengle.model.request.VisitingAddRequest;
import com.yunqi.fengle.model.request.VisitingUpdateRequest;
import com.yunqi.fengle.model.response.ActivityAddResponse;
import com.yunqi.fengle.model.response.ActivitySummaryResponse;
import com.yunqi.fengle.model.response.CustomerWholeResponse;
import com.yunqi.fengle.model.response.CustomersResponse;
import com.yunqi.fengle.model.response.CustomersSituationResponse;
import com.yunqi.fengle.model.response.DailyResponse;
import com.yunqi.fengle.model.response.MessageResponse;
import com.yunqi.fengle.model.response.NoticeResponse;
import com.yunqi.fengle.model.response.RegionalRankingResponse;
import com.yunqi.fengle.model.response.SaleRankingResponse;
import com.yunqi.fengle.model.response.VisitingPlanResponse;
import com.yunqi.fengle.util.NetworkUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public class RetrofitHelper {
    private OkHttpClient okHttpClient = null;
    private ApiService apiService = null;
    public static final int PAGE_SIZE = 20;
    private Context mContext;

    public RetrofitHelper(Context context) {
        mContext = context;
        init();
    }

    private void init() {
        initOkHttp();
        apiService = getApiService();
    }

    private void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
//            builder.addInterceptor(loggingInterceptor);
        }
        File cacheFile = new File(Constants.PATH_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .build();
                ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
                boolean isNetworkConnect = NetworkUtil.isNetworkAvailable(connectivityManager);
                if (!isNetworkConnect) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (isNetworkConnect) {
                    int maxAge = 0;
                    //有网络时，不缓存，最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    //无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public,only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        okHttpClient = builder.build();
    }

    public Observable<BmobListResponse<List<SplashBean>>> getSplashInfo() {
        return apiService.getSplashInfo();
    }

    private ApiService getApiService() {
        Retrofit apiServiceRetrofit =
                new Retrofit.Builder()
                        .baseUrl(ApiService.HOST)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
        return apiServiceRetrofit.create(ApiService.class);
    }

    public Observable<CommonHttpRsp<UserBean>> doLogin(String username, String password) {
        return apiService.doLogin(username, password);
    }

    public Observable<BaseHttpRsp> doSign(SignAddRequest request) {
        return apiService.signAdd(request);
    }

    /**
     * 查询活动
     *
     * @param userid
     * @return
     */
    public Observable<CommonHttpRsp<List<ActivityAddResponse>>> queryActivities(String status, String customerCode, String userid, String startTime, String endTime) {
        return apiService.queryActivities(status, customerCode, userid, startTime, endTime);
    }


    /**
     * 查询活动总结
     *
     -     * @param userid
     * @return
     */
    public Observable<CommonHttpRsp<List<ActivitySummaryResponse>>> queryActivitieSummary(String userId,
                                                                                          String start_time,
                                                                                          String end_time,
                                                                                          String keyword,
                                                                                          String status,
                                                                                          String page,
                                                                                          String size) {
        return apiService.queryActivitieSummary(userId,start_time,end_time,keyword, status, page, size);
    }


    /**
     * 更新拜访计划确认状态
     *
     * @return
     */
    public Observable<BaseHttpRsp> updateVisiteStatus(VisitingUpdateRequest request) {
        return apiService.updateVisiteStatus(request);
    }

    /**
     * 更新活动计划状态
     *
     * @return
     */
    public Observable<BaseHttpRsp> updatePlanUpdateStatus(String userid, String order_code, String status) {
        return apiService.updatePlanStatus(userid, order_code, status);
    }

    /**
     * 删除活动
     *
     * @return
     */
    public Observable<BaseHttpRsp> deleteActivity(String id) {
        return apiService.deleteActivity(id);
    }

    /**
     * 添加维护
     *
     * @param request
     * @return
     */
    public Observable<CommonHttpRsp<Object>> addMaintain(AddMaintainRequest request) {
        return apiService.addMaintain(request);
    }

    /**
     * 添加维护
     *
     * @param request
     * @return
     */
    public Observable<CommonHttpRsp<Object>> addReimburse(ActivityExpenseRequest request) {
        return apiService.addReimburse(request);
    }


    /**
     * 查询个人模块接口
     *
     * @param userid
     * @return
     */
    public Observable<CommonHttpRsp<List<Module>>> authModule(String userid) {
        return apiService.authModule(userid);
    }

    /**
     * 查询库存
     *
     * @param warehouse_code
     * @param area_code
     * @param keywords
     * @param page
     * @return
     */
    public Observable<CommonHttpRsp<List<Goods>>> queryStock(String warehouse_code, String area_code, String keywords, final int page) {
        return apiService.queryStock(warehouse_code, area_code, keywords, page, PAGE_SIZE);
    }

    /**
     * 客户查询
     *
     * @param keyword   客户名或编号
     * @param user_code 用户id
     * @param page      页码
     * @return
     */
    public Observable<CommonHttpRsp<List<Customer>>> queryCustomer(String keyword, String user_code, int page) {
        return apiService.queryCustomer(keyword, user_code, page, PAGE_SIZE, 3);
    }

    /**
     * 我的客户
     *
     * @return
     */
    public Observable<CommonHttpRsp<List<CustomersResponse>>> getCustomers(String user_code, int page, int pageSize) {
        return apiService.getCustomers(user_code, page, pageSize);
    }

    /**
     * 查询客情维护
     *
     * @return
     */
    public Observable<CommonHttpRsp<List<CustomersSituationResponse>>> getMainTain(String userid) {
        return apiService.getMainTain(userid);
    }

    /**
     * 查询货物销售明细接口
     *
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @param ccuscode    客户编码
     * @param cpersoncode 业务员编码
     * @param ccdcode     客户地区编码
     * @return
     */
    public Observable<CommonHttpRsp<List<SaleInfo>>> querySales(String startTime, String endTime, String ccuscode, String cpersoncode, String ccdcode) {
        return apiService.querySales(startTime, endTime, ccuscode, cpersoncode, ccdcode);
    }

    /**
     * 货物查询
     *
     * @param keyword        货物名或编号
     * @param custom_code    编号
     * @param warehouse_code 仓库code
     * @param page           页码
     * @return
     */
    public Observable<CommonHttpRsp<List<Goods>>> queryGoods(String area_code, String keyword, String custom_code, String user_code, String warehouse_code, int page, boolean isPromotion,boolean isPlanAdjustment) {
        //根据大区货物查询
        if (!TextUtils.isEmpty(area_code)&&isPlanAdjustment) {
            return apiService.queryGoodsByAreaCode(area_code, keyword, page, PAGE_SIZE);
        }
        if (TextUtils.isEmpty(custom_code)) {
            //促销货物查询
            if (isPromotion) {
                return apiService.getGoodsForPromotion(keyword, warehouse_code, page, PAGE_SIZE);
            }
            //发货货物查询
            return apiService.queryGoods(keyword, warehouse_code,area_code, page, PAGE_SIZE);
        }
        //调货、退货、开票货物查询
        return apiService.queryGoodsFromDispatch(keyword, custom_code, user_code, page, PAGE_SIZE);
    }

    /**
     * 区域查询
     *
     * @return
     */
    public Observable<CommonHttpRsp<List<Area>>> queryArea() {
        return apiService.queryArea(1, 1000);
    }

    /**
     * 直属下级查询
     *
     * @return
     */
    public Observable<CommonHttpRsp<List<UserBean>>> queryUnder(String userid) {
        return apiService.queryUnder(userid);
    }

    /**
     * 客户分析查询
     *
     * @param user_code
     * @param type      0：表示新客户 1：表示老客户
     * @return
     */
    public Observable<CommonHttpRsp<List<CustomerAnalysis>>> queryCustomerAnalysis(String user_code, int type,int order_type,int order_status) {
        return apiService.queryCustomerAnalysis(user_code, type,order_type,order_status, 1, PAGE_SIZE);
    }

    /**
     * 回款类型查询
     *
     * @return
     */
    public Observable<CommonHttpRsp<List<PaymentType>>> queryPaymentType() {
        return apiService.queryPaymentType();
    }

    /**
     * 获取会计科目
     */
    public Observable<CommonHttpRsp<List<BankCaption>>> queryBankCaption() {
        return apiService.getBankCaption();
    }

    /**
     * 付款类型查询
     *
     * @return
     */
    public Observable<CommonHttpRsp<List<FukuanType>>> queryFukuanType() {
        return apiService.queryFukuanType();
    }

    /**
     * 获得发票类型
     *
     * @return
     */
    public Observable<CommonHttpRsp<List<TypeRequest>>> getInvoiceType() {
        return apiService.getInvoiceType();
    }

    /**
     * 获得回款类型
     *
     * @return
     */
    public Observable<CommonHttpRsp<List<TypeRequest>>> getHuikuanType() {
        return apiService.getHuikuanType();
    }

    /**
     * 获得报销类型
     *
     * @return
     */
    public Observable<CommonHttpRsp<List<TypeRequest>>> getReimburseType() {
        return apiService.getReimburseType();
    }

    /**
     * 获得活动类型
     *
     * @return
     */
    public Observable<CommonHttpRsp<List<TypeRequest>>> getActionType() {
        return apiService.getActionType();
    }

    /**
     * 仓库查询
     *
     * @return
     */
    public Observable<CommonHttpRsp<List<Warehouse>>> queryWarehouse() {
        return apiService.queryWarehouse(1, 1000);
    }

    /**
     * 添加计划
     *
     * @param request
     * @return
     */
    public Observable<CommonHttpRsp<Object>> activityNewPlan(ActivityAddPlanRequest request) {
        return apiService.activityNewPlan(request);
    }

    /**
     * 查询回款
     *
     * @param userid
     * @param status
     * @param startTime
     * @param endTime
     * @param page
     * @return
     */
    public Observable<CommonHttpRsp<List<Payment>>> queryPayment(String userid, String custom_code, int status, String keyword, String startTime, String endTime, int page) {
        return apiService.queryPayment(userid, custom_code, status, keyword, startTime, endTime, page, PAGE_SIZE);
    }

    /**
     * 查询回款
     *
     * @param userid
     * @param status
     * @param type
     * @param page
     * @return
     */
    public Observable<CommonHttpRsp<List<Payment>>> queryPayment(String userid, String custom_code, int status, String keyword, int type, int page) {
        return apiService.queryPayment(userid, custom_code, status, keyword, type, page, PAGE_SIZE);
    }

    /**
     * 删除回款
     *
     * @param huikuan_id
     * @return
     */
    public Observable<BaseHttpRsp> deletePayment(int huikuan_id) {
        return apiService.deletePayment(huikuan_id);
    }

    /**
     * 发货单查询
     *
     * @param userid
     * @param status
     * @param startTime
     * @param endTime
     * @param page
     * @return
     */
    public Observable<CommonHttpRsp<List<InvoiceApply>>> queryInvoiceApply(boolean isPromotion, String userid, String keyword, int status, String startTime, String endTime, int page) {
        if (isPromotion) {
            return apiService.queryPromotionApply(userid, keyword, status, startTime, endTime, page, PAGE_SIZE);
        }
        return apiService.queryInvoiceApply(userid, keyword, status, startTime, endTime, page, PAGE_SIZE);
    }

    /**
     * 大区排名
     *
     * @param page
     * @return
     */
    public Observable<CommonHttpRsp<List<RegionalRankingResponse>>> queryRegional(int page) {
        return apiService.queryRegional(page, PAGE_SIZE);
    }

    /**
     * 销售员排名
     *
     * @param page
     * @return
     */
    public Observable<CommonHttpRsp<List<SaleRankingResponse>>> querySaleRanke(int page) {
        return apiService.querySaleRanke(page, PAGE_SIZE);
    }

    /**
     * 计划调剂查询
     *
     * @param userid
     * @param status
     * @param startTime
     * @param endTime
     * @param page
     * @return
     */
    public Observable<CommonHttpRsp<List<PlanAdjustmentApply>>> queryPlanAdjustmentApply(String userid, String area_code, String from_area_code, String to_area_code, int status, String startTime, String endTime, int page) {
        return apiService.queryPlanAdjustmentApply(userid, area_code, from_area_code, to_area_code, status, startTime, endTime, page, PAGE_SIZE);
    }

    /**
     * 调货单查询
     *
     * @param userid
     * @param status
     * @param startTime
     * @param endTime
     * @param page
     * @return
     */
    public Observable<CommonHttpRsp<List<TransferApply>>> queryTransferApply(String userid, String custom_code, String keyword, int status, String startTime, String endTime, int page) {
        return apiService.queryTransferApply(userid, custom_code, keyword, status, startTime, endTime, page, PAGE_SIZE);
    }

    /**
     * 退货单查询
     *
     * @param userid
     * @param status
     * @param startTime
     * @param endTime
     * @param page
     * @return
     */
    public Observable<CommonHttpRsp<List<ReturnApply>>> queryReturnApply(String userid, String custom_code, String keyword, int status, String startTime, String endTime, int page) {
        return apiService.queryReturnApply(userid, custom_code, keyword, status, startTime, endTime, page, PAGE_SIZE);
    }

    /**
     * 开票查询
     *
     * @param userid
     * @param status
     * @param startTime
     * @param endTime
     * @param page
     * @return
     */
    public Observable<CommonHttpRsp<List<BillingApply>>> queryBillingApply(String userid, String custom_code, String keyword, int status, String startTime, String endTime, int page) {
        return apiService.queryBillingApply(userid, custom_code, keyword, status, startTime, endTime, page, PAGE_SIZE);
    }

    public Observable<CommonHttpRsp<List<StatusInfo>>> queryStatusDetail(@Query("order_code") String order_code) {
        return apiService.queryStatusDetail(order_code);
    }

    /**
     * 单一货物销售明细
     *
     * @param sale_id
     * @return
     */
    public Observable<CommonHttpRsp<GoodsSaleDetail>> queryGoodsSaleDetail(String sale_id) {
        return apiService.queryGoodsSaleDetail(sale_id);
    }


    public Observable<BaseHttpRsp> addPayment(PaymentAddRequest request) {
        return apiService.addPayment(request);
    }

    public Observable<BaseHttpRsp> addDelivery(BillAddRequest request, boolean isPromotion) {
        if (isPromotion) {
            return apiService.addPromotion(request);
        }
        return apiService.addDelivery(request);
    }

    public Observable<BaseHttpRsp> addReturn(BillAddRequest request) {
        return apiService.addReturn(request);
    }

    public Observable<BaseHttpRsp> addPlanAdjustment(PlanAdjustmentAddRequest request) {
        return apiService.addPlanAdjustment(request);
    }

    public Observable<BaseHttpRsp> addBilling(BillAddRequest request) {
        return apiService.addBilling(request);
    }

    public Observable<BaseHttpRsp> addTransfer(TransferAddRequest request) {
        return apiService.addTransfer(request);
    }

    public Observable<CommonHttpRsp<String[]>> upload(File file) {
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        // 添加描述
        String descriptionString = "hello, 这是文件描述";
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);
        return apiService.upload(description, body);
    }

    /**
     * 回款申报详情查询
     *
     * @param id
     * @return
     */
    public Observable<CommonHttpRsp<Payment[]>> getPaymentDeclarationDetails(int id) {
        return apiService.getPaymentDeclarationDetails(id);
    }

    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return
     */
    public Observable<BaseHttpRsp> updatePaymentStatus(int id, int status) {
        return apiService.updatePaymentStatus(id, status);
    }

    /**
     * 获取发货单详情
     *
     * @param id
     */
    public Observable<CommonHttpRsp<InvoiceApply>> getDeliveryDetails(int id, boolean isPromotion) {
        if (isPromotion) {
            return apiService.getPromotionDetails(id);
        }
        return apiService.getDeliveryDetails(id);
    }

    /**
     * 获取调货单详情
     *
     * @param id
     */
    public Observable<CommonHttpRsp<TransferApply>> getTransferDetails(int id) {
        return apiService.getTransferDetails(id);
    }

    /**
     * 获取退货单详情
     *
     * @param id
     */
    public Observable<CommonHttpRsp<ReturnApply>> getReturnDetails(int id) {
        return apiService.getReturnDetails(id);
    }

    /**
     * 获取开票详情
     *
     * @param id
     */
    public Observable<CommonHttpRsp<BillingApply>> getBillingDetails(int id) {
        return apiService.getBillingDetails(id);
    }

    /**
     * 获取计划调剂详情
     *
     * @param id
     */
    public Observable<CommonHttpRsp<PlanAdjustmentApply>> getPlanAdjustmentDetails(int id) {
        return apiService.getPlanAdjustmentDetails(id);
    }


    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return
     */
    public Observable<BaseHttpRsp> updateDeliveryStatus(int id, int status, boolean isPromotion) {
        if (isPromotion) {
            return apiService.updatePromotionStatus(id, status);
        }
        return apiService.updateDeliveryStatus(id, status);
    }

    /**
     * 更新状态
     *
     * @param request
     * @return
     */
    public Observable<BaseHttpRsp> updateDeliveryStatus(BillUpdateRequest request, boolean isPromotion) {
        if (isPromotion) {
            return apiService.updatePromotionStatus(request);
        }
        return apiService.updateDeliveryStatus(request);
    }

    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return
     */
    public Observable<BaseHttpRsp> updateTransferStatus(int id, int status) {
        return apiService.updateTransferStatus(id, status);
    }

    /**
     * 更新状态
     *
     * @param request
     * @return
     */
    public Observable<BaseHttpRsp> updateTransferStatus(BillUpdateRequest request) {
        return apiService.updateTransferStatus(request);
    }

    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return
     */
    public Observable<BaseHttpRsp> updatePlanAdjustmentStatus(int id, int status) {
        return apiService.updatePlanAdjustmentStatus(id, status);
    }

    /**
     * 更新状态
     *
     * @param request
     * @return
     */
    public Observable<BaseHttpRsp> updatePlanAdjustmentStatus(BillUpdateRequest request) {
        return apiService.updatePlanAdjustmentStatus(request);
    }

    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return
     */
    public Observable<BaseHttpRsp> updateReturnStatus(int id, int status) {
        return apiService.updateReturnStatus(id, status);
    }

    /**
     * 更新状态
     *
     * @param request
     * @return
     */
    public Observable<BaseHttpRsp> updateReturnStatus(BillUpdateRequest request) {
        return apiService.updateReturnStatus(request);
    }

    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return
     */
    public Observable<BaseHttpRsp> updateBillingStatus(int id, int status) {
        return apiService.updateBillingStatus(id, status);
    }

    /**
     * 更新状态
     *
     * @param request
     * @return
     */
    public Observable<BaseHttpRsp> updateBillingStatus(BillUpdateRequest request) {
        return apiService.updateBillingStatus(request);
    }

    /**
     * 更新状态
     *
     * @param request
     * @return
     */
    public Observable<BaseHttpRsp> addLinkman(AddLinkmanRequest request) {
        return apiService.addLinkman(request);
    }

    /**
     * 删除发货单据
     *
     * @param id
     */
    public Observable<BaseHttpRsp> deleteDelivert(int id, boolean isPromotion) {
        if (isPromotion) {
            return apiService.deletePromotion(id);
        }
        return apiService.deleteDelivery(id);
    }

    /**
     * 删除调货单据
     *
     * @param id
     */
    public Observable<BaseHttpRsp> deleteTransfer(int id) {
        return apiService.deleteTransfer(id);
    }

    /**
     * 删除退货单据
     *
     * @param id
     */
    public Observable<BaseHttpRsp> deleteReturn(int id) {
        return apiService.deleteReturn(id);
    }

    /**
     * 删除开票单据
     *
     * @param id
     */
    public Observable<BaseHttpRsp> deleteBilling(int id) {
        return apiService.deleteBilling(id);
    }

    /**
     * 删除计划调剂单据
     *
     * @param id
     */
    public Observable<BaseHttpRsp> deletePlanAdjustment(int id) {
        return apiService.deletePlanAdjustment(id);
    }

    /**
     * 删除单据
     *
     * @param id
     */
    public Observable<BaseHttpRsp> delDelivertSelectedGoods(int id, boolean isPromotion) {
        if (isPromotion) {
            return apiService.delPromotionSelectedGoods(id);
        }
        return apiService.delDelivertSelectedGoods(id);
    }

    /**
     * 删除调货选择货物
     *
     * @param id
     */
    public Observable<BaseHttpRsp> delTransferSelectedGoods(int id) {
        return apiService.delTransferSelectedGoods(id);
    }


    /**
     * 删除退货选择货物
     *
     * @param id
     */
    public Observable<BaseHttpRsp> delReturnSelectedGoods(int id) {
        return apiService.delReturnSelectedGoods(id);
    }

    /**
     * 删除退货选择货物
     *
     * @param id
     */
    public Observable<BaseHttpRsp> delBillingSelectedGoods(int id) {
        return apiService.delBillingSelectedGoods(id);
    }

    /**
     * 删除计划调剂选择货物
     *
     * @param id
     */
    public Observable<BaseHttpRsp> delPlanAdjustmentSelectedGoods(int id) {
        return apiService.delPlanAdjustmentSelectedGoods(id);
    }

    /**
     * 审批发货单据
     *
     * @param userid
     * @param order_code
     * @param status
     */
    public Observable<BaseHttpRsp> approvalDispatchBill(String userid, String order_code, int status, boolean isPromotion) {
        if (isPromotion) {
            return apiService.approvalPromotionBill(userid, order_code, status);
        }
        return apiService.approvalDispatchBill(userid, order_code, status);
    }

    /**
     * 审批调货单据
     *
     * @param userid
     * @param order_code
     * @param status
     */
    public Observable<BaseHttpRsp> approvalTransferBill(String userid, String order_code, int status) {
        return apiService.approvalTransferBill(userid, order_code, status);
    }

    /**
     * 审批退货单据
     *
     * @param userid
     * @param order_code
     * @param status
     */
    public Observable<BaseHttpRsp> approvalReturnBill(String userid, String order_code, int status) {
        return apiService.approvalReturnBill(userid, order_code, status);
    }

    /**
     * 审批开票单据
     *
     * @param userid
     * @param order_code
     * @param status
     */
    public Observable<BaseHttpRsp> approvalBillingBill(String userid, String order_code, int status) {
        return apiService.approvalBillingBill(userid, order_code, status);
    }

    /**
     * 审批计划调剂单据
     *
     * @param userid
     * @param status
     */
    public Observable<BaseHttpRsp> approvalPlanAdjustmentBill(String userid, String id, int status) {
        return apiService.approvalPlanAdjustmentBill(userid, id, status);
    }

    /**
     * 更改密码
     *
     * @return
     */
    public Observable<BaseHttpRsp> updatePwd(String account, String oldPwd, String pwd) {
        return apiService.updatePwd(account, oldPwd, pwd);
    }

    /**
     * 发送日报
     *
     * @return
     */
    public Observable<BaseHttpRsp> sendDaily(DailySendRequest request) {
        return apiService.sendDaily(request);
    }

    /**
     * 获取通告
     *
     * @return
     */
    public Observable<CommonHttpRsp<List<NoticeResponse>>> getNotice(String userId) {
        return apiService.getNotice(userId);
    }

    /**
     * 获取消息
     *
     * @return
     */
    public Observable<CommonHttpRsp<List<MessageResponse>>> getMessage(String userId) {
        return apiService.getMessage(userId);
    }

    /**
     * 获取日报
     *
     * @return
     */
    public Observable<CommonHttpRsp<List<DailyResponse>>> getDaily(String userId,int type, String startTime, String endTime) {
        return apiService.getDaily(userId,type, startTime, endTime);
    }

    /**
     * 获取日报
     *
     * @return
     */
    public Observable<CommonHttpRsp<CustomerWholeResponse>> getCustomerWhole(String userId, String customCode) {
        return apiService.getCustomerWhole(userId, customCode);
    }

    /**
     * 上级查询业务员日报
     *
     * @return
     */
    public Observable<CommonHttpRsp<List<DailyResponse>>> getSubDaily(String userId, String startTime, String endTime) {
        return apiService.getSubDaily(userId, startTime, endTime);
    }


    /**
     * 查询拜访计划
     *
     * @return
     */
    public Observable<CommonHttpRsp<List<VisitingPlanResponse>>> getVisitePlanList(String userid, String customerCode) {
        return apiService.getVisitePlanList(userid, customerCode);
    }

    public Observable<CommonHttpRsp<Object>> doUploader(Map<String, RequestBody> params) {
        return apiService.uploader(params);
    }

    /**
     * 查询广告
     */
    public Observable<CommonHttpRsp<List<ADInfo>>> queryAdInfo() {
        return apiService.queryAdInfo();
    }

    /**
     * 客户往来详情查询
     *
     * @param user_code 用户id
     *                  * @param user_code 客户code
     * @return
     */
    public Observable<CommonHttpRsp<CustomerContactDetail>> queryCustomerContact(String user_code, String custom_code) {
        return apiService.queryCustomerContact(user_code, custom_code);
    }


    /**
     * 添加拜访计划
     *
     * @param request
     * @return
     */
    public Observable<BaseHttpRsp> addViste(VisitingAddRequest request) {
        return apiService.addViste(request);
    }

    /**
     * 添加活动总结
     *
     * @param request
     * @return
     */
    public Observable<BaseHttpRsp> addSummary(ActivitySummaryRequest request) {
        return apiService.addSummary(request);
    }

    /**
     * 升级客户类型
     *
     * @return
     */
    public Observable<BaseHttpRsp> doUpgradeClient(CustomersResponse request) {
        return apiService.doUpgradeClient(request);
    }
}
