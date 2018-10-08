package com.example.levi.wanandroidapp.ui.main.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.levi.wanandroidapp.data.main.HomePageArticleBean;
import com.example.levi.wanandroidapp.ui.main.viewholder.HomePageViewHolder;

import java.util.List;

/**
 * Author: Levi
 * CreateDate: 2018/10/8 14:22
 */
public class HomePageAdapter extends BaseQuickAdapter<HomePageArticleBean.DatasBean, HomePageViewHolder> {

    public HomePageAdapter(int layoutResID, List<HomePageArticleBean.DatasBean> data) {
        super(layoutResID, data);
    }

    @Override
    protected void convert(HomePageViewHolder helper, HomePageArticleBean.DatasBean item) {

    }
}
