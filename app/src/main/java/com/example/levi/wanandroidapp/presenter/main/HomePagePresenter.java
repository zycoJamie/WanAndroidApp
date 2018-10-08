package com.example.levi.wanandroidapp.presenter.main;

import com.example.levi.wanandroidapp.base.app.MyApplication;
import com.example.levi.wanandroidapp.base.presenter.BasePresenter;
import com.example.levi.wanandroidapp.contract.main.HomePageContract;
import com.example.levi.wanandroidapp.data.login.UserInfo;
import com.example.levi.wanandroidapp.data.main.BannerBean;
import com.example.levi.wanandroidapp.data.main.HomePageArticleBean;
import com.example.levi.wanandroidapp.model.api.ApiService;
import com.example.levi.wanandroidapp.model.api.ApiStore;
import com.example.levi.wanandroidapp.model.api.BaseResponse;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.util.app.LogUtil;
import com.example.levi.wanandroidapp.util.app.SharedPreferenceUtil;
import com.example.levi.wanandroidapp.util.network.RxUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function3;

/**
 * Author: Levi
 * CreateDate: 2018/10/4 15:09
 */
public class HomePagePresenter extends BasePresenter<HomePageContract.IView> implements HomePageContract.Presenter {
    private static final String TAG = HomePagePresenter.class.getSimpleName();
    private boolean isRefresh = true;
    private int currentPage;

    @Inject
    public HomePagePresenter() {

    }

    /**
     * 下拉刷新
     */
    @Override
    public void autoRefresh() {
        isRefresh = true;
        currentPage = 0;
        getBanner();
        getHomepageList(currentPage);
    }

    /**
     * 上拉加载更多
     */
    @Override
    public void loadMore() {
        isRefresh = false;
        currentPage++;
        getHomepageList(currentPage);
    }

    @Override
    public void getHomepageList(int page) {
        mCompositeDisposable.add(
                ApiStore.createApi(ApiService.class)
                        .getArticleList(page)
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribe(homePageArticleBeanBaseResponse -> {
                            if (homePageArticleBeanBaseResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                                mView.getHomepageListOk(homePageArticleBeanBaseResponse.getData(), isRefresh);
                            } else {
                                mView.getHomepageListErr(homePageArticleBeanBaseResponse.getErrorMsg());
                            }
                        }, throwable -> mView.getHomepageListErr(throwable.getMessage())));
    }

    @Override
    public void getBanner() {
        mCompositeDisposable.add(
                ApiStore.createApi(ApiService.class)
                        .getBanner()
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribe(listBaseResponse -> {
                            if (listBaseResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                                mView.getBannerOk(listBaseResponse.getData());
                            } else {
                                mView.getBannerErr(listBaseResponse.getErrorMsg());
                            }
                        }, throwable -> mView.getBannerErr(throwable.getMessage())));
    }

    @SuppressWarnings("all")
    @Override
    public void loginAndLoad() {
        String username = (String) SharedPreferenceUtil.get(MyApplication.getInstance(), Constant.USERNAME, Constant.DEFAULT);
        String password = (String) SharedPreferenceUtil.get(MyApplication.getInstance(), Constant.PASSWORD, Constant.DEFAULT);
        Observable<BaseResponse<UserInfo>> observableUser = ApiStore.createApi(ApiService.class).login(username, password);
        Observable<BaseResponse<List<BannerBean>>> observableBanner = ApiStore.createApi(ApiService.class).getBanner();
        Observable<BaseResponse<HomePageArticleBean>> observableArticle = ApiStore.createApi(ApiService.class).getArticleList(currentPage);
        mCompositeDisposable.add(
                Observable.zip(observableUser, observableBanner, observableArticle, new Function3<BaseResponse<UserInfo>, BaseResponse<List<BannerBean>>, BaseResponse<HomePageArticleBean>, Map<String, Object>>() {
                    @Override
                    public Map<String, Object> apply(BaseResponse<UserInfo> userInfoBaseResponse, BaseResponse<List<BannerBean>> listBaseResponse, BaseResponse<HomePageArticleBean> homePageArticleBeanBaseResponse) throws Exception {
                        Map<String, Object> map = new HashMap<>();
                        map.put(Constant.LOGIN_DATA, userInfoBaseResponse);
                        map.put(Constant.BANNER_DATA, listBaseResponse);
                        map.put(Constant.ARTICLE_DATA, homePageArticleBeanBaseResponse);
                        return map;
                    }
                }).compose(RxUtil.rxSchedulerHelper())
                        .subscribe(map -> {
                                    /**
                                     * 获取登录信息并显示
                                     */
                                    BaseResponse<UserInfo> userInfo = RxUtil.cast(map.get(Constant.LOGIN_DATA));
                                    if (userInfo.getErrorCode() == Constant.REQUEST_SUCCESS) {
                                        mView.loginSuccess(userInfo.getData());
                                    } else {
                                        mView.loginErr(userInfo.getErrorMsg());
                                    }
                                    /**
                                     * banner
                                     */
                                    BaseResponse<List<BannerBean>> banners = RxUtil.cast(map.get(Constant.BANNER_DATA));
                                    if (banners.getErrorCode() == Constant.REQUEST_SUCCESS) {
                                        mView.getBannerOk(banners.getData());
                                    } else {
                                        mView.getBannerErr(banners.getErrorMsg());
                                    }
                                    /**
                                     * 主界面文章
                                     */
                                    BaseResponse<HomePageArticleBean> articles = RxUtil.cast(map.get(Constant.ARTICLE_DATA));
                                    if (articles.getErrorCode() == Constant.REQUEST_SUCCESS) {
                                        mView.getHomepageListOk(articles.getData(), isRefresh);
                                    } else {
                                        mView.getHomepageListErr(articles.getErrorMsg());
                                    }
                                }
                                , throwable -> mView.getHomepageListErr(throwable.getMessage()))
        );

    }

    @Override
    public void collectArticle(int id) {
        mCompositeDisposable.add(
                ApiStore.createApi(ApiService.class)
                        .collectArticle(id)
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribe(baseResponse -> {
                            if (baseResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                                if (baseResponse.getData() != null) {
                                    LogUtil.i(TAG, (String) baseResponse.getData());
                                    mView.collectArticleOK((String) baseResponse.getData());
                                } else {
                                    mView.collectArticleErr(baseResponse.getErrorMsg());
                                }
                            }
                        }, throwable -> mView.collectArticleErr(throwable.getMessage()))
        );

    }

    @Override
    public void cancelCollectArticle(int id) {
        mCompositeDisposable.add(
                ApiStore.createApi(ApiService.class)
                        .cancleCollectArticle(id)
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribe(baseResponse -> {
                            if (baseResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                                if (baseResponse.getData() != null) {
                                    LogUtil.i(TAG, (String) baseResponse.getData());
                                    mView.cancelCollectArticleOK((String) baseResponse.getData());
                                } else {
                                    mView.cancelCollectArticleErr(baseResponse.getErrorMsg());
                                }
                            }
                        }, throwable -> mView.cancelCollectArticleErr(throwable.getMessage()))
        );
    }
}
