package com.example.levi.wanandroidapp.dagger.component;


import com.example.levi.wanandroidapp.dagger.module.FragmentModule;
import com.example.levi.wanandroidapp.ui.main.fragment.HomePageFragment;

import dagger.Component;

@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(HomePageFragment homePageFragment);
}
