package com.example.levi.wanandroidapp.ui.project.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.data.project.ProjectListBean;
import com.example.levi.wanandroidapp.util.glide.GlideUtil;

import java.util.List;

/**
 * Author: Levi
 * CreateDate: 2018/10/16 15:13
 */
public class ProjectListAdapter extends BaseQuickAdapter<ProjectListBean.DatasBean, BaseViewHolder> {

    public ProjectListAdapter(int resId, List<ProjectListBean.DatasBean> data) {
        super(resId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectListBean.DatasBean item) {
        if (!TextUtils.isEmpty(item.getTitle())) {
            helper.setText(R.id.tv_title, item.getTitle());
        }
        if (!TextUtils.isEmpty(item.getDesc())) {
            helper.setText(R.id.tv_content, item.getDesc());
        }
        if (!TextUtils.isEmpty(item.getNiceDate())) {
            helper.setText(R.id.tv_time, item.getNiceDate());
        }
        if (!TextUtils.isEmpty(item.getAuthor())) {
            helper.setText(R.id.tv_author, item.getAuthor());
        }
        GlideUtil.loadImage(mContext, item.getEnvelopePic(), helper.getView(R.id.image_simple));
    }
}
