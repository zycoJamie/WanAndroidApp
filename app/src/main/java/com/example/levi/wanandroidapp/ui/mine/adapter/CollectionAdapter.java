package com.example.levi.wanandroidapp.ui.mine.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.data.collection.CollectionListBean;

import java.util.List;

/**
 * Author: Levi
 * CreateDate: 2018/10/17 17:35
 */
public class CollectionAdapter extends BaseQuickAdapter<CollectionListBean.DatasBean, BaseViewHolder> {
    public CollectionAdapter(int layoutResId, @Nullable List<CollectionListBean.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectionListBean.DatasBean article) {
        if (!TextUtils.isEmpty(article.getTitle())) {
            helper.setText(R.id.tv_content, article.getTitle());
        }
        if (!TextUtils.isEmpty(article.getAuthor())) {
            helper.setText(R.id.tv_author, article.getAuthor());
        }
        if (!TextUtils.isEmpty(article.getNiceDate())) {
            helper.setText(R.id.tv_time, article.getNiceDate());
        }
        if (!TextUtils.isEmpty(article.getChapterName())) {
            helper.setText(R.id.tv_type, article.getChapterName());
        }
        helper.setImageResource(R.id.iv_collect, R.drawable.ic_collect);
        helper.addOnClickListener(R.id.iv_collect);
    }
}
