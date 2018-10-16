package com.example.levi.wanandroidapp.dagger.component;


import com.example.levi.wanandroidapp.dagger.module.FragmentModule;
import com.example.levi.wanandroidapp.ui.knowledge.fragment.KnowledgeFragment;
import com.example.levi.wanandroidapp.ui.knowledge.fragment.KnowledgeListFragment;
import com.example.levi.wanandroidapp.ui.main.fragment.HomePageFragment;
import com.example.levi.wanandroidapp.ui.project.fragment.ProjectFragment;
import com.example.levi.wanandroidapp.ui.project.fragment.ProjectListFragment;

import dagger.Component;

@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(HomePageFragment homePageFragment);
    void inject(KnowledgeListFragment knowledgeListFragment);
    void inject(KnowledgeFragment knowledgeFragment);
    void inject(ProjectFragment projectFragment);
    void inject(ProjectListFragment projectListFragment);
}
