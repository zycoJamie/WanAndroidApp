package com.example.levi.wanandroidapp.base.presenter;

import com.example.levi.wanandroidapp.base.view.AbstractView;

public class BasePresenter<T extends AbstractView> implements AbsPresenter<T> {
    protected T mView;
    private int mCurrentPage;

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.mCurrentPage = currentPage;
    }
}
