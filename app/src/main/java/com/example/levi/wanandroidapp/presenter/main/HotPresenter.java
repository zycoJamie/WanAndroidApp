package com.example.levi.wanandroidapp.presenter.main;

import com.example.levi.wanandroidapp.base.presenter.BasePresenter;
import com.example.levi.wanandroidapp.contract.main.HotContract;
import com.example.levi.wanandroidapp.model.api.ApiService;
import com.example.levi.wanandroidapp.model.api.ApiStore;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.util.network.RxUtil;


import javax.inject.Inject;


/**
 * Author: Levi
 * CreateDate: 2018/10/19 13:56
 */
public class HotPresenter extends BasePresenter<HotContract.View> implements HotContract.Presenter {

    @Inject
    public HotPresenter() {

    }

    @Override
    public void getHotWeb() {
        mCompositeDisposable.add(ApiStore.createApi(ApiService.class)
                .getHotWebsite()
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe(listBaseResponse -> {
                    if (listBaseResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                        mView.getHotWebOk(listBaseResponse.getData());
                    } else {
                        mView.getHotWebErr(listBaseResponse.getErrorMsg());
                    }
                }, throwable -> mView.getHotWebErr(throwable.getMessage())));
    }
}
