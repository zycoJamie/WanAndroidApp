package com.example.levi.wanandroidapp.presenter.knowledge;

import com.example.levi.wanandroidapp.base.presenter.BasePresenter;
import com.example.levi.wanandroidapp.contract.knowledge.KnowledgeContract;
import com.example.levi.wanandroidapp.model.api.ApiService;
import com.example.levi.wanandroidapp.model.api.ApiStore;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.util.network.RxUtil;

import javax.inject.Inject;


/**
 * Author: Levi
 * CreateDate: 2018/10/15 15:36
 */
public class KnowledgePresenter extends BasePresenter<KnowledgeContract.View> implements KnowledgeContract.Presenter {

    private boolean isRefresh = true;

    @Inject
    public KnowledgePresenter() {

    }

    @Override
    public void autoRefresh() {
        isRefresh = true;
        getKnowledgeList();
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        getKnowledgeList();
    }

    @Override
    public void getKnowledgeList() {
        mCompositeDisposable.add(ApiStore.createApi(ApiService.class)
                .getHierarchy()
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe(listBaseResponse -> {
                    if (listBaseResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                        mView.getKnowledgeListOk(listBaseResponse.getData(), isRefresh);
                    } else {
                        mView.getKnowledgeListErr(listBaseResponse.getErrorMsg());
                    }
                }, throwable -> mView.getKnowledgeListErr(throwable.getMessage())));
    }
}
