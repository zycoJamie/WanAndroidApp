package com.example.levi.wanandroidapp.base.presenter;


import com.example.levi.wanandroidapp.base.view.AbstractView;

public interface AbsPresenter<T extends AbstractView> {
    /**
     * 注入View
     * @param view
     */
    void attachView(T view);

    /**
     * 回收View
     */
    void detachView();
}
