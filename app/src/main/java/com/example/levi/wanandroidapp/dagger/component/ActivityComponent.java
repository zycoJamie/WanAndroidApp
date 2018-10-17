package com.example.levi.wanandroidapp.dagger.component;


import com.example.levi.wanandroidapp.dagger.module.ActivityModule;
import com.example.levi.wanandroidapp.ui.login.LoginActivity;
import com.example.levi.wanandroidapp.ui.login.RegisterActivity;
import com.example.levi.wanandroidapp.ui.main.activity.ArticleDetailsActivity;
import com.example.levi.wanandroidapp.ui.mine.activity.CollectionListActivity;

import dagger.Component;

@Component(dependencies = ApplicationComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(LoginActivity loginActivity);
    void inject(RegisterActivity registerActivity);
    void inject(ArticleDetailsActivity articleDetailsActivity);
    void inject(CollectionListActivity collectionListActivity);
}
