package com.example.levi.wanandroidapp.contract.main;

import com.example.levi.wanandroidapp.base.presenter.AbsPresenter;
import com.example.levi.wanandroidapp.base.view.AbstractView;
import com.example.levi.wanandroidapp.data.login.UserInfo;
import com.example.levi.wanandroidapp.data.main.BannerBean;
import com.example.levi.wanandroidapp.data.main.HomePageArticleBean;

import java.util.List;

/**
 * Author: Levi
 * CreateDate: 2018/10/4 15:11
 */
public class HomePageContract {
    public interface IView extends AbstractView {
        void getHomepageListOk(HomePageArticleBean dataBean, boolean isRefresh);

        void getHomepageListErr(String info);

        void getBannerOk(List<BannerBean> bannerBean);

        void getBannerErr(String info);

        void loginSuccess(UserInfo userInfo);

        void loginErr(String info);

        void collectArticleOK(String info);

        void collectArticleErr(String info);

        void cancelCollectArticleOK(String info);

        void cancelCollectArticleErr(String info);
    }

    public interface Presenter extends AbsPresenter<IView> {
        void autoRefresh();

        void loadMore();

        void getHomepageList(int page);

        void getBanner();

        void loginAndLoad();

        void collectArticle(int id);

        void cancelCollectArticle(int id);
    }
}
