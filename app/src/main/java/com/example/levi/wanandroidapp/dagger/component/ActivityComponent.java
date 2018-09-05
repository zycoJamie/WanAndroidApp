package com.example.levi.wanandroidapp.dagger.component;

import android.app.Activity;

import com.example.levi.wanandroidapp.dagger.module.ActivityModule;

import dagger.Component;

@Component(dependencies = ApplicationComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();
}
