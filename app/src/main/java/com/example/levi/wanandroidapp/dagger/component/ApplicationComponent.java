package com.example.levi.wanandroidapp.dagger.component;


import android.content.Context;

import com.example.levi.wanandroidapp.dagger.module.ApplicationModule;

import dagger.Component;

@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    Context provideApplicationContext();
}
