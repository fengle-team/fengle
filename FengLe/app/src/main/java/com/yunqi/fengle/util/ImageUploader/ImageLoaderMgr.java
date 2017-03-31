package com.yunqi.fengle.util.ImageUploader;

import com.yunqi.fengle.model.http.CommonHttpRsp;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.rx.ExSubscriber;
import com.yunqi.fengle.util.LogEx;
import com.yunqi.fengle.util.RxUtil;
import com.yunqi.fengle.util.map.ResponseListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscription;

/**
 * @Author: Huangweicai
 * @date 2017-02-26 14:45
 * @Description:(这里用一句话描述这个类的作用)
 */

public class ImageLoaderMgr {

    @Inject RetrofitHelper mRetrofitHelper;

    private static volatile ImageLoaderMgr mInstance;

    public static ImageLoaderMgr getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoaderMgr.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoaderMgr();
                }
            }
        }
        return mInstance;
    }

    private ImageLoaderMgr() {

    }


    public void uploader(File file, ResponseListener listener) {
        List<File> fileList = new ArrayList<>();
        fileList.add(file);
//        Subscription rxSubscription = mRetrofitHelper.doUploader(getMap(fileList))
//                .compose(RxUtil.<CommonHttpRsp<Object>>rxSchedulerHelper())
//                .compose(RxUtil.<Object>handleResult())
//                .subscribe(new ExSubscriber<Object>(mView) {
//                    @Override
//                    protected void onSuccess(Object o) {
//                        LogEx.i("success" + o.toString());
//                    }
//
//                });
//        addSubscrebe(rxSubscription);
    }

    public void uploader(List<File> files,ResponseListener listener) {

    }

    private Map<String, RequestBody> getMap(List<File> fileList) {
        Map<String, RequestBody> params = new HashMap<>();
        for (File bean : fileList) {
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), bean);
            params.put("file\"; filename=\""+bean.getName()+"\"", fileBody);
        }
        return params;
    }


}
