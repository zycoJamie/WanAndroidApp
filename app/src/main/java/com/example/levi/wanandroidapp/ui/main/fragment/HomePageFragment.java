package com.example.levi.wanandroidapp.ui.main.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.base.fragment.BaseRootFragment;
import com.example.levi.wanandroidapp.contract.main.HomePageContract;
import com.example.levi.wanandroidapp.data.login.UserInfo;
import com.example.levi.wanandroidapp.data.main.BannerBean;
import com.example.levi.wanandroidapp.data.main.HomePageArticleBean;
import com.example.levi.wanandroidapp.presenter.main.HomePagePresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.BindView;

public class HomePageFragment extends BaseRootFragment<HomePagePresenter> implements HomePageContract.IView {

    @BindView(R.id.normal_view)
    SmartRefreshLayout mRefreshSrl;
    @BindView(R.id.rv_content)
    RecyclerView mListRv;

    private Banner mBanner;
    private LinearLayout mBannerLl;


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
}
