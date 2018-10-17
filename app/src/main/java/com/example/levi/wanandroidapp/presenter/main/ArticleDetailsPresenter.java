package com.example.levi.wanandroidapp.presenter.main;

import com.example.levi.wanandroidapp.base.presenter.BasePresenter;
import com.example.levi.wanandroidapp.contract.main.ArticleDetailsContract;
import com.example.levi.wanandroidapp.model.api.ApiService;
import com.example.levi.wanandroidapp.model.api.ApiStore;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.util.app.LogUtil;
import com.example.levi.wanandroidapp.util.network.RxUtil;

import javax.inject.Inject;

/**
 * Author: Levi
 * CreateDate: 2018/10/9 11:45
 */
public class ArticleDetailsPresenter extends BasePresenter<ArticleDetailsContract.IView> implements ArticleDetailsContract.Presenter {


    private static final String TAG = ArticleDetailsPresenter.class.getSimpleName();

    @Inject
    public ArticleDetailsPresenter() {

    }

    @Override
    public void collectArticle(int id) {
        mCompositeDisposable.add(
                ApiStore.createApi(ApiService.class)
                        .collectArticle(id)
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribe(baseResponse -> {
                            if (baseResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                                LogUtil.i(TAG, "收藏文章success");
                                mView.collectArticleOK("收藏文章成功");
                            } else {
                                LogUtil.i(TAG, "收藏文章error");
                                mView.collectArticleErr(baseResponse.getErrorMsg());
                            }
                        }, throwable -> {
                            LogUtil.i(TAG, "收藏文章exception");
                            mView.collectArticleErr(throwable.getMessage());
                        })
        );

    }

    @Override
    public void cancelCollectArticle(int id) {
        mCompositeDisposable.add(
                ApiStore.createApi(ApiService.class)
                        .cancelCollectArticle(id)
                        .compose(RxUtil.rxSchedulerHelper())
                        .subscribe(baseResponse -> {
                            if (baseResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                                mView.cancelCollectArticleOK("取消文章成功");
                            } else {
                                LogUtil.i(TAG, "取消文章error");
                                mView.cancelCollectArticleErr(baseResponse.getErrorMsg());
                            }
                        }, throwable -> mView.cancelCollectArticleErr(throwable.getMessage()))
        );
    }
}
