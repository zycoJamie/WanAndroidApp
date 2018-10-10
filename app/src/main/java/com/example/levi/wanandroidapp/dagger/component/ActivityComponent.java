package com.example.levi.wanandroidapp.dagger.component;

import android.app.Activity;

import com.example.levi.wanandroidapp.dagger.module.ActivityModule;
import com.example.levi.wanandroidapp.ui.login.LoginActivity;

import dagger.Component;

@Component(dependencies = ApplicationComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();
    void inject(LoginActivity loginActivity);
}
