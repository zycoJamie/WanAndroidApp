package com.example.levi.wanandroidapp.presenter.login;

import com.example.levi.wanandroidapp.base.presenter.AbsPresenter;
import com.example.levi.wanandroidapp.base.presenter.BasePresenter;
import com.example.levi.wanandroidapp.contract.login.RegisterContract;

/**
 * Author: Levi
 * CreateDate: 2018/10/10 16:05
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.IView> implements RegisterContract.Presenter{
    @Override
    public void register(String name, String password, String confirmPassword) {

    }
}
