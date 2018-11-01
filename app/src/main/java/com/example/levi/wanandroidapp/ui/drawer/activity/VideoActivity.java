package com.example.levi.wanandroidapp.ui.drawer.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.base.activity.BaseActivity;
import com.example.levi.wanandroidapp.base.adapter.SimpleFragmentStateAdapter;
import com.example.levi.wanandroidapp.contract.drawer.VideoContract;
import com.example.levi.wanandroidapp.data.drawer.TypeTitle;
import com.example.levi.wanandroidapp.presenter.drawer.VideoPresenter;
import com.example.levi.wanandroidapp.util.app.LogUtil;
import com.example.levi.wanandroidapp.util.app.ToastUtil;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class VideoActivity extends BaseActivity<VideoPresenter> implements VideoContract.View {

    private static final String TAG = VideoActivity.class.getSimpleName();
    @BindView(R.id.tb_common)
    Toolbar mToolbar;

    @BindView(R.id.tl_video)
    SlidingTabLayout mTabLayout;

    @BindView(R.id.vp_video)
    ViewPager mViewPager;

    private String[] mTitlesArray;
    private List<String> mTitles;
    private List<Fragment> mFragments;
    private SimpleFragmentStateAdapter mAdapter;

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getString(R.string.video_title));
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    protected void initInject() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initData() {
        initPager();
        mPresenter.getLiveTitle();
    }

    private void initPager() {
        mAdapter = new SimpleFragmentStateAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    protected void initUI() {
        mTitles = new ArrayList<>();
        mFragments = new ArrayList<>();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    public void getLiveTitleOk(List<TypeTitle> typeTitles) {
        if (typeTitles != null && typeTitles.size() > 0) {
            for (int i = 0; i < 3; i++) {
                if (i == 1) {
                    continue;
                }
                mTitles.add(typeTitles.get(0).getChild_data().get(i).getCname());
                mFragments.add(new Fragment());
            }
            for (int i = 0; i < 4; i++) {
                if (i == 1 || i == 2) {
                    continue;
                }
                mTitles.add(typeTitles.get(1).getChild_data().get(i).getCname());
                mFragments.add(new Fragment());
            }
            for (int i = 0; i < 5; i++) {
                if (i == 1 || i == 4) {
                    mTitles.add(typeTitles.get(2).getChild_data().get(i).getCname());
                    mFragments.add(new Fragment());
                }
            }
        }
        initTabLayout();
    }

    private void initTabLayout() {
        mTitlesArray=mTitles.toArray(new String[mTitles.size()]);
        mTabLayout.setViewPager(mViewPager,mTitlesArray);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getLiveTitleErr(String info) {
        LogUtil.i(TAG,info);
        ToastUtil.show(mContext, info);
    }
}
