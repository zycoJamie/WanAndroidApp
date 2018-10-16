package com.example.levi.wanandroidapp.presenter.project;

import com.example.levi.wanandroidapp.base.presenter.BasePresenter;
import com.example.levi.wanandroidapp.contract.project.ProjectListContract;
import com.example.levi.wanandroidapp.model.api.ApiService;
import com.example.levi.wanandroidapp.model.api.ApiStore;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.util.network.RxUtil;

import javax.inject.Inject;



/**
 * Author: Levi
 * CreateDate: 2018/10/16 14:13
 */
public class ProjectListPresenter extends BasePresenter<ProjectListContract.View> implements ProjectListContract.Presenter {

    private int cid;
    private int mCurrentPage;
    private boolean isRefresh = true;

    @Inject
    public ProjectListPresenter() {

    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    @Override
    public void autoRefresh() {
        isRefresh = true;
        mCurrentPage = 1;
        getProjectList(mCurrentPage, cid);
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        mCurrentPage++;
        getProjectList(mCurrentPage, cid);
    }

    @Override
    public void getProjectList(int page, int id) {
        mCompositeDisposable.add(ApiStore.createApi(ApiService.class)
                .getProjectList(page, id)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe(projectListBeanBaseResponse -> {
                    if (projectListBeanBaseResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                        mView.getProjectListOk(projectListBeanBaseResponse.getData(), isRefresh);
                    } else {
                        mView.getProjectListErr(projectListBeanBaseResponse.getErrorMsg());
                    }
                }, throwable -> mView.getProjectListErr(throwable.getMessage())));
    }
}
