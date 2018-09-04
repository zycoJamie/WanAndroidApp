package com.example.levi.wanandroidapp.dagger.module;

import android.content.Context;

import com.example.levi.wanandroidapp.base.app.MyApplication;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private MyApplication mApplication;

    public ApplicationModule(MyApplication application) {
        mApplication = application;
    }

    @Provides
    Context provideApplicationContext() {
        return mApplication.getApplicationContext();
    }
}
