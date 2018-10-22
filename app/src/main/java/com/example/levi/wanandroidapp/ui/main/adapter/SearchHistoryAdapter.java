package com.example.levi.wanandroidapp.ui.main.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.levi.wanandroidapp.R;

import java.util.List;

/**
 * Author: Levi
 * CreateDate: 2018/10/22 14:54
 */
public class SearchHistoryAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public SearchHistoryAdapter(int resId, List<String> data) {
        super(resId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_history, item);
        helper.addOnClickListener(R.id.iv_close);
    }
}
