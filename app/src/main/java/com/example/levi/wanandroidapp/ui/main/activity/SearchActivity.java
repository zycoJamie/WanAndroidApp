package com.example.levi.wanandroidapp.ui.main.activity;

import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.base.activity.BaseRootActivity;
import com.example.levi.wanandroidapp.contract.main.SearchContract;
import com.example.levi.wanandroidapp.data.main.SearchHotWordBean;
import com.example.levi.wanandroidapp.presenter.main.SearchHotWordPresenter;

import java.util.List;

/**
 * Author: Levi
 * CreateDate: 2018/10/20 15:10
 */
public class SearchActivity extends BaseRootActivity<SearchHotWordPresenter> implements SearchContract.View {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void getSearchHotOk(List<SearchHotWordBean> dataBean) {

    }

    @Override
    public void getSearchHotError(String info) {

    }
}
