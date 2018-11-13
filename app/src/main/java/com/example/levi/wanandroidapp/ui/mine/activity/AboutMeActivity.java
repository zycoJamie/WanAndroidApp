package com.example.levi.wanandroidapp.ui.mine.activity;


import android.animation.ValueAnimator;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.base.activity.BaseActivity;
import com.example.levi.wanandroidapp.util.app.LogUtil;
import com.example.levi.wanandroidapp.widget.ElasticOutInterpolator;
import com.scwang.smartrefresh.header.FlyRefreshHeader;
import com.scwang.smartrefresh.header.flyrefresh.FlyView;
import com.scwang.smartrefresh.header.flyrefresh.MountainSceneView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import butterknife.BindView;


/**
 * Author: Levi
 * CreateDate: 2018/10/18 9:30
 */
public class AboutMeActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {
    private static final String TAG = AboutMeActivity.class.getSimpleName();
    @BindView(R.id.msv_bg)
    MountainSceneView mMountainSceneView;
    @BindView(R.id.tb_common)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_tool_bar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.frh_header)
    FlyRefreshHeader mFlyRefreshHeader;
    @BindView(R.id.srl_about_me)
    SmartRefreshLayout mRefreshSrl;
    @BindView(R.id.fab_airplane)
    FloatingActionButton mAirplaneFab;
    @BindView(R.id.fly_view)
    FlyView mFlyView;
    @BindView(R.id.nsv_content)
    NestedScrollView mScrollView;
    @BindView(R.id.tv_content)
    TextView mContentTv;
    @BindView(R.id.tv_version)
    TextView mVersionTv;
    private View.OnClickListener mThemeListener;
    private boolean isExpand = true;

    @Override
    protected void initUI() {

    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_me;
    }

    @Override
    protected void initData() {
        showContent();
        setSmartRefreshLayout();
        mRefreshSrl.autoRefresh();
        mAirplaneFab.setOnClickListener(v -> mRefreshSrl.autoRefresh());
        mAppBarLayout.addOnOffsetChangedListener(this);
    }

    private void setSmartRefreshLayout() {
        mFlyRefreshHeader.setUp(mMountainSceneView, mFlyView);
        mRefreshSrl.setReboundInterpolator(new ElasticOutInterpolator());
        mRefreshSrl.setReboundDuration(800);
        mRefreshSrl.setOnRefreshListener(refreshLayout -> {
            updateTheme();
            refreshLayout.finishRefresh(1000);
        });
    }

    /**
     * 开始刷新时，更换颜色
     */
    private void updateTheme() {
        if (mThemeListener == null) {
            mThemeListener = new View.OnClickListener() {
                int index = 0;
                int[] ids = new int[]{
                        android.R.color.holo_red_light,
                        android.R.color.holo_green_light,
                        android.R.color.holo_purple
                };

                @Override
                public void onClick(View v) {
                    int color = getResources().getColor(ids[index % ids.length]);
                    mRefreshSrl.setPrimaryColors(color);
                    mAirplaneFab.setBackgroundTintList(ColorStateList.valueOf(color));
                    index++;
                }
            };
        }
        mThemeListener.onClick(null);
    }

    private void showContent() {
        mContentTv.setText(Html.fromHtml(getString(R.string.about_me_content)));
        mContentTv.setMovementMethod(LinkMovementMethod.getInstance());
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stringBuilder.append(getString(R.string.app_name))
                    .append(" V")
                    .append(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
            mVersionTv.setText(stringBuilder.toString());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据appBarLayout偏移的比例，缩放floatActionButton，以及动态设置scrollView的paddingTop
     *
     * @param appBarLayout
     * @param verticalOffset 当appBarLayout所在高度小于scrollRange时，为负值
     */
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int scrollRange = appBarLayout.getTotalScrollRange();
        double fraction = ((double) (scrollRange + verticalOffset)) / scrollRange;
        double minFraction = 0.3;
        double maxFraction = 0.8;
        if (mScrollView == null || mAirplaneFab == null || mFlyView == null || mMountainSceneView == null || mFlyRefreshHeader == null) {
            return;
        }
        if (fraction <= minFraction && isExpand) {
            isExpand = false;
            mAirplaneFab.animate().scaleX(0).scaleY(0);
            mFlyView.animate().scaleX(0).scaleY(0);
            ValueAnimator valueAnimator = ValueAnimator.ofInt(mScrollView.getPaddingTop(), 0);
            valueAnimator.setDuration(300)
                    .addUpdateListener(animation -> mScrollView.setPadding(0, (Integer) animation.getAnimatedValue(), 0, 0));
            valueAnimator.start();
        }
        if (fraction >= maxFraction && !isExpand) {
            isExpand = true;
            mAirplaneFab.animate().scaleX(1).scaleY(1);
            mFlyView.animate().scaleX(1).scaleY(1);
            ValueAnimator valueAnimator = ValueAnimator.ofInt(0, DensityUtil.dp2px(25));
            valueAnimator.setDuration(300)
                    .addUpdateListener(animation -> mScrollView.setPadding(0, (Integer) animation.getAnimatedValue(), 0, 0));
            valueAnimator.start();
        }
    }
}
