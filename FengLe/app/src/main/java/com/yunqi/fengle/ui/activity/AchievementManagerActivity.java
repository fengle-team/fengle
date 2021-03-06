package com.yunqi.fengle.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.yunqi.fengle.R;
import com.yunqi.fengle.base.BaseActivity;
import com.yunqi.fengle.model.response.RegionalRankingResponse;
import com.yunqi.fengle.model.response.SaleRankingResponse;
import com.yunqi.fengle.presenter.AchievementManagerPresenter;
import com.yunqi.fengle.presenter.contract.AchievementManagerContract;
import com.yunqi.fengle.ui.fragment.RegionalRankingFragment;
import com.yunqi.fengle.ui.fragment.SalerRankingFragment;
import com.yunqi.fengle.util.map.NetResponse;
import com.yunqi.fengle.util.map.ResponseListener;

import java.util.List;

import butterknife.BindView;

/**
 * @Author: Huangweicai
 * @date 2017-02-17 09:25
 * @Description:业绩管理
 */

public class AchievementManagerActivity extends BaseActivity<AchievementManagerPresenter> implements AchievementManagerContract.View, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.rgRank)
    RadioGroup rgRank;

    private Fragment mTempFragment;
    private RegionalRankingFragment regionalFragment; //大区排名
    private SalerRankingFragment salerFragment; //业务员排名

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitleBack();
        setTitleText("业绩管理");
        initView();
        getData();
    }

    private void getData() {
        mPresenter.getRegionalRanke(new ResponseListener() {
            @Override
            public void onSuccess(NetResponse response) {
                super.onSuccess(response);
            }

            @Override
            public void onFaild(NetResponse response) {
                super.onFaild(response);
            }
        });
    }

    private void initView() {
        rgRank.setOnCheckedChangeListener(this);
        rgRank.check(R.id.rbRegional);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int id = group.getCheckedRadioButtonId();
        switch (id) {
            case R.id.rbRegional://大区排名
                if (regionalFragment == null) {
                    regionalFragment = RegionalRankingFragment.newInstance();
                }
                switchFragment(regionalFragment);
                break;
            case R.id.rbSaler://业务员排名
                if (salerFragment == null) {
                    salerFragment = SalerRankingFragment.newInstance();
                }
                switchFragment(salerFragment);
                break;
        }
    }

    private void switchFragment(Fragment fragment) {
        if (fragment != mTempFragment) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (!fragment.isAdded()) {
                if (mTempFragment != null) {
                    fragmentTransaction.hide(mTempFragment);
                }
                fragmentTransaction.add(R.id.flContent, fragment).commit();
            } else {
                fragmentTransaction.hide(mTempFragment).show(fragment).commit();
            }
            mTempFragment = fragment;
        }
    }

    @Override
    protected int getViewModel() {
        return MODE_VIEW_02;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_achievement_manager;
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

    @Override
    public void showContentRegional(List<RegionalRankingResponse> listRegional) {
    }

    @Override
    public void showMoreContentRegional(List<RegionalRankingResponse> listRegional) {
    }

    @Override
    public void showContentSale(List<SaleRankingResponse> listInvoiceApply) {

    }

    @Override
    public void showMoreContentSale(List<SaleRankingResponse> listInvoiceApplyMore) {

    }
}
