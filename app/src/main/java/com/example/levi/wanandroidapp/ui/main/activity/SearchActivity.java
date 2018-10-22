package com.example.levi.wanandroidapp.ui.main.activity;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.base.activity.BaseRootActivity;
import com.example.levi.wanandroidapp.contract.main.SearchContract;
import com.example.levi.wanandroidapp.data.main.SearchHotWordBean;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.presenter.main.SearchHotWordPresenter;
import com.example.levi.wanandroidapp.ui.main.adapter.SearchHistoryAdapter;
import com.example.levi.wanandroidapp.util.app.CommonUtil;
import com.example.levi.wanandroidapp.util.app.SkipUtil;
import com.example.levi.wanandroidapp.util.app.ToastUtil;
import com.example.levi.wanandroidapp.widget.CommonDialog;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: Levi
 * CreateDate: 2018/10/20 15:10
 */
public class SearchActivity extends BaseRootActivity<SearchHotWordPresenter> implements SearchContract.View, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.tb_common)
    Toolbar mToolbar;
    @BindView(R.id.tfl_hot_word)
    TagFlowLayout mFlowLayout;
    @BindView(R.id.tv_clear)
    TextView mClearTv;
    @BindView(R.id.rv_history)
    RecyclerView mRecyclerView;

    private List<SearchHotWordBean> mHotWordList;
    private List<String> mHistoryList;
    private SearchHistoryAdapter mAdapter;
    private CommonDialog mDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }


    @Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @OnClick(R.id.tv_clear)
    void click(View view) {
        switch (view.getId()) {
            case R.id.tv_clear: {
                if (mHistoryList.size() > 0) {
                    mDialog = new CommonDialog.Builder(mContext)
                            .setTitle(getString(R.string.search_dialog_clear_history))
                            .setContent(getString(R.string.search_dialog_clear_history_question))
                            .setPositiveClickListener(v -> {
                                mHistoryList.clear();
                                mAdapter.notifyDataSetChanged();
                                mPresenter.saveSearchHistory(mContext, mHistoryList);
                                mDialog.dismiss();
                            }).create();
                    mDialog.show();
                }
                break;
            }
        }
    }

    @Override
    protected void initInject() {
        super.initInject();
        mActivityComponent.inject(this);
    }

    /**
     * 初始化SearchView
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchView searchView = (SearchView) (menu.findItem(R.id.menu_search).getActionView());
        searchView.setQueryHint(getString(R.string.search_menu_query_hint));
        searchView.setIconified(false);
        searchView.onActionViewExpanded();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!mHistoryList.contains(query)) {
                    mHistoryList.add(query);
                    mPresenter.saveSearchHistory(mContext, mHistoryList);
                }
                getSearchResult(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void initData() {
        mHotWordList = new ArrayList<>();
        mHistoryList = new ArrayList<>();
        mPresenter.getSearchHotWord();
        mAdapter = new SearchHistoryAdapter(R.layout.item_search_history, mHistoryList);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mPresenter.readHistory(mContext, mHistoryList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getSearchHotOk(List<SearchHotWordBean> dataBean) {
        mHotWordList.clear();
        mHotWordList.addAll(dataBean);
        mAdapter.notifyDataSetChanged();
        initFlowLayout();
    }

    /**
     * 初始化TagFlowLayout
     */
    private void initFlowLayout() {
        TagAdapter<SearchHotWordBean> mTagAdapter = new TagAdapter<SearchHotWordBean>(mHotWordList) {
            @Override
            public View getView(FlowLayout parent, int position, SearchHotWordBean searchHotWordBean) {
                TextView textView = (TextView) getLayoutInflater().inflate(R.layout.item_hot_tag, parent, false);
                int color = CommonUtil.randomColor();
                textView.setText(searchHotWordBean.getName());
                textView.setTextColor(color);
                GradientDrawable drawable = new GradientDrawable();
                drawable.setShape(GradientDrawable.RECTANGLE);
                drawable.setStroke((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()), color);
                drawable.setCornerRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));
                textView.setBackgroundDrawable(drawable);
                return textView;
            }
        };
        mFlowLayout.setAdapter(mTagAdapter);
        mFlowLayout.setOnTagClickListener((view, position, parent) -> {
            String name = mHotWordList.get(position).getName();
            if (!mHistoryList.contains(name)) {
                mHistoryList.add(name);
                mPresenter.saveSearchHistory(mContext, mHistoryList);
            }
            getSearchResult(name);
            return false;
        });
    }

    private void getSearchResult(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.SEARCH_RESULT_TITLE, title);
        SkipUtil.overlay(mContext, SearchResultActivity.class);
    }

    @Override
    public void getSearchHotError(String info) {
        ToastUtil.show(mContext, info);
        showError();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        getSearchResult(mAdapter.getData().get(position));
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        mHistoryList.remove(position);
        mAdapter.notifyDataSetChanged();
        mPresenter.saveSearchHistory(mContext, mHistoryList);
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        CommonUtil.hideKeyBoard();
    }
}
