package com.example.levi.wanandroidapp.presenter.login;

import com.example.levi.wanandroidapp.base.presenter.BasePresenter;
import com.example.levi.wanandroidapp.contract.login.RegisterContract;
import com.example.levi.wanandroidapp.model.api.ApiService;
import com.example.levi.wanandroidapp.model.api.ApiStore;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.util.network.RxUtil;

import javax.inject.Inject;


/**
 * Author: Levi
 * CreateDate: 2018/10/10 16:05
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.IView> implements RegisterContract.Presenter {

    @Inject
    public RegisterPresenter() {

    }

    @Override
    public void register(String name, String password, String confirmPassword) {
        mCompositeDisposable.add(ApiStore.createApi(ApiService.class)
                .register(name, password, confirmPassword)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe(userInfoBaseResponse -> {
                    if (userInfoBaseResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                        mView.registerOk(userInfoBaseResponse.getData());
                    } else {
                        mView.registerErr(userInfoBaseResponse.getErrorMsg());
                    }
                }, throwable -> mView.registerErr(throwable.getMessage())));
    }
}
