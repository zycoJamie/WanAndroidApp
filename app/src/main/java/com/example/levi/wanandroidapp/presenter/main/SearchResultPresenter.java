package com.example.levi.wanandroidapp.presenter.main;

import com.example.levi.wanandroidapp.base.presenter.BasePresenter;
import com.example.levi.wanandroidapp.contract.main.SearchResultContract;
import com.example.levi.wanandroidapp.model.api.ApiService;
import com.example.levi.wanandroidapp.model.api.ApiStore;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.util.network.RxUtil;

import javax.inject.Inject;

/**
 * Author: Levi
 * CreateDate: 2018/10/22 16:04
 */
public class SearchResultPresenter extends BasePresenter<SearchResultContract.View> implements SearchResultContract.Presenter {

    private boolean isRefresh = true;
    private int mCurrentPage;

    @Inject
    public SearchResultPresenter() {
    }

    @Override
    public void autoRefresh(String key) {
        isRefresh = true;
        mCurrentPage = 0;
        getSearchResult(mCurrentPage, key);
    }

    @Override
    public void loadMore(String key) {
        isRefresh = false;
        mCurrentPage++;
        getSearchResult(mCurrentPage, key);
    }

    @Override
    public void getSearchResult(int page, String key) {
        mCompositeDisposable.add(ApiStore.createApi(ApiService.class)
                .getSearchResult(page, key)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe(listBaseResponse -> {
                    if (listBaseResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                        mView.getSearchResultOk(listBaseResponse.getData(), isRefresh);
                    } else {
                        mView.getSearchResultErr(listBaseResponse.getErrorMsg());
                    }
                }, throwable -> mView.getSearchResultErr(throwable.getMessage())));
    }
}
