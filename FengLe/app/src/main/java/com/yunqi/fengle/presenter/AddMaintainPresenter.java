package com.yunqi.fengle.presenter;

import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.bean.PaymentType;
import com.yunqi.fengle.model.http.BaseHttpRsp;
import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.model.request.AddMaintainRequest;
import com.yunqi.fengle.model.request.TypeRequest;
import com.yunqi.fengle.presenter.contract.AddmaintainContract;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.ImageUploader.ImageUploaderUtils;
import com.yunqi.fengle.util.LogEx;
import com.yunqi.fengle.util.RxUtil;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

/**
 * @Author: Huangweicai
 * @date 2017-02-21 09:30
 * @Description: {@link com.yunqi.fengle.ui.activity.AddMaintainActivity}
 */

public class AddMaintainPresenter extends RxPresenter<AddmaintainContract.View> implements AddmaintainContract.Presenter{
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public AddMaintainPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }

    @Override
    public void doAddMaintain(final AddMaintainRequest request, final ResponseListener listener) {
        mRetrofitHelper.doUploader(ImageUploaderUtils.getRequestBody(request.getImages()))
                .compose(RxUtil.<CommonHttpRsp<ArrayList<String>>>rxSchedulerHelper())
                .compose(RxUtil.<List<String>>handleResult())
                .subscribe(new ExSubscriber<ArrayList<String>>(mView) {
                    @Override
                    protected void onSuccess(ArrayList<String> strings) {
                        onNext(strings);
                    }

                    @Override
                    public void onNext(ArrayList<String> strings) {
                        request.setImages(strings);
                        doRequest(request,listener);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFaild(new NetResponse(-1,"图片上传出错"));
                    }

                    @Override
                    public void onCompleted() {
                    }
                });

    }

    @Override
    public void getData(final ResponseListener listener) {
        mRetrofitHelper.getActionType()
                .compose(RxUtil.<CommonHttpRsp<List<TypeRequest>>>rxSchedulerHelper())
                .compose(RxUtil.<List<TypeRequest>>handleResult())
                .subscribe(new ExSubscriber<List<TypeRequest>>(mView) {
                    @Override
                    protected void onSuccess(List<TypeRequest> response) {
                        listener.onSuccess(new NetResponse(0,"success",response));
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFaild(new NetResponse(-1,e.getMessage()));
                    }
                });
    }

    private void doRequest(AddMaintainRequest request, final ResponseListener listener) {
        Subscription rxSubscription = mRetrofitHelper.addMaintain(request)
                .compose(RxUtil.<BaseHttpRsp>rxSchedulerHelper())
                .subscribe(new ExSubscriber<BaseHttpRsp>(mView) {
                    @Override
                    protected void onSuccess(BaseHttpRsp rsp) {
                        if (rsp.getCode() != 200) {
                            listener.onFaild(new NetResponse(-1,rsp.getMessage()));
                        } else {
                            listener.onSuccess();
                        }
                    }

//                    @Override
//                    public void onCompleted() {
//                        LogEx.i("success");
//                        listener.onSuccess();
//                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFaild(new NetResponse(-1,e.getMessage()));
                    }
                });
        addSubscrebe(rxSubscription);
    }


}
