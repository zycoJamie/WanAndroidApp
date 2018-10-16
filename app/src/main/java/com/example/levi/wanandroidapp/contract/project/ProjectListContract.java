package com.example.levi.wanandroidapp.contract.project;

import com.example.levi.wanandroidapp.base.presenter.AbsPresenter;
import com.example.levi.wanandroidapp.base.view.AbstractView;
import com.example.levi.wanandroidapp.data.project.ProjectListBean;

/**
 * Author: Levi
 * CreateDate: 2018/10/16 14:10
 */
public class ProjectListContract {
    public interface View extends AbstractView {

        void getProjectListOk(ProjectListBean projectListBean, boolean isRefresh);

        void getProjectListErr(String info);

    }

    public interface Presenter extends AbsPresenter<View> {

        void autoRefresh();

        void loadMore();

        void getProjectList(int page, int id);

    }
}
