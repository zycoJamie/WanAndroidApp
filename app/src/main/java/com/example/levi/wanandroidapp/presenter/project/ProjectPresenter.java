package com.example.levi.wanandroidapp.presenter.project;

import com.example.levi.wanandroidapp.base.presenter.BasePresenter;
import com.example.levi.wanandroidapp.contract.project.ProjectContract;
import com.example.levi.wanandroidapp.model.api.ApiService;
import com.example.levi.wanandroidapp.model.api.ApiStore;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.util.network.RxUtil;
import javax.inject.Inject;


/**
 * Author: Levi
 * CreateDate: 2018/10/16 9:36
 */
public class ProjectPresenter extends BasePresenter<ProjectContract.View> implements ProjectContract.Presenter {

    @Inject
    public ProjectPresenter() {

    }

    @Override
    public void getProjectTitle() {
        mCompositeDisposable.add(ApiStore.createApi(ApiService.class)
                .getProjectTitle()
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe(listBaseResponse -> {
                    if (listBaseResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                        mView.getProjectTitleOk(listBaseResponse.getData());
                    } else {
                        mView.getProjectTitleErr(listBaseResponse.getErrorMsg());
                    }
                }, throwable -> mView.getProjectTitleErr(throwable.getMessage())));
    }
}
