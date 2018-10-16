package com.example.levi.wanandroidapp.contract.project;

import com.example.levi.wanandroidapp.base.presenter.AbsPresenter;
import com.example.levi.wanandroidapp.base.view.AbstractView;
import com.example.levi.wanandroidapp.data.project.ProjectBean;

import java.util.List;

/**
 * Author: Levi
 * CreateDate: 2018/10/16 9:24
 */
public class ProjectContract {
    public interface View extends AbstractView {

        void getProjectTitleOk(List<ProjectBean> dataBean);

        void getProjectTitleErr(String info);

    }

    public interface Presenter extends AbsPresenter<View> {

        void getProjectTitle();

    }
}
