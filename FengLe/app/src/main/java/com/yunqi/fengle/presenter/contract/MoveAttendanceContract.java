package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.request.SignAddRequest;
import com.yunqi.fengle.util.map.ResponseListener;

import java.util.Map;

import okhttp3.RequestBody;

/**
 * @Author: Huangweicai
 * @date 2017-02-21 09:30
 * @Description:
 */

public interface MoveAttendanceContract {
    interface View extends BaseView {

//        void signSuccess();
    }

    interface Presenter extends BasePresenter<MoveAttendanceContract.View> {
        void sign(SignAddRequest request,ResponseListener listener);

        void test(Map<String, RequestBody> params,ResponseListener listener);
    }
}
