package com.example.levi.wanandroidapp.ui.main.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.base.activity.BaseRootActivity;
import com.example.levi.wanandroidapp.contract.main.ArticleDetailsContract;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.presenter.main.ArticleDetailsPresenter;

import butterknife.BindView;

/**
 * Author: Levi
 * CreateDate: 2018/10/9 11:40
 */
public class ArticleDetailsActivity extends BaseRootActivity<ArticleDetailsPresenter> implements ArticleDetailsContract.IView {

    @BindView(R.id.tb_common)
    Toolbar mToolbar;

    private String mTitle;
    private String mArticleLink;
    private int mArticleId;
    private boolean isCollect;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_details;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        getBundleData();
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(mTitle);
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    /**
     * 获取从上一个界面传递过来的extra data
     */
    private void getBundleData() {
        Bundle bundle=getIntent().getExtras();
        assert bundle!=null;
        mTitle=bundle.getString(Constant.ARTICLE_TITLE);
        mArticleLink=bundle.getString(Constant.ARTICLE_LINK);
        mArticleId=bundle.getInt(Constant.ARTICLE_ID,-1);
        isCollect=bundle.getBoolean(Constant.ARTICLE_IS_COLLECT);
    }

    @Override
    protected void initInject() {
        super.initInject();
        mActivityComponent.inject(this);
    }

    @Override
    public void initBind() {
        super.initBind();
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
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
