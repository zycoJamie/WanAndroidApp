package com.example.levi.wanandroidapp.ui.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.levi.wanandroidapp.presenter.main.HomePagePresenter;
import com.example.levi.wanandroidapp.ui.main.activity.AriticleDetailsActivity;
import com.example.levi.wanandroidapp.ui.main.adapter.HomePageAdapter;
import com.example.levi.wanandroidapp.util.app.SharedPreferenceUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomePageFragment extends BaseRootFragment<HomePagePresenter> implements HomePageContract.IView, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

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
        mBannerLl= (LinearLayout) getLayoutInflater().inflate(R.layout.view_banner,null);
        mBanner=mBannerLl.findViewById(R.id.banner);
        mBannerLl.removeView(mBanner);
        mBannerLl.addView(mBanner);
    }

    @Override
    protected void initData() {
        setRefresh();
        mArticleList=new ArrayList<>();
        mLinkList=new ArrayList<>();
        mImageList=new ArrayList<>();
        mTitleList=new ArrayList<>();
        if(Constant.DEFAULT.equals(SharedPreferenceUtil.get(mActivity, Constant.USERNAME,Constant.DEFAULT))){
            mPresenter.getBanner();
            mPresenter.getHomepageList(0);
        }else{
            mPresenter.loginAndLoad();
        }
        mAdapter=new HomePageAdapter(R.layout.item_homepage,mArticleList);
        mAdapter.addHeaderView(mBannerLl);
        mAdapter.setOnItemClickListener(HomePageFragment.this);
        mAdapter.setOnItemChildClickListener(HomePageFragment.this);

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
    public void getHomepageListOk(HomePageArticleBean dataBean, boolean isRefresh) {

    }

    @Override
    public void getHomepageListErr(String info) {

    }

    @Override
    public void getBannerOk(List<BannerBean> bannerBean) {

    }

    @Override
    public void getBannerErr(String info) {

    }

    @Override
    public void loginSuccess(UserInfo userInfo) {

    }

    @Override
    public void loginErr(String info) {

    }

    @Override
    public void collectArticleOK(String info) {

    }

    @Override
    public void collectArticleErr(String info) {

    }

    @Override
    public void cancelCollectArticleOK(String info) {

    }

    @Override
    public void cancelCollectArticleErr(String info) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle=new Bundle();
        bundle.putString(Constant.ARTICLE_TITLE,mAdapter.getData().get(position).getTitle());
        bundle.putString(Constant.ARTICLE_LINK,mAdapter.getData().get(position).getLink());
        bundle.putBoolean(Constant.ARTICLE_IS_COLLECT,mAdapter.getData().get(position).isCollect());
        bundle.putInt(Constant.ARTICLE_ID,mAdapter.getData().get(position).getId());
        Intent intent=new Intent(mActivity, AriticleDetailsActivity.class);
        intent.putExtras(bundle);
        /*ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity,view,mContext.getString(R.string.share_element_view));
        startActivity(intent,optionsCompat.toBundle());*/
        startActivity(intent);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        mClickPosition=position;
        switch (view.getId()){
            case R.id.tv_type:{

                break;
            }
        }
    }
}
