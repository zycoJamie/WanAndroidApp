package com.example.levi.wanandroidapp.ui.main.activity;


import android.view.View;
import android.widget.ImageView;

import com.example.levi.wanandroidapp.MainActivity;
import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.base.activity.BaseActivity;
import com.example.levi.wanandroidapp.base.app.MyApplication;
import com.example.levi.wanandroidapp.util.app.SkipUtil;
import com.example.levi.wanandroidapp.util.glide.GlideUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.iv_splash_bg)
    ImageView mSplashIv;
    private Disposable mTimer;
    private int mTime = 2;

    @OnClick(R.id.tv_splash_skip)
    void click(View view){
        switch(view.getId()){
            case R.id.tv_splash_skip:{
                if(mTimer!=null && !mTimer.isDisposed()){
                    mTimer.dispose();
                    mTimer=null;
                }
                skip();
                break;
            }
        }
    }

    @Override
    protected void initUI() {
        GlideUtil.loadImage(MyApplication.getInstance(), R.mipmap.bg_splash_test_2, mSplashIv);
    }

    @Override
    protected void initData() {
        mTimer = Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(mTime + 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Long aLong) -> {
                    if (aLong == mTime && !mTimer.isDisposed()) {
                        skip();
                    }
                });
    }

    /**
     * 跳转界面
     */
    private void skip() {
        SkipUtil.overlay(SplashActivity.this, MainActivity.class);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null && !mTimer.isDisposed()) {
            mTimer.dispose();
            mTimer = null;
        }
    }
}
