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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.levi.wanandroidapp.base.activity.BaseRootActivity;
import com.example.levi.wanandroidapp.base.presenter.BasePresenter;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.model.constant.EventConstant;
import com.example.levi.wanandroidapp.model.constant.MessageEvent;
import com.example.levi.wanandroidapp.ui.drawer.VideoActivity;
import com.example.levi.wanandroidapp.ui.knowledge.fragment.KnowledgeFragment;
import com.example.levi.wanandroidapp.ui.login.LoginActivity;
import com.example.levi.wanandroidapp.ui.main.activity.HotActivity;
import com.example.levi.wanandroidapp.ui.main.fragment.HomePageFragment;
import com.example.levi.wanandroidapp.ui.mine.fragment.MineFragment;
import com.example.levi.wanandroidapp.ui.project.fragment.ProjectFragment;
import com.example.levi.wanandroidapp.util.app.BottomNavigationHelper;
import com.example.levi.wanandroidapp.util.app.SharedPreferenceUtil;
import com.example.levi.wanandroidapp.util.app.SkipUtil;
import com.example.levi.wanandroidapp.util.app.ToastUtil;
import com.example.levi.wanandroidapp.util.glide.GlideUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
    private long mExitTime;

    @Override
    protected void initData() {
        initNavigationHeader();
        initNavigation();
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
        mFragments.add(KnowledgeFragment.getInstance());
        mFragments.add(ProjectFragment.getInstance());
        mFragments.add(MineFragment.getInstance());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @SuppressWarnings("all")
    private void initNavigationHeader() {
        mNameTv.setText((Boolean) (SharedPreferenceUtil.get(mActivity, Constant.ISLOGIN, false)) ?
                (String) SharedPreferenceUtil.get(mActivity, Constant.USERNAME, "") : getString(R.string.drawer_login));
        GlideUtil.loadCircleImage(mActivity, R.mipmap.user_avatar, mAvatarIv);
        mAvatarIv.setOnClickListener((v) -> SkipUtil.overlay(mActivity, LoginActivity.class));
    }

    /**
     * 初始化底部的BottomNavigation
     */
    private void initNavigation() {
        BottomNavigationHelper.disableShiftMode(mBottomNavigationView);
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.tab_main: {
                    mScroll2TopFb.setVisibility(View.VISIBLE);
                    selectFragment(0);
                    break;
                }
                case R.id.tab_knowledge_hierarchy: {
                    mScroll2TopFb.setVisibility(View.VISIBLE);
                    selectFragment(1);
                    break;
                }
                case R.id.tab_project: {
                    mScroll2TopFb.setVisibility(View.VISIBLE);
                    selectFragment(2);
                    break;
                }
                case R.id.tab_mine: {
                    mScroll2TopFb.setVisibility(View.GONE);
                    selectFragment(3);
                    break;
                }
            }
            return true;
        });
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

    @OnClick(R.id.fb_scroll_2_top)
    public void floatActionButtonClick(View view) {
        switch (view.getId()) {
            case R.id.fb_scroll_2_top: {
                scrollToTop();
                break;
            }
        }
    }

    /**
     * 发送滚到顶部事件
     */
    private void scrollToTop() {
        switch (mPresenter.getCurrentPage()) {
            case 0: {
                EventBus.getDefault().post(new MessageEvent(EventConstant.HOME_SCROLL_2_TOP, ""));
                break;
            }
            case 1: {
                EventBus.getDefault().post(new MessageEvent(EventConstant.KNOWLEDGE_SCROLL_2_TOP, ""));
                break;
            }
            case 2: {
                EventBus.getDefault().post(new MessageEvent(EventConstant.PROJECT_SCROLL_2_TOP, ""));
                break;
            }
        }
    }

    /**
     * 处理登陆、登出事件
     *
     * @param event
     */
    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        switch (event.getCode()) {
            case EventConstant.LOGIN_SUCCESS: {

            }
            case EventConstant.LOGIN_OUT_SUCCESS: {
                initNavigationHeader();
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_hot: {
                // TODO: 2018/10/4 menu function
                SkipUtil.overlay(mContext,HotActivity.class);
                break;
            }
            case R.id.menu_main_search: {

                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressedSupport() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtil.show(mActivity, getString(R.string.exit_app));
            mExitTime = System.currentTimeMillis();
        } else {
            SharedPreferenceUtil.put(mActivity, Constant.ISLOGIN, false);
            finish();
        }
    }
}
