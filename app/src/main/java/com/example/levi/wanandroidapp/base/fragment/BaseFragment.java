package com.example.levi.wanandroidapp.base.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.levi.wanandroidapp.base.app.MyApplication;
import com.example.levi.wanandroidapp.base.presenter.AbsPresenter;
import com.example.levi.wanandroidapp.base.view.AbstractView;
import com.example.levi.wanandroidapp.dagger.component.DaggerFragmentComponent;
import com.example.levi.wanandroidapp.dagger.component.FragmentComponent;
import com.example.levi.wanandroidapp.dagger.module.FragmentModule;
import com.example.levi.wanandroidapp.model.constant.MessageEvent;
import com.example.levi.wanandroidapp.util.network.NetUtils;
import com.example.levi.wanandroidapp.util.network.NetworkBroadcastReceiver;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

public abstract class BaseFragment<T extends AbsPresenter> extends SupportFragment implements AbstractView, NetworkBroadcastReceiver.NetEvent {
    public View pRootView;
    protected Activity mActivity;
    protected MyApplication mContext;
    protected FragmentComponent mFragmentComponent;
    private int mNetMobile;
    private boolean isNetwork;

    @Inject
    protected T mPresenter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResID(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pRootView = view;
        mActivity = getActivity();
        mContext = MyApplication.getInstance();
        initFragmentComponent();
        initInject();
        onViewCreated();
        initBind(view);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initUI();
        initData();
    }

    /**
     * 数据初始化
     */
    protected abstract void initData();

    /**
     * 界面初始化
     */
    protected abstract void initUI();

    private void initBind(View view) {
        ButterKnife.bind(this, view);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        NetUtils.init(mContext);
    }

    @SuppressWarnings("unchecked")
    private void onViewCreated() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    /**
     * dagger初始化
     */
    protected void initInject() {

    }

    private void initFragmentComponent() {
        mFragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent(MyApplication.getInstance().getApplicationComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    /**
     * 得到UI布局
     */
    public abstract int getLayoutResID();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onNetChange(int netMobile) {
        mNetMobile = netMobile;
        isNetwork = isNetConnect();
    }

    /**
     * 根据返回的网络类型，判断是否有网络
     */
    private boolean isNetConnect() {
        if (mNetMobile == NetUtils.NETWORK_MOBILE) {
            return true;
        } else if (mNetMobile == NetUtils.NETWORK_WIFI) {
            return true;
        } else if (mNetMobile == NetUtils.NETWORK_NONE) {
            return false;
        }
        return false;
    }

    @Override
    public void setVisible(View... views) {
        for (View v : views) {
            v.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setInVisible(View... views) {
        for (View v : views) {
            v.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void setGone(View... views) {
        for (View v : views) {
            v.setVisibility(View.GONE);
        }
    }

    @Override
    public void showNormal() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void reload() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

    }
}
