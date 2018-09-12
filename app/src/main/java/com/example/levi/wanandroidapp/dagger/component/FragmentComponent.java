package com.example.levi.wanandroidapp.dagger.component;

import android.app.Activity;

import com.example.levi.wanandroidapp.dagger.module.FragmentModule;
import com.example.levi.wanandroidapp.ui.main.fragment.HomePageFragment;

import dagger.Component;

@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    Activity getActivity();
    void inject(HomePageFragment homePageFragment);
}
