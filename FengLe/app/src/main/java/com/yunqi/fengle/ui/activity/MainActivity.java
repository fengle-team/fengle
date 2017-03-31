package com.yunqi.fengle.ui.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.yunqi.fengle.R;
import com.yunqi.fengle.app.App;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.bean.ADInfo;
import com.yunqi.fengle.model.bean.Module;
import com.yunqi.fengle.parser.IModuleParse;
import com.yunqi.fengle.parser.ModuleParse;
import com.yunqi.fengle.presenter.MainPresenter;
import com.yunqi.fengle.presenter.contract.MainContract;
import com.yunqi.fengle.ui.adapter.ModuleAdapter;
import com.yunqi.fengle.util.ToastUtil;
import com.yunqi.fengle.util.ViewFactory;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.androiddevelop.cycleviewpager.lib.CycleViewPager;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    @BindView(R.id.grid_module)
    GridView gridModule;
    private List<ImageView> views = new ArrayList<ImageView>();
    private CycleViewPager cycleViewPager;
    private List<Module> moduleList;
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    /**
     * 定义一个变量，来标识是否退出
     */
    private  boolean isExit = false;
    private  Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        initBannerData();
        initGridData();
    }

    private void initBannerData() {
        cycleViewPager = (CycleViewPager) getFragmentManager()
                .findFragmentById(R.id.cycle_viewPager);
        mPresenter.getAdInfos();
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(this, this.getResources().getString(R.string.warming_doubleclick_logout), Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            App.getInstance().killAllActivities();
            super.onBackPressedSupport();
        }
    }

    @Override
    public void onBackPressedSupport() {
        exit();
    }

    private void initGridData() {
        try {
            //通过assertmanager的open方法获取到beauties.xml文件的输入流
            InputStream is = this.getAssets().open("home_module.xml");
            //初始化自定义的实现类BeautyParserImpl
            IModuleParse parser = new ModuleParse();
            //调用pbp的parse()方法，将输入流传进去解析，返回的链表结果赋给beautyList
            moduleList = parser.parse(is);
            mPresenter.authModule("13",moduleList);
            gridModule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent = new Intent();
                    String className = moduleList.get(position).className;
                    if (TextUtils.isEmpty(className)) {
                        return;
                    }
                    try {
                        ComponentName cn = new ComponentName(getPackageName(), className);
                        intent.setComponent(cn);
                        startActivity(intent);
                    } catch (Exception e) {
                        ToastUtil.showNoticeToast(MainActivity.this, "正在开发中，敬请期待!");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void showError(String msg) {
        ToastUtil.showErrorToast(this,msg);
    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {
        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                position = position - 1;

            }

        }

    };

    @Override
    public void showContent(List<Module> listModuleL) {
        ModuleAdapter adapter = new ModuleAdapter(this, listModuleL);
        gridModule.setAdapter(adapter);
    }

    @Override
    public void showAdInfos(List<ADInfo> listAdInfos) {
        if(listAdInfos.isEmpty()){
            return;
        }
        infos.clear();
        infos.addAll(listAdInfos);
        views.add(ViewFactory.getImageView(this, infos.get(infos.size() - 1).getUrl()));
        for (int i = 0; i < infos.size(); i++) {
            views.add(ViewFactory.getImageView(this, infos.get(i).getUrl()));
        }
        // 将第一个ImageView添加进来
        views.add(ViewFactory.getImageView(this, infos.get(0).getUrl()));

        // 设置循环，在调用setData方法前调用
        cycleViewPager.setCycle(true);

        // 在加载数据前设置是否循环
        cycleViewPager.setData(views, infos, mAdCycleViewListener);
        //设置轮播
        cycleViewPager.setWheel(true);

        // 设置轮播时间，默认5000ms
        cycleViewPager.setTime(2000);
        //设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorCenter();
    }
}

