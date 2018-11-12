package com.example.levi.wanandroidapp.ui.drawer.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.base.fragment.BaseRootFragment;
import com.example.levi.wanandroidapp.contract.drawer.LiveListContract;
import com.example.levi.wanandroidapp.data.drawer.LiveList;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.presenter.drawer.LiveListPresenter;
import com.example.levi.wanandroidapp.ui.drawer.activity.PandaLiveRoomActivity;
import com.example.levi.wanandroidapp.ui.drawer.adapter.LiveListAdapter;
import com.example.levi.wanandroidapp.util.app.LogUtil;
import com.example.levi.wanandroidapp.util.app.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LiveListFragment extends BaseRootFragment<LiveListPresenter> implements LiveListContract.View, BaseQuickAdapter.OnItemClickListener {

    private static final String TAG = LiveListFragment.class.getSimpleName();
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.normal_view)
    SmartRefreshLayout mSmartRefreshLayout;

    private String cate;
    private List<LiveList.ItemsBeanX> mLists;
    private LiveListAdapter mAdapter;

    public static LiveListFragment getInstance(String cate) {
        LiveListFragment fragment = new LiveListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.CATE, cate);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initInject() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initUI() {
        super.initUI();
        cate = getArguments().getString(Constant.CATE);
        showLoading();
        GridLayoutManager layoutManager = new GridLayoutManager(mActivity, 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildLayoutPosition(view) % 2 == 1) {
                    outRect.left = 10;
                    outRect.right = 20;
                } else {
                    outRect.left = 20;
                    outRect.right = 10;
                }
                if (parent.getChildLayoutPosition(view) == 0 || parent.getChildLayoutPosition(view) == 1) {
                    outRect.top = 30;
                }
                outRect.bottom = 30;
            }
        };
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    protected void initData() {
        setRefresh();
        mLists = new ArrayList<>();
        mPresenter.getLiveList(cate);
        mAdapter = new LiveListAdapter(R.layout.item_live_list, mLists);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_live_list;
    }

    private void setRefresh() {
        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.autoRefresh(cate);
            refreshLayout.finishRefresh(1000);
        });
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.loadMore(cate);
            refreshLayout.finishLoadMore(1000);
        });
    }

    @Override
    public void reload() {
        showLoading();
        mPresenter.autoRefresh(cate);
    }

    @Override
    public void getLiveListOk(LiveList list, boolean isRefresh) {
        if (mAdapter == null) {
            return;
        }
        if (isRefresh) {
            LogUtil.i(TAG, "data size:" + list.getItems().size());
            mLists.clear();
            List<LiveList.ItemsBeanX> removeItems = new ArrayList<>();
            for (int i = 0; i < list.getItems().size(); i++) {
                if (list.getItems().get(i).getUserinfo() == null) {
                    removeItems.add(list.getItems().get(i));
                }
            }
            list.getItems().removeAll(removeItems);
            mLists.addAll(list.getItems());
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.addData(list.getItems());
        }
        showNormal();
    }

    @Override
    public void getLiveListErr(String info) {
        ToastUtil.show(mContext, info);
        showError();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(getActivity(), PandaLiveRoomActivity.class);
        int rid=mAdapter.getData().get(position).getUserinfo().getRid();
        LogUtil.i(TAG,"rid: "+rid);
        intent.putExtra(Constant.ROOM, mAdapter.getData().get(position));
        startActivity(intent);
    }
}
