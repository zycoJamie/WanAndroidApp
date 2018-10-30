package com.example.levi.wanandroidapp.ui.drawer.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.base.activity.BaseActivity;
import com.flyco.tablayout.SlidingTabLayout;

import butterknife.BindView;

public class VideoActivity extends BaseActivity {

    @BindView(R.id.tb_common)
    Toolbar mToolbar;

    @BindView(R.id.tl_video)
    SlidingTabLayout mTabLayout;

    @BindView(R.id.vp_video)
    ViewPager mViewPager;


    @Override
    protected void initData() {

    }

    @Override
    protected void initUI() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video;
    }
}
