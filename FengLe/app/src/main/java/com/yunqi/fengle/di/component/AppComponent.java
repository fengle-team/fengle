package com.yunqi.fengle.di.component;

import com.yunqi.fengle.app.App;
import com.yunqi.fengle.di.ContextLife;
import com.yunqi.fengle.di.module.AppModule;
import com.yunqi.fengle.model.db.RealmHelper;
import com.yunqi.fengle.model.http.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author ghcui
 * @time 2017/1/11
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    @ContextLife("Application")
    App getContext();//提供App的Context

    RetrofitHelper  retrofitHelper(); //提供http的帮助类

    RealmHelper realmHelper();    //提供数据库帮助类
}
