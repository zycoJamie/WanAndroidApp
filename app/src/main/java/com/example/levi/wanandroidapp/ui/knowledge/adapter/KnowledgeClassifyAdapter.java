package com.example.levi.wanandroidapp.ui.knowledge.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.data.knowledge.KnowledgeClassifyListBean;

import java.util.List;

/**
 * Author: Levi
 * CreateDate: 2018/10/15 10:33
 */
public class KnowledgeClassifyAdapter extends BaseQuickAdapter<KnowledgeClassifyListBean.DatasBean, BaseViewHolder> {

    public KnowledgeClassifyAdapter(int layoutResId, List<KnowledgeClassifyListBean.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KnowledgeClassifyListBean.DatasBean item) {
        if (!TextUtils.isEmpty(item.getTitle())) {
            helper.setText(R.id.tv_content, item.getTitle());
        }
        if (!TextUtils.isEmpty(item.getAuthor())) {
            helper.setText(R.id.tv_author, item.getAuthor());
        }
        if (!TextUtils.isEmpty(item.getNiceDate())) {
            helper.setText(R.id.tv_time, item.getNiceDate());
        }
        if (!TextUtils.isEmpty(item.getChapterName())) {
            String classifyName = item.getSuperChapterName() + "/" + item.getChapterName();
            helper.setText(R.id.tv_type, classifyName);
        }
        helper.addOnClickListener(R.id.iv_collect);
        helper.setImageResource(R.id.iv_collect, item.isCollect() ? R.drawable.ic_collect : R.drawable.ic_no_collect);
    }
}
