package com.example.levi.wanandroidapp.presenter.login;

import com.example.levi.wanandroidapp.base.presenter.BasePresenter;
import com.example.levi.wanandroidapp.contract.login.LoginContract;
import com.example.levi.wanandroidapp.data.login.UserInfo;
import com.example.levi.wanandroidapp.model.api.ApiService;
import com.example.levi.wanandroidapp.model.api.ApiStore;
import com.example.levi.wanandroidapp.model.api.BaseResponse;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.util.network.RxUtil;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Author: Levi
 * CreateDate: 2018/9/28 12:53
 */
public class LoginPresenter extends BasePresenter<LoginContract.IView> implements LoginContract.Presenter {

    @Inject
    public LoginPresenter() {

    }

    @Override
    public void login(String name, String password) {
        mCompositeDisposable.add(ApiStore.createApi(ApiService.class)
                .login(name, password)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe(userInfoBaseResponse -> {
                    if (userInfoBaseResponse != null && userInfoBaseResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                        mView.loginOK(userInfoBaseResponse.getData());
                    } else {
                        mView.loginErr(userInfoBaseResponse.getErrorMsg());
                    }
                }, throwable -> mView.loginErr(throwable.getMessage())));
    }
}
