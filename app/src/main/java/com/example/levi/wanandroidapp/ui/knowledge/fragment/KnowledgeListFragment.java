package com.example.levi.wanandroidapp.ui.knowledge.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.base.fragment.BaseRootFragment;
import com.example.levi.wanandroidapp.contract.knowledge.KnowledgeClassifyContract;
import com.example.levi.wanandroidapp.data.knowledge.KnowledgeClassifyListBean;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.model.constant.EventConstant;
import com.example.levi.wanandroidapp.model.constant.MessageEvent;
import com.example.levi.wanandroidapp.presenter.knowledge.KnowledgeClassifyPresenter;
import com.example.levi.wanandroidapp.ui.knowledge.adapter.KnowledgeClassifyAdapter;
import com.example.levi.wanandroidapp.ui.login.LoginActivity;
import com.example.levi.wanandroidapp.ui.main.activity.ArticleDetailsActivity;
import com.example.levi.wanandroidapp.util.app.SharedPreferenceUtil;
import com.example.levi.wanandroidapp.util.app.SkipUtil;
import com.example.levi.wanandroidapp.util.app.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author: Levi
 * CreateDate: 2018/10/13 18:06
 */
public class KnowledgeListFragment extends BaseRootFragment<KnowledgeClassifyPresenter> implements KnowledgeClassifyContract.View, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshSrl;
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;

    private int cid;
    private List<KnowledgeClassifyListBean.DatasBean> mList;
    private KnowledgeClassifyAdapter mAdapter;
    private int mClickPosition;

    public static KnowledgeListFragment getInstance(int cid) {
        KnowledgeListFragment knowledgeListFragment = new KnowledgeListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.KNOWLEDGE_CID, cid);
        knowledgeListFragment.setArguments(bundle);
        return knowledgeListFragment;
    }

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_knowledge_list;
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
        if (getArguments() != null) {
            cid = getArguments().getInt(Constant.KNOWLEDGE_CID);
            mPresenter.getKnowledgeClassifyList(0, cid);
        }
        mAdapter = new KnowledgeClassifyAdapter(R.layout.item_homepage, mList);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initInject() {
        super.initInject();
        mFragmentComponent.inject(this);
    }

    /**
     * 下拉刷新和上拉加载更多的回调监听
     */
    private void setRefresh() {
        mRefreshSrl.setOnRefreshListener(refreshLayout -> {
            mPresenter.setCid(cid);
            mPresenter.autoRefresh();
            refreshLayout.finishRefresh(1000);
        });
        mRefreshSrl.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.setCid(cid);
            mPresenter.loadMore();
            refreshLayout.finishLoadMore(1000);
        });
    }

    @Override
    public void reload() {
        showLoading();
        mPresenter.autoRefresh();
    }

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        switch(event.getCode()){
            case EventConstant.KNOWLEDGE_LIST_SCROLL_2_TOP:{
                mRecyclerView.smoothScrollToPosition(0);
                break;
            }
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        mClickPosition = position;
        switch (view.getId()) {
            case R.id.iv_collect: {
                if ((boolean) SharedPreferenceUtil.get(mContext, Constant.ISLOGIN, false)) {
                    if (mList.get(position).isCollect()) {
                        mPresenter.cancelCollectArticle(mList.get(position).getId());
                    } else {
                        mPresenter.collectArticle(mList.get(position).getId());
                    }
                } else {
                    ToastUtil.show(mContext, getString(R.string.please_you_must_login));
                    SkipUtil.overlay(mActivity, LoginActivity.class);
                }
                break;
            }
        }
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

    @Override
    public void getKnowledgeClassifyListOk(KnowledgeClassifyListBean knowledgeClassifyListBean, boolean isRefresh) {
        if (mAdapter == null) {
            return;
        }
        if (isRefresh) {
            mList.clear();
            mList.addAll(knowledgeClassifyListBean.getDatas());
            mAdapter.notifyDataSetChanged();
        } else {
            if (knowledgeClassifyListBean.getDatas().size() > 0) {
                mAdapter.addData(knowledgeClassifyListBean.getDatas());
            } else {
                ToastUtil.show(mContext, getString(R.string.knowledge_no_more_data));
            }
        }
        showNormal();
    }

    @Override
    public void getKnowledgeClassifyListErr(String info) {
        ToastUtil.show(mContext, info);
        showError();
    }

    @Override
    public void collectArticleOK(String info) {
        if (mAdapter != null && mAdapter.getData().size() > mClickPosition) {
            ToastUtil.show(mContext, getString(R.string.collect_article));
            mList.get(mClickPosition).setCollect(true);
            mAdapter.setData(mClickPosition, mList.get(mClickPosition));
        }
    }

    @Override
    public void collectArticleErr(String info) {
        ToastUtil.show(mContext, getString(R.string.please_you_must_login));
        SkipUtil.overlay(mContext, LoginActivity.class);
    }

    @Override
    public void cancelCollectArticleOK(String info) {
        if (mAdapter != null && mAdapter.getData().size() > mClickPosition) {
            ToastUtil.show(mContext, getString(R.string.collect_cancel_article));
            mList.get(mClickPosition).setCollect(false);
            mAdapter.setData(mClickPosition, mList.get(mClickPosition));
        }
    }

    @Override
    public void cancelCollectArticleErr(String info) {
        ToastUtil.show(mContext, getString(R.string.please_you_must_login_2));
        SkipUtil.overlay(mContext, LoginActivity.class);
    }
}
