package com.example.levi.wanandroidapp.ui.mine.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.base.activity.BaseRootActivity;
import com.example.levi.wanandroidapp.contract.collection.CollectionContract;
import com.example.levi.wanandroidapp.data.collection.CollectionListBean;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.presenter.collection.CollectionPresenter;
import com.example.levi.wanandroidapp.ui.main.activity.ArticleDetailsActivity;
import com.example.levi.wanandroidapp.ui.mine.adapter.CollectionAdapter;
import com.example.levi.wanandroidapp.util.app.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author: Levi
 * CreateDate: 2018/10/17 17:01
 */
public class CollectionListActivity extends BaseRootActivity<CollectionPresenter> implements CollectionContract.View,
        BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshSrl;
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.tb_common)
    Toolbar mToolBar;

    private List<CollectionListBean.DatasBean> mList;
    private CollectionAdapter mAdapter;
    private int mClickPosition;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection_list;
    }

    @Override
    protected void initInject() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(mToolBar);
        mToolBar.setTitle(getString(R.string.collection_tool_bar_title));
        mToolBar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    protected void initUI() {
        super.initUI();
        showLoading();
    }

    @Override
    protected void initData() {
        setRefresh();
        mList = new ArrayList<>();
        mPresenter.getCollectList(0);
        mAdapter = new CollectionAdapter(R.layout.item_homepage, mList);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void getCollectListOk(CollectionListBean dataBean, boolean isRefresh) {
        if (mAdapter == null) {
            return;
        }
        if (isRefresh) {
            mList.clear();
            mList.addAll(dataBean.getDatas());
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.addData(dataBean.getDatas());
        }
        showNormal();
    }

    @Override
    public void getCollectListErr(String info) {
        showError();
    }

    @Override
    public void cancelCollectOk() {
        ToastUtil.show(mContext, getString(R.string.collect_cancel_article));
        mAdapter.remove(mClickPosition);
    }

    @Override
    public void cancelCollectErr(String info) {
        ToastUtil.show(mContext, info);
    }

    @Override
    public void reload() {
        showLoading();
        mPresenter.getCollectList(0);
    }

    /**
     * 下拉刷新和上拉加载更多的回调监听
     */
    private void setRefresh() {
        mRefreshSrl.setOnRefreshListener(refreshLayout -> {
            mPresenter.autoRefresh();
            refreshLayout.finishRefresh(1000);
        });
        mRefreshSrl.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.loadMore();
            refreshLayout.finishLoadMore(1000);
        });
    }

    @SuppressWarnings("all")
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.ARTICLE_TITLE, mAdapter.getData().get(position).getTitle());
        bundle.putString(Constant.ARTICLE_LINK, mAdapter.getData().get(position).getLink());
        bundle.putInt(Constant.ARTICLE_ID, mAdapter.getData().get(position).getId());
        bundle.putBoolean(Constant.ARTICLE_IS_COLLECT, true);
        /*ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mActivity, view, getString(R.string.share_element_view));
        startActivity(new Intent(mActivity, ArticleDetailsActivity.class).putExtras(bundle), options.toBundle());*/
        startActivity(new Intent(mActivity, ArticleDetailsActivity.class).putExtras(bundle));
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        mClickPosition = position;
        mPresenter.cancelCollect(mAdapter.getData().get(position).getId());
    }
}
