package com.example.levi.wanandroidapp.ui.knowledge.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.base.activity.BaseActivity;
import com.example.levi.wanandroidapp.base.adapter.SimpleFragmentStateAdapter;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
        mTitles=new ArrayList<>();
        mFragments=new ArrayList<>();
        mAdapter=new SimpleFragmentStateAdapter(getSupportFragmentManager(),mFragments);
        if(getIntent().getBooleanExtra(Constant.HOMEPAGE_TAG,false)){
            getHomepageBundleData();
        }else{
            getKnowledgeBundleData();
        }
    }

    /**
     * 来自HomePageFragment type tag
     */
    private void getHomepageBundleData() {
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            int cId=bundle.getInt(Constant.HOMEPAGE_CID);
            String cName=bundle.getString(Constant.HOMEPAGE_CNAME);
            String superNamer=bundle.getString(Constant.HOMEPAGE_SUPER_CNAME);
            mFragments.clear();
            mToolbar.setTitle(superNamer);

        }
    }

    private void getKnowledgeBundleData() {
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }
}
