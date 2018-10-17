package com.example.levi.wanandroidapp.presenter.collection;

import com.example.levi.wanandroidapp.base.presenter.BasePresenter;
import com.example.levi.wanandroidapp.contract.collection.CollectionContract;

import javax.inject.Inject;

/**
 * Author: Levi
 * CreateDate: 2018/10/17 17:46
 */
public class CollectionPresenter extends BasePresenter<CollectionContract.View> implements CollectionContract.Presenter{

    private int mCurrentPage;
    private boolean isRefresh = true;

    @Inject
    public CollectionPresenter(){

    }

    @Override
    public void autoRefresh() {
        isRefresh = true;
        mCurrentPage = 0;
        getCollectList(mCurrentPage);
    }

    @Override
    public void loadMore() {
        isRefresh=false;
        mCurrentPage++;
        getCollectList(mCurrentPage);
    }

    @Override
    public void getCollectList(int page) {

    }

    @Override
    public void cancelCollect(int id) {

    }
}
