package com.example.levi.wanandroidapp.base.app;

import android.app.Application;

import com.example.levi.wanandroidapp.dagger.component.ApplicationComponent;
import com.example.levi.wanandroidapp.dagger.component.DaggerApplicationComponent;
import com.example.levi.wanandroidapp.dagger.module.ApplicationModule;

public class MyApplication extends Application {
    private static MyApplication sMyApplication;
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initApplicationComponent();
        sMyApplication = this;
    }

    /**
     * 初始化ApplicationComponent
     */
    private void initApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static synchronized MyApplication getInstance() {
        return sMyApplication;
    }


}
