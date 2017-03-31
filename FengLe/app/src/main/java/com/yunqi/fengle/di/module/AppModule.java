package com.yunqi.fengle.di.module;

import com.yunqi.fengle.app.App;
import com.yunqi.fengle.di.ContextLife;
import com.yunqi.fengle.model.db.RealmHelper;
import com.yunqi.fengle.model.http.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author ghcui
 * @time 2017/1/11
 */
@Module
public class AppModule {
    private App application;

    public AppModule(App application){
        this.application=application;
    }
    @Provides
    @Singleton
    @ContextLife("Application")
    public App provideApplicationContext(){
        return application;
    }

    @Provides
    @Singleton
    public RetrofitHelper provideRetrofitHelper(){
        return new RetrofitHelper(application);
    }
    @Provides
    @Singleton
    public RealmHelper provideRealmHelper(){
        return new RealmHelper(application);
    }

}
