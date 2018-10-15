package com.example.levi.wanandroidapp.ui.knowledge.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.base.activity.BaseActivity;
import com.example.levi.wanandroidapp.base.adapter.SimpleFragmentStateAdapter;
import com.example.levi.wanandroidapp.data.knowledge.KnowledgeListBean;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.model.constant.EventConstant;
import com.example.levi.wanandroidapp.model.constant.MessageEvent;
import com.example.levi.wanandroidapp.ui.knowledge.fragment.KnowledgeListFragment;
import com.example.levi.wanandroidapp.util.app.LogUtil;
import com.flyco.tablayout.SlidingTabLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: Levi
 * CreateDate: 2018/10/9 12:43
 */
public class KnowledgeClassifyActivity extends BaseActivity {

    @BindView(R.id.tb_common)
    Toolbar mToolbar;
    @BindView(R.id.tl_knowledge_classify)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.vp_knowledge_classify)
    ViewPager mViewPager;
    @BindView(R.id.fb_scroll_2_top)
    FloatingActionButton mScroll2TopFab;

    private List<String> mTitles;
    private List<Fragment> mFragments;
    private SimpleFragmentStateAdapter mAdapter;

    @Override
    protected void initUI() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_knowledge_classify;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    protected void initData() {
        mTitles = new ArrayList<>();
        mFragments = new ArrayList<>();
        mAdapter = new SimpleFragmentStateAdapter(getSupportFragmentManager(), mFragments);
        if (getIntent().getBooleanExtra(Constant.HOMEPAGE_TAG, false)) {
            getHomepageBundleData();
        } else {
            getKnowledgeBundleData();
        }
    }

    /**
     * 来自HomePageFragment type tag
     */
    private void getHomepageBundleData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int cId = bundle.getInt(Constant.HOMEPAGE_CID);
            String cName = bundle.getString(Constant.HOMEPAGE_CNAME);
            String superName = bundle.getString(Constant.HOMEPAGE_SUPER_CNAME);
            mFragments.clear();
            mToolbar.setTitle(superName);
            mTitles.clear();
            mTitles.add(cName);
            mFragments.add(KnowledgeListFragment.getInstance(cId));
        }
        initTabAndPaper(mTitles.toArray(new String[mTitles.size()]));
    }

    /**
     * 来自Knowledge model
     */
    @TargetApi(24)
    private void getKnowledgeBundleData() {
        KnowledgeListBean knowledgeListBean = (KnowledgeListBean) getIntent().getSerializableExtra(Constant.KNOWLEDGE_FIRST_TYPE);
        if (knowledgeListBean != null) {
            mFragments.clear();
            mTitles.clear();
            mToolbar.setTitle(knowledgeListBean.getName());
            LogUtil.i(knowledgeListBean.getChildren().size() + "");
            if (Build.VERSION_CODES.N > Build.VERSION.SDK_INT) {
                LogUtil.i("currentSDK: " + Build.VERSION.SDK_INT);
                for (KnowledgeListBean.ChildrenBean childrenBean : knowledgeListBean.getChildren()) {
                    mTitles.add(childrenBean.getName());
                    mFragments.add(KnowledgeListFragment.getInstance(childrenBean.getId()));
                }
            } else {
                LogUtil.i("currentSDK: " + Build.VERSION.SDK_INT);
                knowledgeListBean.getChildren().forEach(childrenBean -> {
                    mTitles.add(childrenBean.getName());
                    mFragments.add(KnowledgeListFragment.getInstance(childrenBean.getId()));
                });
            }
        }
        initTabAndPaper(mTitles.toArray(new String[mTitles.size()]));
    }

    private void initTabAndPaper(String[] titles) {
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setViewPager(mViewPager, titles);
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.fb_scroll_2_top)
    void click(View view) {
        switch (view.getId()) {
            case R.id.fb_scroll_2_top: {
                EventBus.getDefault().post(new MessageEvent(EventConstant.KNOWLEDGE_LIST_SCROLL_2_TOP, ""));
                break;
            }
        }
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }
}
