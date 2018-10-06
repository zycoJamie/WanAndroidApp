package com.example.levi.wanandroidapp.base.presenter;

import com.example.levi.wanandroidapp.base.view.AbstractView;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<T extends AbstractView> implements AbsPresenter<T> {
    protected T mView;
    private int mCurrentPage;
    protected CompositeDisposable mCompositeDisposable=new CompositeDisposable();

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

    public void destroy(){
        mCompositeDisposable.dispose();
    }
}
