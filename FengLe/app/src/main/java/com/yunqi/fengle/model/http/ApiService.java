package com.yunqi.fengle.model.http;

import com.yunqi.fengle.model.bean.ADInfo;
import com.yunqi.fengle.model.bean.Area;
import com.yunqi.fengle.model.bean.BillingApply;
import com.yunqi.fengle.model.bean.Customer;
import com.yunqi.fengle.model.bean.CustomerContactDetail;
import com.yunqi.fengle.model.bean.FukuanType;
import com.yunqi.fengle.model.bean.Goods;
import com.yunqi.fengle.model.bean.InvoiceApply;
import com.yunqi.fengle.model.bean.Module;
import com.yunqi.fengle.model.bean.Payment;
import com.yunqi.fengle.model.bean.PaymentType;
import com.yunqi.fengle.model.bean.PlanAdjustmentApply;
import com.yunqi.fengle.model.bean.ReturnApply;
import com.yunqi.fengle.model.bean.SaleInfo;
import com.yunqi.fengle.model.bean.SplashBean;
import com.yunqi.fengle.model.bean.TransferApply;
import com.yunqi.fengle.model.bean.UserBean;
import com.yunqi.fengle.model.bean.Warehouse;
import com.yunqi.fengle.model.request.ActivityAddPlanRequest;
import com.yunqi.fengle.model.request.ActivityExpenseRequest;
import com.yunqi.fengle.model.request.ActivitySummaryRequest;
import com.yunqi.fengle.model.request.BillUpdateRequest;
import com.yunqi.fengle.model.request.DailySendRequest;
import com.yunqi.fengle.model.request.BillAddRequest;
import com.yunqi.fengle.model.request.PaymentAddRequest;
import com.yunqi.fengle.model.request.AddMaintainRequest;
import com.yunqi.fengle.model.request.SignAddRequest;
import com.yunqi.fengle.model.request.TransferAddRequest;
import com.yunqi.fengle.model.request.TypeRequest;
import com.yunqi.fengle.model.request.VisitingAddRequest;
import com.yunqi.fengle.model.request.VisitingUpdateRequest;
import com.yunqi.fengle.model.response.ActivityAddResponse;
import com.yunqi.fengle.model.response.CustomersResponse;
import com.yunqi.fengle.model.response.CustomersSituationResponse;
import com.yunqi.fengle.model.response.DailyResponse;
import com.yunqi.fengle.model.response.MessageResponse;
import com.yunqi.fengle.model.response.NoticeResponse;
import com.yunqi.fengle.model.response.VisitingPlanResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public interface ApiService {

//    String baseUrl = "http://60.174.196.101:3080";
//    String baseUrl = "http://192.168.1.103:3080";
    String baseUrl = "http://60.174.196.102:3080";
    String HOST = baseUrl + "/api/";

    /**
     * 启动界面图片
     */
    @GET("classes/File")
    Observable<BmobListResponse<List<SplashBean>>> getSplashInfo();


    /**
     * 登录接口
     */

    @FormUrlEncoded
    @POST("users/login")
    Observable<CommonHttpRsp<UserBean>> doLogin(@Field("account") String account, @Field("password") String password);

    /**
     * 查询个人模块接口
     */
    @GET("auth_module")
    Observable<CommonHttpRsp<List<Module>>> authModule(@Query("userid") String userid);

    /**
     * 查询库存接口
     */
    @GET("goods/get_stock")
    Observable<CommonHttpRsp<List<Goods>>> queryStock(@Query("warehouse_code") String warehouse_code,@Query("area_code") String area_code, @Query("keyword") String keyword, @Query("page") int page,@Query("size") int size);

    /**
     * 查询客户接口
     */
    @GET("custom/get")
    Observable<CommonHttpRsp<List<Customer>>> queryCustomer(@Query("keyword") String keyword,@Query("user_code") String user_code,@Query("page") int page,@Query("type") int type);
    /**
     * 查询大区
     */
    @GET("area/get")
    Observable<CommonHttpRsp<List<Area>>> queryArea(@Query("page") int page,@Query("size") int size);
    /**
     * 回款类型查询
     */
    @GET("type/get_huikuan_type")
    Observable<CommonHttpRsp<List<PaymentType>>> queryPaymentType();
    /**
     * 付款类型查询
     */
    @GET("type/get_huikuan_pay_type")
    Observable<CommonHttpRsp<List<FukuanType>>> queryFukuanType();
    /**
     * 发票类型查询
     */
    @GET("type/get_invoice_type")
    Observable<CommonHttpRsp<List<TypeRequest>>> getInvoiceType();
    /**
     * 回款类型查询
     */
    @GET("type/get_huikuan_type")
    Observable<CommonHttpRsp<List<TypeRequest>>> getHuikuanType();
    /**
     * 报销类型查询
     */
    @GET("type/get_reimburse_type")
    Observable<CommonHttpRsp<List<TypeRequest>>> getReimburseType();
    /**
     * 活动类型查询
     */
    @GET("type/get_action_type")
    Observable<CommonHttpRsp<List<TypeRequest>>> getActionType();

    /**
     * 查询仓库
     */
    @GET("warehouse/get")
    Observable<CommonHttpRsp<List<Warehouse>>> queryWarehouse(@Query("page") int page,@Query("size") int size);

    /**
     * 查询货物接口
     */
    @GET("goods/get")
    Observable<CommonHttpRsp<List<Goods>>> queryGoods(@Query("keyword") String keyword,@Query("userid")String userid,@Query("warehouse_id")String warehouse_id,@Query("page") int page,@Query("size") int size);

    /**
     * 查询活动 {@link com.yunqi.fengle.ui.activity.ActivityPlanActivity}
     *
     * @param userid
     * @return
     */
    @GET("action/get")
    Observable<CommonHttpRsp<List<ActivityAddResponse>>> queryActivities(@Query("userid") String userid);

    /**
     * 添加活动计划
     *
     * @param request
     * @return
     */
    @POST("action/add")
    Observable<CommonHttpRsp<Object>> activityNewPlan(@Body ActivityAddPlanRequest request);

    /**
     * 签到 签退
     *
     * @param request
     * @return
     */
    @POST("sign/add")
    Observable<BaseHttpRsp> signAdd(@Body SignAddRequest request);


    /**
     * 更新确认状态
     *
     * @param request
     * @return
     */
    @POST("visite_plan/update_status")
    Observable<BaseHttpRsp> updateVisiteStatus(@Body VisitingUpdateRequest request);

    /**
     * 发货申请添加
     *
     * @param request
     * @return
     */
    @POST("dispatch_bill/add")
    Observable<BaseHttpRsp> addDelivery(@Body BillAddRequest request);
    /**
     * 退货申请添加
     *
     * @param request
     * @return
     */
    @POST("sale_return/add")
    Observable<BaseHttpRsp> addReturn(@Body BillAddRequest request);

    /**
     * 调货申请添加
     *
     * @param request
     * @return
     */
    @POST("delivered/add")
    Observable<BaseHttpRsp> addTransfer(@Body TransferAddRequest request);
    /**
     * 开票申请添加
     *
     * @param request
     * @return
     */
    @POST("invoice/add")
    Observable<BaseHttpRsp> addBilling(@Body BillAddRequest request);
    /**
     * 计划调剂添加
     *
     * @param request
     * @return
     */
    @POST("invoice/add")
    Observable<BaseHttpRsp> addPlanAdjustment(@Body TransferAddRequest request);

    /**
     * 添加拜访计划
     *
     * @param request
     * @return
     */
    @POST("visite_plan/add")
    Observable<BaseHttpRsp> addViste(@Body VisitingAddRequest request);
    /**
     * 添加活动总结
     *
     * @param request
     * @return
     */
    @POST("action_summary/add")
    Observable<BaseHttpRsp> addSummary(@Body ActivitySummaryRequest request);

    /**
     * 删除回款单据
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("huikuan/delete")
    Observable<BaseHttpRsp> deletePayment(@Field("id") int id);

    /**
     * 查询回款接口
     */
    @GET("huikuan/get")
    Observable<CommonHttpRsp<List<Payment>>> queryPayment(@Query("userid") String userid, @Query("status") int status, @Query("start_time") String start_time, @Query("end_time") String end_time, @Query("page") int page, @Query("size") int size);
    /**
     * 查询回款接口
     */
    @GET("huikuan/get")
    Observable<CommonHttpRsp<List<Payment>>> queryPayment(@Query("userid") String userid, @Query("status") int status,@Query("type") int type,@Query("page") int page, @Query("size") int size);
    /**
     * 添加回款申报
     *
     * @param request
     * @return
     */
    @POST("huikuan/add")
    Observable<BaseHttpRsp> addPayment(@Body PaymentAddRequest request);

    /**
     * 单文件文件上传
     *
     * @param file
     * @return
     */
    @Multipart
    @POST("upload")
    Observable<CommonHttpRsp<String[]>> upload(@Part("description") RequestBody description,
                                   @Part MultipartBody.Part file);
    /**
     * 回款申报详情查询
     *
     * @param id
     * @return
     */
    @GET("huikuan/get_by_id")
    Observable<CommonHttpRsp<Payment[]>> getPaymentDeclarationDetails(@Query("id")int id);

    /**
     * 发货单查询接口
     */
    @GET("dispatch_bill/get")
    Observable<CommonHttpRsp<List<InvoiceApply>>> queryInvoiceApply(@Query("userid") String userid, @Query("keyword") String keyword,@Query("status") int status, @Query("start_time") String start_time, @Query("end_time") String end_time, @Query("page") int page, @Query("size") int size);

    /**
     * 退货单查询接口
     */
    @GET("sale_return/get")
    Observable<CommonHttpRsp<List<ReturnApply>>> queryReturnApply(@Query("userid") String userid,  @Query("keyword") String keyword,@Query("status") int status, @Query("start_time") String start_time, @Query("end_time") String end_time, @Query("page") int page, @Query("size") int size);
    /**
     * 开票查询接口
     */
    @GET("invoice/get")
    Observable<CommonHttpRsp<List<BillingApply>>> queryBillingApply(@Query("userid") String userid,  @Query("keyword") String keyword,@Query("status") int status, @Query("start_time") String start_time, @Query("end_time") String end_time, @Query("page") int page, @Query("size") int size);


    /**
     * 调货单查询接口
     */
    @GET("delivered/get")
    Observable<CommonHttpRsp<List<TransferApply>>> queryTransferApply(@Query("userid") String userid, @Query("keyword") String keyword, @Query("status") int status, @Query("start_time") String start_time, @Query("end_time") String end_time, @Query("page") int page, @Query("size") int size);

    /**
     * 计划查询接口
     */
    @GET("delivered/get")
    Observable<CommonHttpRsp<List<PlanAdjustmentApply>>> queryPlanAdjustmentApply(@Query("userid") String userid, @Query("keyword") String keyword, @Query("status") int status, @Query("start_time") String start_time, @Query("end_time") String end_time, @Query("page") int page, @Query("size") int size);

    /**
     * 发货单详情查询接口
     */
    @GET("dispatch_bill/get_by_id")
    Observable<CommonHttpRsp<InvoiceApply>> getDeliveryDetails(@Query("id") int id);
    /**
     * 调货单详情查询接口
     */
    @GET("delivered/get_by_id")
    Observable<CommonHttpRsp<TransferApply>> getTransferDetails(@Query("id") int id);
    /**
     * 退货单详情查询接口
     */
    @GET("sale_return/get_by_id")
    Observable<CommonHttpRsp<ReturnApply>> getReturnDetails(@Query("id") int id);
    /**
     * 开票详情查询接口
     */
    @GET("invoice/get_by_id")
    Observable<CommonHttpRsp<BillingApply>> getBillingDetails(@Query("id") int id);

    /**
     * 计划调剂详情查询接口
     */
    @GET("invoice/get_by_id")
    Observable<CommonHttpRsp<PlanAdjustmentApply>> getPlanAdjustmentDetails(@Query("id") int id);
    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return
     */
    @FormUrlEncoded
    @POST("dispatch_bill/update_status")
    Observable<BaseHttpRsp> updateDeliveryStatus(@Field("id") int id,@Field("status")int status);


    /**
     * 更新状态
     * @param request
     * @return
     */
    @POST("dispatch_bill/update_status")
    Observable<BaseHttpRsp> updateDeliveryStatus(@Body BillUpdateRequest request);
    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return
     */
    @FormUrlEncoded
    @POST("delivered/update_status")
    Observable<BaseHttpRsp> updateTransferStatus(@Field("id") int id,@Field("status")int status);
    /**
     * 更新状态
     * @param request
     * @return
     */
    @POST("delivered/update_status")
    Observable<BaseHttpRsp> updateTransferStatus(@Body BillUpdateRequest request);
    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return
     */
    @FormUrlEncoded
    @POST("dispatch_bill/update_status")
    Observable<BaseHttpRsp> updatePlanAdjustmentStatus(@Field("id") int id,@Field("status")int status);
    /**
     * 更新状态
     * @param request
     * @return
     */
    @POST("dispatch_bill/update_status")
    Observable<BaseHttpRsp> updatePlanAdjustmentStatus(@Body BillUpdateRequest request);
    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return
     */
    @FormUrlEncoded
    @POST("huikuan/update_status")
    Observable<BaseHttpRsp> updatePaymentStatus(@Field("id") int id,@Field("status")int status);

    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return
     */
    @FormUrlEncoded
    @POST("sale_return/update_status")
    Observable<BaseHttpRsp> updateReturnStatus(@Field("id") int id,@Field("status")int status);
    /**
     * 更新状态
     * @param request
     * @return
     */
    @POST("sale_return/update_status")
    Observable<BaseHttpRsp> updateReturnStatus(@Body BillUpdateRequest request);

    /**
     * 更新状态
     *
     * @param id
     * @param status
     * @return
     */
    @FormUrlEncoded
    @POST("invoice/update_status")
    Observable<BaseHttpRsp> updateBillingStatus(@Field("id") int id,@Field("status")int status);
    /**
     * 更新状态
     * @param request
     * @return
     */
    @POST("invoice/update_status")
    Observable<BaseHttpRsp> updateBillingStatus(@Body BillUpdateRequest request);

    /**
     * 添加活动计划
     *
     * @param request
     * @return
     */
    @POST("maintain/add")
    Observable<CommonHttpRsp<Object>> addMaintain(@Body AddMaintainRequest request);
    /**
     * 添加费用报销
     * @param request
     * @return
     */
    @POST("reimburse/add")
    Observable<CommonHttpRsp<Object>> addReimburse(@Body ActivityExpenseRequest request);


    /**
     * 上传文件
     *
     * @return
     */
    @Multipart
    @POST("upload")
    Observable<CommonHttpRsp<Object>> uploader(@PartMap Map<String, RequestBody> file);

    /**
     * 删除发货单据
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("dispatch_bill/delete")
    Observable<BaseHttpRsp> deleteDelivery(@Field("id") int id);

    /**
     * 删除调货单据
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("delivered/delete")
    Observable<BaseHttpRsp> deleteTransfer(@Field("id") int id);


    /**
     * 删除退货单据
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("sale_return/delete")
    Observable<BaseHttpRsp> deleteReturn(@Field("id") int id);
    /**
     * 删除计划调剂
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("sale_return/delete")
    Observable<BaseHttpRsp> deletePlanAdjustment(@Field("id") int id);

    /**
     * 删除发票单据
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("invoice/delete")
    Observable<BaseHttpRsp> deleteBilling(@Field("id") int id);
    /**
     * 删除发货选择货物
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("dispatch_bill/delete_selected_goods")
    Observable<BaseHttpRsp> delDelivertSelectedGoods(@Field("id") int id);
    /**
     * 删除调货选择货物
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("delivered/delete_selected_goods")
    Observable<BaseHttpRsp> delTransferSelectedGoods(@Field("id") int id);

    /**
     * 删除退货选择货物
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("sale_return/delete_selected_goods")
    Observable<BaseHttpRsp> delPlanAdjustmentSelectedGoods(@Field("id") int id);
    /**
     * 删除退货选择货物
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("sale_return/delete_selected_goods")
    Observable<BaseHttpRsp> delReturnSelectedGoods(@Field("id") int id);

    /**
     * 删除发票选择货物
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("invoice/delete_selected_goods")
    Observable<BaseHttpRsp> delBillingSelectedGoods(@Field("id") int id);
    /**
     * 审批发货单据
     *
     * @return
     */
    @FormUrlEncoded
    @POST("dispatch_bill/approval")
    Observable<BaseHttpRsp> approvalDispatchBill(@Field("userid") String userid,@Field("order_code") String order_code,@Field("status") int status);

    /**
     * 审批发货单据
     *
     * @return
     */
    @FormUrlEncoded
    @POST("delivered/approval")
    Observable<BaseHttpRsp> approvalTransferBill(@Field("userid") String userid,@Field("order_code") String order_code,@Field("status") int status);
    /**
     * 审批退货单据
     *
     * @return
     */
    @FormUrlEncoded
    @POST("sale_return/approval")
    Observable<BaseHttpRsp> approvalReturnBill(@Field("userid") String userid,@Field("order_code") String order_code,@Field("status") int status);
    /**
     * 审批开票单据
     *
     * @return
     */
    @FormUrlEncoded
    @POST("invoice/approval")
    Observable<BaseHttpRsp> approvalBillingBill(@Field("userid") String userid,@Field("order_code") String order_code,@Field("status") int status);
    /**
     * 审批计划调剂单据
     *
     * @return
     */
    @FormUrlEncoded
    @POST("return_bill/approval")
    Observable<BaseHttpRsp> approvalPlanAdjustmentBill(@Field("userid") String userid,@Field("order_code") String order_code,@Field("status") int status);
    /**
     * 修改密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("users/reset_password")
    Observable<BaseHttpRsp> updatePwd(@Field("account") String account,@Field("old_pwd")String old_pwd,@Field("new_pwd")String new_pwd);
    /**
     * 查询广告
     */
    @GET("banner")
    Observable<CommonHttpRsp<List<ADInfo>>> queryAdInfo();

    /**
     * 添加日报
     *
     * @param request
     * @return
     */
    @POST("daily/add")
    Observable<BaseHttpRsp> sendDaily(@Body DailySendRequest request);
    /**
     * 客户往来详情查询
     */
    @GET("custom/get_wanglai")
    Observable<CommonHttpRsp<CustomerContactDetail>> queryCustomerContact(@Query("id") String id);
    /**
     * 获取公告
     *
     * @return
     */
    @GET("notice/get_notice")
    Observable<CommonHttpRsp<List<NoticeResponse>>> getNotice(@Query("userid") String userid);
    /**
     * 获取消息
     *
     * @return
     */
    @GET("notice/get_message")
    Observable<CommonHttpRsp<List<MessageResponse>>> getMessage(@Query("userid") String userid);
    /**
     * 获取日报
     *
     * @return
     */
    @GET("daily/get")
    Observable<CommonHttpRsp<List<DailyResponse>>> getDaily(@Query("userid") String userid,@Query("start_time") String start_time,@Query("end_time") String end_time);

    /**
     * 获取我的客户
     *
     * @return
     */
    @GET("custom/get")
    Observable<CommonHttpRsp<List<CustomersResponse>>> getCustomers(@Query("user_code") String user_code);

    /**
     * 获取客情维护
     *
     * @return
     */
    @GET("maintain/get")
    Observable<CommonHttpRsp<List<CustomersSituationResponse>>> getMainTain(@Query("userid") String userid);

    /**
     * 获取计划列表
     *
     * @return
     */
    @GET("visite_plan/get")
    Observable<CommonHttpRsp<List<VisitingPlanResponse>>> getVisitePlanList(@Query("userid") String userid);
    /**
     * 查询货物销售明细接口
     * @param start_time 开始时间
     * @param end_time  结束时间
     * @param goods_id  货物Id
     * @param page 页码
     * @param size 大小
     * @return
     */
    @GET("goods/sale_detail")
    Observable<CommonHttpRsp<List<SaleInfo>>> querySales(@Query("start_time") String start_time, @Query("end_time") String end_time,@Query("goods_id") String goods_id, @Query("page") int page, @Query("size") int size);


}
