package com.yunqi.fengle.base;

import android.os.Bundle;

/**
 * @Author: Huangweicai
 * @date 2017-02-15 12:24
 * @Description:基础View activity
 */

public abstract class BaseViewActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

    }

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void showError(String msg) {

    }
}
