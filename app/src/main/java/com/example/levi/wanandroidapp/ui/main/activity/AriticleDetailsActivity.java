package com.example.levi.wanandroidapp.ui.main.activity;

import com.example.levi.wanandroidapp.base.activity.BaseRootActivity;
import com.example.levi.wanandroidapp.contract.main.ArticleDetailsContract;
import com.example.levi.wanandroidapp.presenter.main.ArticleDetailsPresenter;

/**
 * Author: Levi
 * CreateDate: 2018/10/9 11:40
 */
public class AriticleDetailsActivity extends BaseRootActivity<ArticleDetailsPresenter> implements ArticleDetailsContract.IView {

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initBind() {
        super.initBind();
    }

    @Override
    public void collectArticleOK(String info) {

    }

    @Override
    public void collectArticleErr(String info) {

    }

    @Override
    public void cancelCollectArticleOK(String info) {

    }

    @Override
    public void cancelCollectArticleErr(String info) {

    }
}
