package com.example.levi.wanandroidapp.ui.main.activity;

import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.base.activity.BaseRootActivity;
import com.example.levi.wanandroidapp.contract.main.SearchResultContract;
import com.example.levi.wanandroidapp.data.main.HomePageArticleBean;
import com.example.levi.wanandroidapp.presenter.main.SearchResultPresenter;

/**
 * Author: Levi
 * CreateDate: 2018/10/22 15:59
 */
public class SearchResultActivity extends BaseRootActivity<SearchResultPresenter> implements SearchResultContract.View{

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void getSearchResultOk(HomePageArticleBean searchResult, boolean isRefresh) {

    }

    @Override
    public void getSearchResultErr(String info) {

    }
}
