package com.example.levi.wanandroidapp.ui.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.base.fragment.BaseRootFragment;
import com.example.levi.wanandroidapp.contract.main.HomePageContract;
import com.example.levi.wanandroidapp.data.login.UserInfo;
import com.example.levi.wanandroidapp.data.main.BannerBean;
import com.example.levi.wanandroidapp.data.main.HomePageArticleBean;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.model.constant.EventConstant;
import com.example.levi.wanandroidapp.model.constant.MessageEvent;
import com.example.levi.wanandroidapp.presenter.main.HomePagePresenter;
import com.example.levi.wanandroidapp.ui.knowledge.activity.KnowledgeClassifyActivity;
import com.example.levi.wanandroidapp.ui.login.LoginActivity;
import com.example.levi.wanandroidapp.ui.main.activity.AriticleDetailsActivity;
import com.example.levi.wanandroidapp.ui.main.adapter.HomePageAdapter;
import com.example.levi.wanandroidapp.util.app.LogUtil;
import com.example.levi.wanandroidapp.util.app.SharedPreferenceUtil;
import com.example.levi.wanandroidapp.util.app.SkipUtil;
import com.example.levi.wanandroidapp.util.app.ToastUtil;
import com.example.levi.wanandroidapp.util.glide.GlideImageLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomePageFragment extends BaseRootFragment<HomePagePresenter> implements HomePageContract.IView, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    private static final String TAG = HomePageFragment.class.getSimpleName();
    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshSrl;
    @BindView(R.id.rv_content)
    RecyclerView mListRv;

    private Banner mBanner;
    private LinearLayout mBannerLl;
    private List<HomePageArticleBean.DatasBean> mArticleList;
    private List<String> mLinkList;
    private List<String> mImageList;
    private List<String> mTitleList;
    private HomePageAdapter mAdapter;
    private int mClickPosition;


    public static HomePageFragment getInstance() {
        return new HomePageFragment();
    }

    @Override
    protected void initInject() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initUI() {
        super.initUI();
        showLoading();
        mListRv.setLayoutManager(new LinearLayoutManager(mContext));
        mBannerLl = (LinearLayout) getLayoutInflater().inflate(R.layout.view_banner, null);
        mBanner = mBannerLl.findViewById(R.id.banner);
        mBannerLl.removeView(mBanner);
        mBannerLl.addView(mBanner);
    }

    @Override
    protected void initData() {
        setRefresh();
        mArticleList = new ArrayList<>();
        mLinkList = new ArrayList<>();
        mImageList = new ArrayList<>();
        mTitleList = new ArrayList<>();
        if (Constant.DEFAULT.equals(SharedPreferenceUtil.get(mActivity, Constant.USERNAME, Constant.DEFAULT))) {
            LogUtil.i(TAG,"无登录信息");
            mPresenter.getBanner();
            mPresenter.getHomepageList(0);
        } else {
            LogUtil.i(TAG,"有登录信息");
            mPresenter.loginAndLoad();
        }
        mAdapter = new HomePageAdapter(R.layout.item_homepage, mArticleList);
        mAdapter.addHeaderView(mBannerLl);
        mAdapter.setOnItemClickListener(HomePageFragment.this);
        mAdapter.setOnItemChildClickListener(HomePageFragment.this);
        mListRv.setAdapter(mAdapter);
    }

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        switch (event.getCode()) {
            case EventConstant.HOME_SCROLL_2_TOP: {
                mListRv.smoothScrollToPosition(0);
                break;
            }
            case EventConstant.REFRESH_HOMEPAGE: {
                mListRv.smoothScrollToPosition(0);
                break;
            }
        }
    }

    /**
     * 下拉刷新和上拉加载更多的回调监听
     */
    private void setRefresh() {
        mRefreshSrl.setOnRefreshListener(refreshLayout -> {
            mPresenter.autoRefresh();
            refreshLayout.finishRefresh(1000);
        });
        mRefreshSrl.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.loadMore();
            refreshLayout.finishLoadMore(1000);
        });
    }

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_homepage;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBanner != null) {
            mBanner.stopAutoPlay();
        }
    }


    @Override
    public void getHomepageListOk(HomePageArticleBean dataBean, boolean isRefresh) {
        if (mAdapter == null) {
            return;
        }
        if (isRefresh) {
            mArticleList = dataBean.getDatas();
            mAdapter.replaceData(mArticleList);
        } else {
            mArticleList.addAll(dataBean.getDatas());
            mAdapter.addData(dataBean.getDatas());
        }
        showNormal();
    }

    @Override
    public void getHomepageListErr(String info) {
        LogUtil.i(TAG,"error message:"+info);
        showError();
    }

    @Override
    public void getBannerOk(List<BannerBean> bannerBean) {
        mTitleList.clear();
        mImageList.clear();
        mLinkList.clear();
        for (BannerBean bean : bannerBean) {
            mLinkList.add(bean.getUrl());
            mImageList.add(bean.getImagePath());
            mTitleList.add(bean.getTitle());
        }
        mBanner.setImageLoader(new GlideImageLoader())
                .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                .setImages(mImageList)
                .setBannerTitles(mTitleList)
                .setBannerAnimation(Transformer.DepthPage)
                .isAutoPlay(true)
                .setDelayTime(5000)
                .setIndicatorGravity(BannerConfig.RIGHT)
                .start();
        mBanner.setOnBannerListener(position -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.ARTICLE_TITLE, mTitleList.get(position));
            bundle.putString(Constant.ARTICLE_LINK, mLinkList.get(position));
            if (!TextUtils.isEmpty(mLinkList.get(position))) {
                SkipUtil.overlay(mActivity, AriticleDetailsActivity.class, bundle);
            }
        });
    }

    @Override
    public void getBannerErr(String info) {
        LogUtil.i(TAG,"error message:"+info);
        showError();
    }

    @Override
    public void loginSuccess(UserInfo userInfo) {
        ToastUtil.show(mContext, getString(R.string.login_success_auto));
        SharedPreferenceUtil.put(mActivity, Constant.ISLOGIN, true);
        EventBus.getDefault().post(new MessageEvent(EventConstant.LOGIN_SUCCESS, ""));
    }

    @Override
    public void loginErr(String info) {
        ToastUtil.show(mContext, info);
    }

    @Override
    public void collectArticleOK(String info) {
        if (mAdapter != null && mAdapter.getData().size() > mClickPosition) {
            ToastUtil.show(mContext, getString(R.string.collect_article));
            mAdapter.getData().get(mClickPosition).setCollect(true);
            mAdapter.setData(mClickPosition, mAdapter.getData().get(mClickPosition));
        }
    }

    @Override
    public void collectArticleErr(String info) {
        ToastUtil.show(mContext, getString(R.string.please_you_must_login));
        SkipUtil.overlay(mActivity, LoginActivity.class);
    }

    @Override
    public void cancelCollectArticleOK(String info) {
        if (mAdapter != null && mAdapter.getData().size() > mClickPosition) {
            ToastUtil.show(mContext, getString(R.string.collect_cancel_article));
            mAdapter.getData().get(mClickPosition).setCollect(false);
            mAdapter.setData(mClickPosition, mAdapter.getData().get(mClickPosition));
        }
    }

    @Override
    public void cancelCollectArticleErr(String info) {
        ToastUtil.show(mContext, getString(R.string.please_you_must_login_2));
        SkipUtil.overlay(mActivity, LoginActivity.class);
    }

    /**
     * 当出现ErrorView时，单击重新加载数据
     */
    @Override
    public void reload() {
        super.reload();
        showLoading();
        mPresenter.autoRefresh();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.ARTICLE_TITLE, mAdapter.getData().get(position).getTitle());
        bundle.putString(Constant.ARTICLE_LINK, mAdapter.getData().get(position).getLink());
        bundle.putBoolean(Constant.ARTICLE_IS_COLLECT, mAdapter.getData().get(position).isCollect());
        bundle.putInt(Constant.ARTICLE_ID, mAdapter.getData().get(position).getId());
        Intent intent = new Intent(mActivity, AriticleDetailsActivity.class);
        intent.putExtras(bundle);
        /*ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity,view,mContext.getString(R.string.share_element_view));
        startActivity(intent,optionsCompat.toBundle());*/
        startActivity(intent);
    }

    @SuppressWarnings("all")
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        mClickPosition = position;
        switch (view.getId()) {
            case R.id.tv_type: {
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, view, mContext.getString(R.string.share_element_view));
                Intent intent = new Intent(mActivity, KnowledgeClassifyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean(Constant.HOMEPAGE_TAG, true);
                bundle.putInt(Constant.HOMEPAGE_CID, mAdapter.getData().get(position).getChapterId());
                bundle.putString(Constant.HOMEPAGE_CNAME, mAdapter.getData().get(position).getChapterName());
                bundle.putString(Constant.HOMEPAGE_SUPER_CNAME, mAdapter.getData().get(position).getSuperChapterName());
                intent.putExtras(bundle);
                /*startActivity(intent,optionsCompat.toBundle());*/
                startActivity(intent);
                break;
            }
            case R.id.iv_collect: {
                if ((Boolean) SharedPreferenceUtil.get(mContext, Constant.ISLOGIN, false)) {
                    if (mAdapter.getData().get(mClickPosition).isCollect()) {
                        mPresenter.cancelCollectArticle(mAdapter.getData().get(mClickPosition).getId());
                    } else {
                        mPresenter.collectArticle(mAdapter.getData().get(mClickPosition).getId());
                    }
                } else {
                    ToastUtil.show(mContext, getString(R.string.please_you_must_login));
                    SkipUtil.overlay(mActivity, LoginActivity.class);
                }
                break;
            }
        }
    }
}
