package com.example.levi.wanandroidapp.ui.login;

import com.example.levi.wanandroidapp.base.activity.BaseActivity;
import com.example.levi.wanandroidapp.contract.login.LoginContract;
import com.example.levi.wanandroidapp.data.login.UserInfo;
import com.example.levi.wanandroidapp.presenter.login.LoginPresenter;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.IView {


    @Override
    protected void initInject() {

    }

    @Override
    protected void initUI() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void loginOK(UserInfo userInfo) {

    }

    @Override
    public void loginErr(String info) {

    }
}