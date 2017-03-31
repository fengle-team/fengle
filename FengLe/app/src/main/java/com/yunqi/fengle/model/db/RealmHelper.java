package com.yunqi.fengle.model.db;

import android.content.Context;

import com.yunqi.fengle.model.bean.UserBean;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public class RealmHelper {
    private static final String DB_NAME = "myRealm.realm";

    private Realm mRealm;

    public RealmHelper(Context mContext) {
        mRealm = Realm.getInstance(new RealmConfiguration.Builder(mContext)
                .deleteRealmIfMigrationNeeded()
                .name(DB_NAME)
                .build());
    }

    /**
     * 添加登录的用户信息
     * 使用@PrimaryKey注解后copyToRealm需要替换为copyToRealmOrUpdate
     */
    public void addUser(UserBean userBean) {
        mRealm.beginTransaction();
        mRealm.copyToRealm(userBean);
        if(mRealm.isInTransaction()){
            mRealm.commitTransaction();
        }
    }

    /**
     * 获取最后一次登录的用户信息
     * @return
     */
    public UserBean getUserBean() {
        UserBean bean = mRealm.where(UserBean.class).findFirst();
        if (bean == null)
            return null;
        return mRealm.copyFromRealm(bean);
    }


}
