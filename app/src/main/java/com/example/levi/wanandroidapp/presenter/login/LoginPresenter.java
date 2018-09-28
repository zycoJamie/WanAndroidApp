package com.example.levi.wanandroidapp.presenter.login;

import com.example.levi.wanandroidapp.base.presenter.BasePresenter;
import com.example.levi.wanandroidapp.contract.login.LoginContract;

import javax.inject.Inject;

/**
 * Author: Levi
 * CreateDate: 2018/9/28 12:53
 */
public class LoginPresenter extends BasePresenter<LoginContract.IView> implements LoginContract.Presenter{

    @Inject
    public LoginPresenter(){

    }

    @Override
    public void login(String name, String password) {

    }
}
