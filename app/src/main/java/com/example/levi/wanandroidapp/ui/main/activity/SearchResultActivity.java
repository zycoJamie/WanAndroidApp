package com.example.levi.wanandroidapp.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.base.activity.BaseRootActivity;
import com.example.levi.wanandroidapp.contract.main.SearchResultContract;
import com.example.levi.wanandroidapp.data.main.HomePageArticleBean;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.presenter.main.SearchResultPresenter;
import com.example.levi.wanandroidapp.ui.knowledge.activity.KnowledgeClassifyActivity;
import com.example.levi.wanandroidapp.ui.login.LoginActivity;
import com.example.levi.wanandroidapp.ui.main.adapter.HomePageAdapter;
import com.example.levi.wanandroidapp.util.app.LogUtil;
import com.example.levi.wanandroidapp.util.app.SharedPreferenceUtil;
import com.example.levi.wanandroidapp.util.app.SkipUtil;
import com.example.levi.wanandroidapp.util.app.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author: Levi
 * CreateDate: 2018/10/22 15:59
 */
public class SearchResultActivity extends BaseRootActivity<SearchResultPresenter> implements SearchResultContract.View, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.tb_common)
    Toolbar mToolbar;
    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshSrl;
    @BindView(R.id.rv_content)
    RecyclerView mRecyclerView;

    private String key;
    private List<HomePageArticleBean.DatasBean> mList;
    private HomePageAdapter mAdapter;
    private int mClickPosition;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void initInject() {
        super.initInject();
        mActivityComponent.inject(this);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(mToolbar);
        getBundleData();
        if (key != null) {
            mToolbar.setTitle(key);
        }
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    private void getBundleData() {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        key = bundle.getString(Constant.SEARCH_RESULT_TITLE);
    }

    @Override
    protected void initUI() {
        super.initUI();
        showLoading();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    protected void initData() {
        setRefresh();
        mList = new ArrayList<>();
        mPresenter.getSearchResult(0, key);
        mAdapter = new HomePageAdapter(R.layout.item_homepage, mList);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 下拉刷新和上拉加载更多的回调监听
     */
    private void setRefresh() {
        mRefreshSrl.setOnRefreshListener(refreshLayout -> {
            mPresenter.autoRefresh(key);
            refreshLayout.finishRefresh(1000);
        });
        mRefreshSrl.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.loadMore(key);
            refreshLayout.finishLoadMore(1000);
        });
    }

    @Override
    public void reload() {
        super.reload();
        mPresenter.autoRefresh(key);
    }

    @Override
    public void getSearchResultOk(HomePageArticleBean searchResult, boolean isRefresh) {
        if (mAdapter == null) {
            return;
        }
        if (isRefresh) {
            if (searchResult.getDatas().size() == 0) {
                showEmpty();
                return;
            }
            mList.clear();
            mList.addAll(searchResult.getDatas());
            mAdapter.notifyDataSetChanged();
            showNormal();
        } else {
            if (searchResult.getDatas().size() > 0) {
                mAdapter.addData(searchResult.getDatas());
            } else {
                ToastUtil.show(mContext, getString(R.string.knowledge_no_more_data));
            }
        }
    }

    @Override
    public void getSearchResultErr(String info) {
        ToastUtil.show(mContext, info);
        showError();
    }

    @Override
    public void collectArticleOK(String info) {
        if (mAdapter != null && mAdapter.getData().size() > mClickPosition) {
            ToastUtil.show(mContext, getString(R.string.collect_article));
            mAdapter.getData().get(mClickPosition).setCollect(true);
            mAdapter.setData(mClickPosition, mAdapter.getData().get(mClickPosition));
        }
    }

    @Override
    public void collectArticleErr(String info) {
        ToastUtil.show(mContext, getString(R.string.please_you_must_login));
        SkipUtil.overlay(mActivity, LoginActivity.class);
    }

    @Override
    public void cancelCollectArticleOK(String info) {
        if (mAdapter != null && mAdapter.getData().size() > mClickPosition) {
            ToastUtil.show(mContext, getString(R.string.collect_cancel_article));
            mAdapter.getData().get(mClickPosition).setCollect(false);
            mAdapter.setData(mClickPosition, mAdapter.getData().get(mClickPosition));
        }
    }

    @Override
    public void cancelCollectArticleErr(String info) {
        ToastUtil.show(mContext, getString(R.string.please_you_must_login_2));
        SkipUtil.overlay(mActivity, LoginActivity.class);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.ARTICLE_TITLE, mAdapter.getData().get(position).getTitle());
        bundle.putString(Constant.ARTICLE_LINK, mAdapter.getData().get(position).getLink());
        bundle.putBoolean(Constant.ARTICLE_IS_COLLECT, mAdapter.getData().get(position).isCollect());
        bundle.putInt(Constant.ARTICLE_ID, mAdapter.getData().get(position).getId());
        Intent intent = new Intent(mActivity, ArticleDetailsActivity.class);
        intent.putExtras(bundle);
        /*ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity,view,mContext.getString(R.string.share_element_view));
        startActivity(intent,optionsCompat.toBundle());*/
        startActivity(intent);
    }

    @SuppressWarnings("all")
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        mClickPosition = position;
        switch (view.getId()) {
            case R.id.tv_type: {
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, view, mContext.getString(R.string.share_element_view));
                Intent intent = new Intent(mActivity, KnowledgeClassifyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean(Constant.HOMEPAGE_TAG, true);
                bundle.putInt(Constant.HOMEPAGE_CID, mAdapter.getData().get(position).getChapterId());
                bundle.putString(Constant.HOMEPAGE_CNAME, mAdapter.getData().get(position).getChapterName());
                bundle.putString(Constant.HOMEPAGE_SUPER_CNAME, mAdapter.getData().get(position).getSuperChapterName());
                intent.putExtras(bundle);
                /*startActivity(intent,optionsCompat.toBundle());*/
                startActivity(intent);
                break;
            }
            case R.id.iv_collect: {
                if ((Boolean) SharedPreferenceUtil.get(mContext, Constant.ISLOGIN, false)) {
                    if (mAdapter.getData().get(mClickPosition).isCollect()) {
                        mPresenter.cancelCollectArticle(mAdapter.getData().get(mClickPosition).getId());
                    } else {
                        mPresenter.collectArticle(mAdapter.getData().get(mClickPosition).getId());
                    }
                } else {
                    ToastUtil.show(mContext, getString(R.string.please_you_must_login));
                    SkipUtil.overlay(mActivity, LoginActivity.class);
                }
                break;
            }
        }
    }
}
