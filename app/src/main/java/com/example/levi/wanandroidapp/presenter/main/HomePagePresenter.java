package com.example.levi.wanandroidapp.presenter.main;

import com.example.levi.wanandroidapp.base.presenter.BasePresenter;
import com.example.levi.wanandroidapp.contract.main.HomePageContract;

import javax.inject.Inject;

/**
 * Author: Levi
 * CreateDate: 2018/10/4 15:09
 */
public class HomePagePresenter extends BasePresenter<HomePageContract.IView> implements HomePageContract.Presenter{
    private boolean isRefresh=true;
    private int currentPage;

    @Inject
    public HomePagePresenter(){

    }

    /**
     * 下拉刷新
     */
    @Override
    public void autoRefresh() {
        isRefresh=true;
        currentPage=0;
        getBanner();
        getHomepageList(currentPage);
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void loadMore() {
        isRefresh=false;
        currentPage++;
        getHomepageList(currentPage);
    }

    @Override
    public void getHomepageList(int page) {

    }

    @Override
    public void getBanner() {

    }

    @Override
    public void loginAndLoad() {

    }

    @Override
    public void collectArticle(int id) {

    }

    @Override
    public void cancelCollectArticle(int id) {

    }
}
