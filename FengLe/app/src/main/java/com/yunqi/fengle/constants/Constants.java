package com.yunqi.fengle.constants;

import com.yunqi.fengle.app.App;

import java.io.File;

/**
 * @author ghcui
 * @time 2017/1/12
 */
public class Constants {
    /**
     * debug
     */
    public static final boolean isDebug = false;

    //================= 第三方AppId相关====================
    //腾讯BUGLY AppId
    public static final String TENCENT_BUGLY_APPID = "72f85baf3e";

    //======================= Path=======================
    public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final String LOGIN_NAME = "LoginName";
    public static final String PASSWORD = "Password";
    public static final String LOGIN_STATUS = "Login_Status";

    public static final int STATUS_UNLOGIN = 0;

    public static final int STATUS_LOGINED = 1;


}
