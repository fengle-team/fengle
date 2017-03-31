package com.yunqi.fengle.di.component;

import android.app.Activity;

import com.yunqi.fengle.di.FragmentScope;
import com.yunqi.fengle.di.module.FragmentModule;

import dagger.Component;

/**
 * @author ghcui
 * @time 2017/1/11
 */
@FragmentScope
@Component(dependencies = AppComponent.class,modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();
}
