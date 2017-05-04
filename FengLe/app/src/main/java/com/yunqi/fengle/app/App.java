package com.yunqi.fengle.app;

import android.support.v7.app.AppCompatDelegate;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.yunqi.fengle.base.BaseApplication;
import com.yunqi.fengle.di.component.AppComponent;
import com.yunqi.fengle.di.component.DaggerAppComponent;
import com.yunqi.fengle.di.module.AppModule;
import com.yunqi.fengle.model.bean.UserBean;
import com.yunqi.fengle.model.db.RealmHelper;
import com.yunqi.fengle.ui.activity.MainActivity;
import com.yunqi.fengle.util.ToastUtil;


/**
 * @author ghcui
 * @time 2017/1/11
 */
public class App extends BaseApplication {
    private static App instance;
    private UserBean userBean;

    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initBugly();

    }

    private void initBugly() {
        Bugly.init(getApplicationContext(),"b9c3f570f4", false);
    }


    public static synchronized App getInstance() {
        return instance;
    }


    public static AppComponent getAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .build();
    }


    public void saveUserInfo(UserBean userBean) {
        this.userBean = userBean;
    }

    public UserBean getUserInfo() {
        //当因为系统内存不足时，获取数据库的方式获取用户信息
        if (userBean == null) {
            userBean = new RealmHelper(this).getUserBean();
        }
        return userBean;
    }
}
