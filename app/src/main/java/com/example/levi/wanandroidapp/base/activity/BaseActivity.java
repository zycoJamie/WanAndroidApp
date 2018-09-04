package com.example.levi.wanandroidapp.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;


import com.example.levi.wanandroidapp.base.app.MyApplication;
import com.example.levi.wanandroidapp.base.presenter.AbsPresenter;
import com.example.levi.wanandroidapp.base.view.AbstractView;
import com.example.levi.wanandroidapp.util.network.NetworkBroadcastReceiver;

import me.yokeyword.fragmentation.SupportActivity;

public class BaseActivity<T extends AbsPresenter> extends SupportActivity implements AbstractView, NetworkBroadcastReceiver.NetEvent {
    protected MyApplication mCcontext;
    protected BaseActivity mActivity;

    private int mNetMobile;
    public static NetworkBroadcastReceiver.NetEvent gEventActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    public void reload() {

    }

    @Override
    public void setVisible(View... views) {

    }

    @Override
    public void setInVisible(View... views) {

    }

    @Override
    public void setGone(View... views) {

    }

    @Override
    public void onNetChange(int netMobile) {

    }
}
