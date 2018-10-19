package com.example.levi.wanandroidapp.ui.main.activity;

import com.example.levi.wanandroidapp.base.activity.BaseRootActivity;
import com.example.levi.wanandroidapp.contract.main.HotContract;
import com.example.levi.wanandroidapp.data.main.HotBean;
import com.example.levi.wanandroidapp.presenter.main.HotPresenter;

import java.util.List;

/**
 * Author: Levi
 * CreateDate: 2018/10/19 13:53
 */
public class HotActivity extends BaseRootActivity<HotPresenter> implements HotContract.View {


    @Override
    protected void initUI() {
        super.initUI();
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
    }

    @Override
    protected void initInject() {
        super.initInject();
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void getHotWebOk(List<HotBean> dataBean) {

    }

    @Override
    public void getHotWebErr(String info) {

    }
}
