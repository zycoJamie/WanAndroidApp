package com.example.levi.wanandroidapp;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.levi.wanandroidapp.base.activity.BaseRootActivity;
import com.example.levi.wanandroidapp.base.presenter.AbsPresenter;
import com.example.levi.wanandroidapp.base.presenter.BasePresenter;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.ui.drawer.VideoActivity;
import com.example.levi.wanandroidapp.ui.main.fragment.HomePageFragment;
import com.example.levi.wanandroidapp.util.app.SharedPreferenceUtil;
import com.example.levi.wanandroidapp.util.app.SkipUtil;
import com.example.levi.wanandroidapp.util.glide.GlideUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseRootActivity implements NavigationView.OnNavigationItemSelectedListener {

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
    private List<Fragment> mFragments;
    private int mLastIndex; //上一个fragment的index

    @Override
    protected void initData() {
        initNavigationHeader();
    }

    @Override
    protected void initUI() {
        View headerView = mLeftNav.inflateHeaderView(R.layout.nav_header_view);
        mAvatarIv = headerView.findViewById(R.id.iv_avatar);
        mNameTv = headerView.findViewById(R.id.tv_name);
        mPresenter = new BasePresenter();
        initFragment();
        selectFragment(0);
        mLeftNav.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
            toggle.syncState();
            mDrawerLayout.addDrawerListener(toggle);
        }
    }

    private void selectFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = mFragments.get(position);
        Fragment lastFragment = mFragments.get(mLastIndex);
        transaction.hide(lastFragment);
        if (!currentFragment.isAdded()) {
            transaction.add(R.id.frame_layout, currentFragment);
        }
        transaction.show(currentFragment);
        transaction.commitAllowingStateLoss();
        mLastIndex = position;
        mPresenter.setCurrentPage(position);
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(HomePageFragment.getInstance());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @SuppressWarnings("all")
    private void initNavigationHeader() {
        mNameTv.setText((Boolean) (SharedPreferenceUtil.get(mActivity, Constant.ISLOGIN, false)) ?
                (String) SharedPreferenceUtil.get(mActivity, Constant.USERNAME, "") : getString(R.string.drawer_login));
        GlideUtil.loadCircleImage(mActivity,R.mipmap.user_avatar,mAvatarIv);
    }

    /**
     * 左侧抽屉菜单的子项单击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_item_video: {
                mDrawerLayout.closeDrawer(Gravity.START);
                mDrawerLayout.postDelayed(() -> SkipUtil.overlay(mActivity, VideoActivity.class), 400);
                break;
            }
        }
        return true;
    }
}
