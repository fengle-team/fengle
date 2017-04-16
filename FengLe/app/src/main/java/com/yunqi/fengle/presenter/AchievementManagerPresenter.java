package com.yunqi.fengle.presenter;

import com.yunqi.fengle.base.RxPresenter;
import com.yunqi.fengle.model.http.RetrofitHelper;
import com.yunqi.fengle.presenter.contract.AchievementManagerContract;
import com.yunqi.fengle.presenter.contract.LoginContract;

import javax.inject.Inject;

/**
 * @Author: Huangweicai
 * @date 2017-02-21 09:30
 * @Description: {@link com.yunqi.fengle.ui.activity.AchievementManagerActivity}
 */

public class AchievementManagerPresenter extends RxPresenter<AchievementManagerContract.View> implements AchievementManagerContract.Presenter{

    public String TAG = getClass().getName();
    private RetrofitHelper mRetrofitHelper;

    @Inject
    public AchievementManagerPresenter(RetrofitHelper retrofitHelper) {
        this.mRetrofitHelper = retrofitHelper;
    }


}
