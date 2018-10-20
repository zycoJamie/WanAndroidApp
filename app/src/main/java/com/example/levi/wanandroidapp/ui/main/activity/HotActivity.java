package com.example.levi.wanandroidapp.ui.main.activity;

import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.base.activity.BaseRootActivity;
import com.example.levi.wanandroidapp.contract.main.HotContract;
import com.example.levi.wanandroidapp.data.main.HotBean;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.presenter.main.HotPresenter;
import com.example.levi.wanandroidapp.util.app.CommonUtil;
import com.example.levi.wanandroidapp.util.app.SkipUtil;
import com.example.levi.wanandroidapp.util.app.ToastUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;

/**
 * Author: Levi
 * CreateDate: 2018/10/19 13:53
 */
public class HotActivity extends BaseRootActivity<HotPresenter> implements HotContract.View {

    @BindView(R.id.tfl_hot_tag)
    TagFlowLayout mTagFlowLayout;
    @BindView(R.id.tb_common)
    Toolbar mToolbar;

    private List<HotBean> mList;

    @Override
    protected void initUI() {
        super.initUI();
        showLoading();
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.hot_title);
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    protected void initInject() {
        super.initInject();
        mActivityComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hot;
    }

    @Override
    protected void initData() {
        mPresenter.getHotWeb();
    }

    @Override
    public void getHotWebOk(List<HotBean> dataBean) {
        showNormal();
        mList = dataBean;
        mTagFlowLayout.setAdapter(new TagAdapter<HotBean>(dataBean) {
            @Override
            public View getView(FlowLayout parent, int position, HotBean itemData) {
                View view = getLayoutInflater().inflate(R.layout.item_hot_tag, parent, false);
                if (view instanceof TextView) {
                    int color = CommonUtil.randomColor();
                    ((TextView) view).setText(itemData.getName());
                    ((TextView) view).setTextColor(color);
                    GradientDrawable drawable = new GradientDrawable();
                    drawable.setShape(GradientDrawable.RECTANGLE);
                    drawable.setStroke((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()), color);
                    drawable.setCornerRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));
                    view.setBackgroundDrawable(drawable);
                }
                return view;
            }
        });
        mTagFlowLayout.setOnTagClickListener((view, position, parent) -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.ARTICLE_LINK, mList.get(position).getLink());
            bundle.putString(Constant.ARTICLE_TITLE, mList.get(position).getName());
            SkipUtil.overlay(mActivity, ArticleDetailsActivity.class, bundle);
            return false;
        });
        mTagFlowLayout.getAdapter().notifyDataChanged();
    }

    @Override
    public void getHotWebErr(String info) {
        showError();
        ToastUtil.show(mContext, info);
    }
}
