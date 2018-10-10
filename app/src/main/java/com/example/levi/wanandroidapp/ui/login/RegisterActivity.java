package com.example.levi.wanandroidapp.ui.login;

import android.view.MotionEvent;

import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.base.activity.BaseActivity;
import com.example.levi.wanandroidapp.contract.login.RegisterContract;
import com.example.levi.wanandroidapp.data.login.UserInfo;
import com.example.levi.wanandroidapp.presenter.login.RegisterPresenter;

import butterknife.BindView;

/**
 * Author: Levi
 * CreateDate: 2018/10/10 16:03
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.IView {


    @Override
    protected void initToolbar() {
        super.initToolbar();
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initInject() {
        super.initInject();
    }


    @Override
    public void registerOk(UserInfo userInfo) {

    }

    @Override
    public void registerErr(String info) {

    }
}
