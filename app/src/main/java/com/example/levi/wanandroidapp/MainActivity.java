package com.example.levi.wanandroidapp;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.levi.wanandroidapp.base.activity.BaseRootActivity;
import com.example.levi.wanandroidapp.base.presenter.AbsPresenter;
import com.example.levi.wanandroidapp.base.presenter.BasePresenter;

import butterknife.BindView;

public class MainActivity extends BaseRootActivity implements NavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.bnv_view)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.fb_scroll_2_top)
    FloatingActionButton mScroll2TopFb;
    @BindView(R.id.tb_common)
    Toolbar mToolbar;
    @BindView(R.id.dl_container)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_left_view)
    NavigationView mLeftNav;

    private ImageView mAvatarIv;
    private TextView mNameTv;
    private BasePresenter mPresenter;

    @Override
    protected void initData() {

    }

    @Override
    protected void initUI() {
        View headerView=mLeftNav.inflateHeaderView(R.layout.nav_header_view);
        mAvatarIv=headerView.findViewById(R.id.iv_avatar);
        mNameTv=headerView.findViewById(R.id.tv_name);
        mPresenter=new BasePresenter();
        initFragment();
    }

    private void initFragment() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return true;
    }
}
