package com.yunqi.fengle.presenter.contract;

import com.yunqi.fengle.base.BasePresenter;
import com.yunqi.fengle.base.BaseView;
import com.yunqi.fengle.model.bean.ADInfo;
import com.yunqi.fengle.model.bean.Module;

import java.util.List;

/**
 * @author ghcui
 * @time 2017/1/11
 */
public interface MainContract {

    interface View extends BaseView{
        void showContent(List<Module> listModule);
        void showAdInfos(List<ADInfo> listAdInfos);
    }
    interface Presenter extends BasePresenter<View> {
        void authModule(String userid,List<Module> modules);
        void getAdInfos();
    }
}
