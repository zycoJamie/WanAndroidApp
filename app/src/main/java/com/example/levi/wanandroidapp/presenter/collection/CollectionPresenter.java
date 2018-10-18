package com.example.levi.wanandroidapp.presenter.collection;

import com.example.levi.wanandroidapp.base.presenter.BasePresenter;
import com.example.levi.wanandroidapp.contract.collection.CollectionContract;
import com.example.levi.wanandroidapp.model.api.ApiService;
import com.example.levi.wanandroidapp.model.api.ApiStore;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.util.network.RxUtil;

import javax.inject.Inject;

/**
 * Author: Levi
 * CreateDate: 2018/10/17 17:46
 */
public class CollectionPresenter extends BasePresenter<CollectionContract.View> implements CollectionContract.Presenter {

    private int mCurrentPage;
    private boolean isRefresh = true;

    @Inject
    public CollectionPresenter() {

    }

    @Override
    public void autoRefresh() {
        isRefresh = true;
        mCurrentPage = 0;
        getCollectList(mCurrentPage);
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        mCurrentPage++;
        getCollectList(mCurrentPage);
    }

    @Override
    public void getCollectList(int page) {
        mCompositeDisposable.add(ApiStore.createApi(ApiService.class)
                .collectionListArticle(page)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe(collectionListBeanBaseResponse -> {
                    if (collectionListBeanBaseResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                        mView.getCollectListOk(collectionListBeanBaseResponse.getData(), isRefresh);
                    } else {
                        mView.getCollectListErr(collectionListBeanBaseResponse.getErrorMsg());
                    }
                }, throwable -> mView.getCollectListErr(throwable.getMessage())));
    }

    @Override
    public void cancelCollect(int id) {
        mCompositeDisposable.add(ApiStore.createApi(ApiService.class)
                .cancelCollectionListArticle(id, id)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe(baseResponse -> {
                    if (baseResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                        mView.cancelCollectOk();
                    } else {
                        mView.cancelCollectErr(baseResponse.getErrorMsg());
                    }
                }, throwable -> mView.getCollectListErr(throwable.getMessage())));
    }
}
