package com.yunqi.fengle.di.module;

import android.app.Activity;

import com.yunqi.fengle.di.ActivityScope;


import dagger.Module;
import dagger.Provides;

/**
 * @author ghcui
 * @time 2017/1/11
 */
@Module
public class ActivityModule {
    private Activity mActivity;


    public ActivityModule(Activity activity){
        this.mActivity=activity;
    }
    @Provides
    @ActivityScope
    public Activity provideActvity(){
        return  mActivity;
    }


}
