package com.example.levi.wanandroidapp.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;


import com.example.levi.wanandroidapp.base.app.MyApplication;
import com.example.levi.wanandroidapp.base.presenter.AbsPresenter;
import com.example.levi.wanandroidapp.base.view.AbstractView;
import com.example.levi.wanandroidapp.dagger.component.ActivityComponent;
import com.example.levi.wanandroidapp.dagger.component.DaggerActivityComponent;
import com.example.levi.wanandroidapp.dagger.module.ActivityModule;
import com.example.levi.wanandroidapp.model.constant.MessageEvent;
import com.example.levi.wanandroidapp.util.network.NetUtils;
import com.example.levi.wanandroidapp.util.network.NetworkBroadcastReceiver;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

public abstract class BaseActivity<T extends AbsPresenter> extends SupportActivity implements AbstractView, NetworkBroadcastReceiver.NetEvent {
    protected MyApplication mCcontext;
    protected BaseActivity mActivity;
    protected ActivityComponent mActivityComponent;
    private int mNetMobile;
    private boolean isNetwork;
    public static NetworkBroadcastReceiver.NetEvent gEventActivity;

    @Inject
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mCcontext = MyApplication.getInstance();
        mActivity = this;
        gEventActivity = this;
        initActivityComponent();
        initBind();
        initInject();
        onViewCreated();
        initUI();
        initToolbar();
        initData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

    }

    protected void initToolbar() {

    }

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化UI
     */
    protected abstract void initUI();

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

    /**
     * 获取布局id
     */
    protected abstract int getLayoutId();

    private void initActivityComponent() {
        mActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(MyApplication.getInstance().getApplicationComponent())
                .activityModule(new ActivityModule(this)).build();
    }

    public void initBind() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        NetUtils.init(MyApplication.getInstance());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
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

    @Override
    public void setVisible(View... views) {
        for(View v:views){
            v.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setInVisible(View... views) {
        for(View v:views){
            v.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void setGone(View... views) {
        for(View view:views){
            view.setVisibility(View.GONE);
        }
    }

    /**
     * 获得网络变化后的类型
     */
    @Override
    public void onNetChange(int netMobile) {
        this.mNetMobile=netMobile;
        isNetwork=isNetConnect();
    }

    /**
     * 根据返回的网络类型，判断是否有网络
     */
    private boolean isNetConnect() {
        if(mNetMobile==NetUtils.NETWORK_MOBILE){
            return true;
        }else if(mNetMobile==NetUtils.NETWORK_WIFI){
            return true;
        }else if(mNetMobile==NetUtils.NETWORK_NONE){
            return false;
        }
        return false;
    }
}
