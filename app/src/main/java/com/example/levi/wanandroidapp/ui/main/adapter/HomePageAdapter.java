package com.example.levi.wanandroidapp.ui.main.adapter;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.levi.wanandroidapp.R;
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
    protected void convert(HomePageViewHolder helper, HomePageArticleBean.DatasBean article) {
        helper.getView(R.id.tv_tag).setVisibility(View.GONE);
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
            String classifyName = article.getSuperChapterName() + "/" + article.getChapterName();
            helper.setText(R.id.tv_type, classifyName);
        }
        if (article.getSuperChapterName().contains(mContext.getString(R.string.article_tag_project))) {
            helper.getView(R.id.tv_tag).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_tag, mContext.getString(R.string.article_tag_project));
            helper.setTextColor(R.id.tv_tag, mContext.getResources().getColor(R.color.green));
            helper.setBackgroundRes(R.id.tv_tag, R.drawable.shape_green_stroke);
        } else if (article.getSuperChapterName().contains(mContext.getString(R.string.article_tag_hot))) {
            helper.getView(R.id.tv_tag).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_tag, mContext.getString(R.string.article_tag_hot));
            helper.setTextColor(R.id.tv_tag, mContext.getResources().getColor(R.color.red));
            helper.setBackgroundRes(R.id.tv_tag, R.drawable.shape_red_stroke);
        }
        helper.addOnClickListener(R.id.tv_type);
        helper.addOnClickListener(R.id.iv_collect);
        helper.setImageResource(R.id.iv_collect, article.isCollect() ? R.drawable.ic_collect : R.drawable.ic_no_collect);

    }
}
