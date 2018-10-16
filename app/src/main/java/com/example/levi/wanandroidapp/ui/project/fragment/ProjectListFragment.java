package com.example.levi.wanandroidapp.ui.project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.base.fragment.BaseFragment;
import com.example.levi.wanandroidapp.base.fragment.BaseRootFragment;
import com.example.levi.wanandroidapp.contract.project.ProjectListContract;
import com.example.levi.wanandroidapp.data.project.ProjectListBean;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.model.constant.EventConstant;
import com.example.levi.wanandroidapp.model.constant.MessageEvent;
import com.example.levi.wanandroidapp.presenter.project.ProjectListPresenter;
import com.example.levi.wanandroidapp.ui.main.activity.ArticleDetailsActivity;
import com.example.levi.wanandroidapp.ui.project.adapter.ProjectListAdapter;
import com.example.levi.wanandroidapp.util.app.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author: Levi
 * CreateDate: 2018/10/16 14:07
 */
public class ProjectListFragment extends BaseRootFragment<ProjectListPresenter> implements ProjectListContract.View, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshSrl;
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;

    private int cid;
    private List<ProjectListBean.DatasBean> mList;
    private ProjectListAdapter mAdapter;

    public static ProjectListFragment getInstance(int cid) {
        ProjectListFragment fragment = new ProjectListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.PROJECT_CID, cid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initInject() {
        super.initInject();
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initData() {
        setRefresh();
        mList = new ArrayList<>();
        if (getArguments() != null) {
            cid = getArguments().getInt(Constant.PROJECT_CID);
            mPresenter.getProjectList(1, cid);
        }
        mAdapter = new ProjectListAdapter(R.layout.item_project, mList);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initUI() {
        super.initUI();
        showLoading();
    }

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_project_list;
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
    public void getProjectListOk(ProjectListBean projectListBean, boolean isRefresh) {
        if (mAdapter == null) {
            return;
        }
        if (isRefresh) {
            mList.clear();
            mList.addAll(projectListBean.getDatas());
            mAdapter.notifyDataSetChanged();
        } else {
            if (projectListBean.getDatas().size() > 0) {
                mAdapter.addData(projectListBean.getDatas());
            } else {
                ToastUtil.show(mContext, getString(R.string.knowledge_no_more_data));
            }
        }
        showNormal();
    }

    @Override
    public void getProjectListErr(String info) {
        showError();
    }


    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        switch (event.getCode()) {
            case EventConstant.REFRESH_PROJECT_LIST: {
                reload();
                break;
            }
        }
    }

    @Override
    public void reload() {
        super.reload();
        mPresenter.autoRefresh();
    }

    @SuppressWarnings("all")
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.ARTICLE_TITLE, mAdapter.getData().get(position).getTitle());
        bundle.putString(Constant.ARTICLE_LINK, mAdapter.getData().get(position).getLink());
        bundle.putInt(Constant.ARTICLE_ID, mAdapter.getData().get(position).getId());
        bundle.putBoolean(Constant.ARTICLE_IS_COLLECT, mAdapter.getData().get(position).isCollect());
        /*ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mActivity, view, getString(R.string.share_element_view));
        startActivity(new Intent(mActivity, ArticleDetailsActivity.class).putExtras(bundle), options.toBundle());*/
        startActivity(new Intent(mActivity, ArticleDetailsActivity.class).putExtras(bundle));

    }
}
