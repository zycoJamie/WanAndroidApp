package com.example.levi.wanandroidapp.presenter.knowledge;

import com.example.levi.wanandroidapp.base.presenter.BasePresenter;
import com.example.levi.wanandroidapp.contract.knowledge.KnowledgeClassifyContract;
import com.example.levi.wanandroidapp.model.api.ApiService;
import com.example.levi.wanandroidapp.model.api.ApiStore;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.util.app.LogUtil;
import com.example.levi.wanandroidapp.util.network.RxUtil;

import javax.inject.Inject;


/**
 * Author: Levi
 * CreateDate: 2018/10/13 18:21
 */
public class KnowledgeClassifyPresenter extends BasePresenter<KnowledgeClassifyContract.View> implements KnowledgeClassifyContract.Presenter {

    private static final String TAG = KnowledgeClassifyPresenter.class.getSimpleName();
    private int mCurrentPage;
    private int cid;
    private boolean isRefresh = true;

    @Inject
    public KnowledgeClassifyPresenter() {

    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    @Override
    public void autoRefresh() {
        isRefresh = true;
        mCurrentPage = 0;
        getKnowledgeClassifyList(mCurrentPage, cid);
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        mCurrentPage++;
        getKnowledgeClassifyList(mCurrentPage, cid);
    }

    @Override
    public void getKnowledgeClassifyList(int page, int id) {
        mCompositeDisposable.add(ApiStore.createApi(ApiService.class)
                .getKnowledgeClassifyList(page, id)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe(knowledgeClassifyListBeanBaseResponse -> {
                    if (knowledgeClassifyListBeanBaseResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                        mView.getKnowledgeClassifyListOk(knowledgeClassifyListBeanBaseResponse.getData(), isRefresh);
                    } else {
                        mView.getKnowledgeClassifyListErr(knowledgeClassifyListBeanBaseResponse.getErrorMsg());
                    }
                }, throwable -> mView.getKnowledgeClassifyListErr(throwable.getMessage())));
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
                        .cancleCollectArticle(id)
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
