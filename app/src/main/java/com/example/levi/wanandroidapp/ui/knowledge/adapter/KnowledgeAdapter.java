package com.example.levi.wanandroidapp.ui.knowledge.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.data.knowledge.KnowledgeListBean;

import java.util.List;

/**
 * Author: Levi
 * CreateDate: 2018/10/15 16:03
 */
public class KnowledgeAdapter extends BaseQuickAdapter<KnowledgeListBean, BaseViewHolder> {

    public KnowledgeAdapter(int resId, List<KnowledgeListBean> data) {
        super(resId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KnowledgeListBean item) {
        helper.setText(R.id.tv_knowledge_title, item.getName());
        StringBuilder stringBuilder = new StringBuilder();
        for (KnowledgeListBean.ChildrenBean childrenBean : item.getChildren()) {
            stringBuilder.append(childrenBean.getName().concat("    "));
        }
        helper.setText(R.id.tv_knowledge_content, stringBuilder.toString());
        stringBuilder.setLength(0);
    }
}
