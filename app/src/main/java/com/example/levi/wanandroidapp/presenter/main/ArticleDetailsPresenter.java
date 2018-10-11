package com.example.levi.wanandroidapp.presenter.main;

import com.example.levi.wanandroidapp.base.presenter.BasePresenter;
import com.example.levi.wanandroidapp.contract.main.ArticleDetailsContract;

import javax.inject.Inject;

/**
 * Author: Levi
 * CreateDate: 2018/10/9 11:45
 */
public class ArticleDetailsPresenter extends BasePresenter<ArticleDetailsContract.IView> implements ArticleDetailsContract.Presenter {

    @Inject
    public ArticleDetailsPresenter() {

    }

    @Override
    public void collectArticle(int id) {

    }

    @Override
    public void cancelCollectArticle(int id) {

    }
}
