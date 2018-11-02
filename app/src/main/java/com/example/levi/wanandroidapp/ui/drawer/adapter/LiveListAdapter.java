package com.example.levi.wanandroidapp.ui.drawer.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.base.app.MyApplication;
import com.example.levi.wanandroidapp.data.drawer.LiveList;
import com.example.levi.wanandroidapp.util.app.CommonUtil;
import com.example.levi.wanandroidapp.util.glide.GlideUtil;

import java.util.List;

public class LiveListAdapter extends BaseQuickAdapter<LiveList.ItemsBeanX, BaseViewHolder> {

    public LiveListAdapter(int resId, List<LiveList.ItemsBeanX> data) {
        super(resId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LiveList.ItemsBeanX item) {
        if (item.getUserinfo() != null) {
            helper.setText(R.id.tv_nickname, item.getUserinfo().getNickName());
            helper.setText(R.id.tv_person_num, CommonUtil.getOnlineNum(Integer.valueOf(item.getPerson_num())));
            helper.setText(R.id.tv_room_name, item.getName());
            GlideUtil.loadImage(MyApplication.getInstance(), item.getImg(), helper.getView(R.id.image_preview));
        }
    }

}
