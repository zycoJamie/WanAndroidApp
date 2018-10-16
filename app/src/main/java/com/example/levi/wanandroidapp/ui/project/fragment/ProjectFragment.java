package com.example.levi.wanandroidapp.ui.project.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.base.adapter.SimpleFragmentStateAdapter;
import com.example.levi.wanandroidapp.base.fragment.BaseRootFragment;
import com.example.levi.wanandroidapp.contract.project.ProjectContract;
import com.example.levi.wanandroidapp.data.project.ProjectBean;
import com.example.levi.wanandroidapp.model.constant.EventConstant;
import com.example.levi.wanandroidapp.model.constant.MessageEvent;
import com.example.levi.wanandroidapp.presenter.project.ProjectPresenter;
import com.flyco.tablayout.SlidingTabLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author: Levi
 * CreateDate: 2018/10/16 9:19
 */
public class ProjectFragment extends BaseRootFragment<ProjectPresenter> implements ProjectContract.View {

    @BindView(R.id.tl_project)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.vp_project)
    ViewPager mViewPager;

    private SimpleFragmentStateAdapter mAdapter;
    private List<Fragment> mFragments;
    private List<ProjectBean> mProjectBeans;
    private List<String> mTitles;

    public static ProjectFragment getInstance() {
        return new ProjectFragment();
    }

    @Override
    protected void initInject() {
        super.initInject();
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initUI() {
        super.initUI();
        showLoading();
    }

    @Override
    protected void initData() {
        mTitles = new ArrayList<>();
        mFragments = new ArrayList<>();
        mProjectBeans = new ArrayList<>();
        mAdapter = new SimpleFragmentStateAdapter(getChildFragmentManager(), mFragments);
        mPresenter.getProjectTitle();
    }

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_project;
    }

    @Override
    public void getProjectTitleOk(List<ProjectBean> dataBean) {
        mSlidingTabLayout.setVisibility(View.VISIBLE);
        mProjectBeans.clear();
        mFragments.clear();
        mTitles.clear();
        mProjectBeans.addAll(dataBean);
        if (mProjectBeans.size() > 0) {
            for (ProjectBean bean : mProjectBeans) {
                mFragments.add(ProjectListFragment.getInstance(bean.getId()));
                mTitles.add(bean.getName());
            }
            initTabAndPaper(mTitles.toArray(new String[mTitles.size()]));
        }
        showNormal();
    }

    private void initTabAndPaper(String[] titles) {
        mViewPager.setAdapter(mAdapter);
        mSlidingTabLayout.setViewPager(mViewPager, titles);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void reload() {
        super.reload();
        mPresenter.getProjectTitle();
        EventBus.getDefault().post(new MessageEvent(EventConstant.REFRESH_PROJECT_LIST,""));
        showLoading();
    }


    @Override
    public void getProjectTitleErr(String info) {
        showError();
    }
}
