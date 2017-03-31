package com.yunqi.fengle.di.component;

import android.app.Activity;

import com.yunqi.fengle.ui.activity.ActivityExpenseActivity;
import com.yunqi.fengle.ui.activity.ActivityNewPlanActivity;
import com.yunqi.fengle.ui.activity.ActivityPlanActivity;
import com.yunqi.fengle.ui.activity.ActivityPlanDetailActivity;
import com.yunqi.fengle.ui.activity.AddPlanAdjustmentRequestActivity;
import com.yunqi.fengle.ui.activity.AddTransferRequestActivity;
import com.yunqi.fengle.ui.activity.ActivityPlanManagerActivity;
import com.yunqi.fengle.ui.activity.ActivitySummaryActivity;
import com.yunqi.fengle.ui.activity.AddBillingRequestActivity;
import com.yunqi.fengle.ui.activity.AddReturnRequestActivity;
import com.yunqi.fengle.ui.activity.AreaQueryActivity;
import com.yunqi.fengle.ui.activity.BillingDetailsActivity;
import com.yunqi.fengle.ui.activity.BillingRequestActivity;
import com.yunqi.fengle.ui.activity.CustomersSituationActivity;
import com.yunqi.fengle.ui.activity.DailyActivity;
import com.yunqi.fengle.ui.activity.DailySendActivity;
import com.yunqi.fengle.ui.activity.DeliveryDetailsActivity;
import com.yunqi.fengle.ui.activity.AddDeliveryRequestActivity;
import com.yunqi.fengle.ui.activity.AddMaintainActivity;
import com.yunqi.fengle.ui.activity.AddPaymentDeclarationActivity;
import com.yunqi.fengle.ui.activity.CustomerAnalysisActivity;
import com.yunqi.fengle.ui.activity.CustomerContactActivity;
import com.yunqi.fengle.ui.activity.CustomerQueryActivity;
import com.yunqi.fengle.ui.activity.DeliveryRequestActivity;
import com.yunqi.fengle.ui.activity.FukuanTypeActivity;
import com.yunqi.fengle.ui.activity.GoodsQueryActivity;
import com.yunqi.fengle.ui.activity.LoginActivity;
import com.yunqi.fengle.ui.activity.MainActivity;
import com.yunqi.fengle.ui.activity.MessageActivity;
import com.yunqi.fengle.ui.activity.MoveAttendanceSignInActivity;
import com.yunqi.fengle.ui.activity.MyCustomersActivity;
import com.yunqi.fengle.ui.activity.NoticeActivity;
import com.yunqi.fengle.ui.activity.PaymentDeclarationActivity;
import com.yunqi.fengle.ui.activity.PaymentDeclarationDetailsActivity;
import com.yunqi.fengle.ui.activity.PaymentQueryActivity;
import com.yunqi.fengle.ui.activity.PaymentTypeActivity;
import com.yunqi.fengle.ui.activity.PersonChangePwdActivity;
import com.yunqi.fengle.ui.activity.PlanAdjustmentActivity;
import com.yunqi.fengle.ui.activity.PlanAdjustmentDetailsActivity;
import com.yunqi.fengle.ui.activity.ReturnDetailsActivity;
import com.yunqi.fengle.ui.activity.ReturnRequestActivity;
import com.yunqi.fengle.ui.activity.SalesDetailActivity;
import com.yunqi.fengle.ui.activity.SplashActivity;
import com.yunqi.fengle.di.ActivityScope;
import com.yunqi.fengle.di.module.ActivityModule;
import com.yunqi.fengle.ui.activity.StockQueryActivity;
import com.yunqi.fengle.ui.activity.TransferDetailsActivity;
import com.yunqi.fengle.ui.activity.TransferRequestActivity;
import com.yunqi.fengle.ui.activity.VisitingAddCustomerActivity;
import com.yunqi.fengle.ui.activity.VisitingAddCustomerActivity2;
import com.yunqi.fengle.ui.activity.VisitingAddCustomerActivity3;
import com.yunqi.fengle.ui.activity.VisitingPlanActivity;
import com.yunqi.fengle.ui.activity.VistingAddVisteActivity;
import com.yunqi.fengle.ui.activity.WarehouseQueryActivity;


import dagger.Component;

/**
 * @author ghcui
 * @time 2017/1/11
 */
@ActivityScope
@Component(dependencies = AppComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(SplashActivity splashActivity);

    void inject(LoginActivity loginActivity);

    void inject(MainActivity mainActivity);

    void inject(StockQueryActivity stockQueryActivity);

    void inject(DeliveryRequestActivity deliveryRequestActivity);

    void inject(CustomerQueryActivity customerQueryActivity);

    void inject(AddDeliveryRequestActivity addDeliveryRequestActivity);

    void inject(PaymentQueryActivity paymentQueryActivity);

    void inject(SalesDetailActivity salesDetailActivity);

    void inject(CustomerContactActivity customerContactActivity);

    void inject(AddPaymentDeclarationActivity addPaymentDeclarationActivity);

    void inject(ActivityNewPlanActivity activityNewPlanActivity);

    void inject(TransferRequestActivity transferRequestActivity);

    void inject(GoodsQueryActivity goodsQueryActivity);

    void inject(DeliveryDetailsActivity addDeliveryActivity);

    void inject(CustomerAnalysisActivity customerAnalysisActivity);

    void inject(MoveAttendanceSignInActivity activity);

    void inject(PaymentDeclarationActivity paymentDeclarationActivity);

    void inject(AddMaintainActivity activity);

    void inject(ActivityPlanActivity activity);

    void inject(PaymentDeclarationDetailsActivity activity);

    void inject(AreaQueryActivity activity);

    void inject(WarehouseQueryActivity activity);

    void inject(PersonChangePwdActivity activity);

    void inject(NoticeActivity activity);

    void inject(VisitingPlanActivity activity);

    void inject(MessageActivity activity);

    void inject(DailyActivity activity);

    void inject(DailySendActivity activity);

    void inject(MyCustomersActivity activity);

    void inject(ActivityExpenseActivity activity);

    void inject(PaymentTypeActivity activity);

    void inject(VisitingAddCustomerActivity activity);

    void inject(VisitingAddCustomerActivity2 activity);

    void inject(VistingAddVisteActivity activity);

    void inject(TransferDetailsActivity activity);

    void inject(AddTransferRequestActivity activity);

    void inject(ActivitySummaryActivity activity);

    void inject(VisitingAddCustomerActivity3 activity);

    void inject(ReturnRequestActivity activity);

    void inject(ReturnDetailsActivity activity);

    void inject(AddReturnRequestActivity activity);

    void inject(BillingRequestActivity activity);

    void inject(BillingDetailsActivity activity);

    void inject(AddBillingRequestActivity activity);

    void inject(PlanAdjustmentActivity activity);

    void inject(PlanAdjustmentDetailsActivity activity);

    void inject(AddPlanAdjustmentRequestActivity activity);

    void inject(ActivityPlanManagerActivity activity);

    void inject(ActivityPlanDetailActivity activity);

    void inject(CustomersSituationActivity activity);

    void inject(FukuanTypeActivity activity);

//    void inject(MessageActivity activity);
}
