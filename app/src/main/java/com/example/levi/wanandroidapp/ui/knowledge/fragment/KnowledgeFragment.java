package com.example.levi.wanandroidapp.ui.knowledge.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.base.fragment.BaseRootFragment;
import com.example.levi.wanandroidapp.contract.knowledge.KnowledgeContract;
import com.example.levi.wanandroidapp.data.knowledge.KnowledgeListBean;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.model.constant.EventConstant;
import com.example.levi.wanandroidapp.model.constant.MessageEvent;
import com.example.levi.wanandroidapp.presenter.knowledge.KnowledgePresenter;
import com.example.levi.wanandroidapp.ui.knowledge.activity.KnowledgeClassifyActivity;
import com.example.levi.wanandroidapp.ui.knowledge.adapter.KnowledgeAdapter;
import com.example.levi.wanandroidapp.util.app.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author: Levi
 * CreateDate: 2018/10/15 15:32
 */
public class KnowledgeFragment extends BaseRootFragment<KnowledgePresenter> implements KnowledgeContract.View, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshSrl;
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;

    private List<KnowledgeListBean> mList;
    private KnowledgeAdapter mAdapter;

    public static KnowledgeFragment getInstance() {
        return new KnowledgeFragment();
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
        mPresenter.autoRefresh();
        mAdapter = new KnowledgeAdapter(R.layout.item_knowledge, mList);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_knowledge;
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
            mPresenter.autoRefresh();
            refreshLayout.finishRefresh(1000);
        });
        mRefreshSrl.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.loadMore();
            refreshLayout.finishLoadMore(1000);
        });
    }

    @Override
    public void reload() {
        super.reload();
        showLoading();
        mPresenter.autoRefresh();
    }

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        switch (event.getCode()) {
            case EventConstant.KNOWLEDGE_SCROLL_2_TOP: {
                mRecyclerView.smoothScrollToPosition(0);
                break;
            }
        }
    }

    @Override
    public void getKnowledgeListOk(List<KnowledgeListBean> dataBean, boolean isRefresh) {
        if (mAdapter == null) {
            return;
        }
        if (isRefresh) {
            mList.clear();
            mList.addAll(dataBean);
            mAdapter.notifyDataSetChanged();
        } else {
            ToastUtil.show(mContext, getString(R.string.knowledge_no_more_data));
        }
        showNormal();
    }

    @Override
    public void getKnowledgeListErr(String info) {
        showError();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        /*ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity,view,getString(R.string.share_element_view));*/
        Intent intent = new Intent(mActivity, KnowledgeClassifyActivity.class);
        intent.putExtra(Constant.KNOWLEDGE_FIRST_TYPE, mAdapter.getData().get(position));
        /*startActivity(intent,optionsCompat.toBundle());*/
        startActivity(intent);
    }
}
