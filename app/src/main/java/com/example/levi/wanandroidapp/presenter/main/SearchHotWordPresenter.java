package com.example.levi.wanandroidapp.presenter.main;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.example.levi.wanandroidapp.base.presenter.BasePresenter;
import com.example.levi.wanandroidapp.contract.main.SearchContract;
import com.example.levi.wanandroidapp.model.api.ApiService;
import com.example.levi.wanandroidapp.model.api.ApiStore;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.util.app.SharedPreferenceUtil;
import com.example.levi.wanandroidapp.util.network.RxUtil;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * Author: Levi
 * CreateDate: 2018/10/20 15:54
 */
public class SearchHotWordPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {

    @Inject
    public SearchHotWordPresenter() {

    }

    @Override
    public void getSearchHotWord() {
        mCompositeDisposable.add(ApiStore.createApi(ApiService.class)
                .getHotWord()
                .compose(RxUtil.rxSchedulerHelper())
                .subscribe(listBaseResponse -> {
                    if (listBaseResponse.getErrorCode() == Constant.REQUEST_SUCCESS) {
                        mView.getSearchHotOk(listBaseResponse.getData());
                    } else {
                        mView.getSearchHotError(listBaseResponse.getErrorMsg());
                    }
                }, throwable -> mView.getSearchHotError(throwable.getMessage())));

    }

    /**
     * 保存搜索历史
     */
    public void saveSearchHistory(Context context, List<String> list) {
        SharedPreferenceUtil.remove(context, Constant.SEARCH_HISTORY);
        StringBuilder stringBuilder = new StringBuilder();
        if (list.size() > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                list.forEach(s -> stringBuilder.append(s).append(","));
            } else {
                for (String s : list) {
                    stringBuilder.append(s).append(",");
                }
            }
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
            SharedPreferenceUtil.put(context, Constant.SEARCH_HISTORY, stringBuilder.toString());
            stringBuilder.setLength(0);
        }
    }

    /**
     * 读取搜索历史
     */
    public void readHistory(Context context, List<String> list) {
        String history = (String) SharedPreferenceUtil.get(context, Constant.SEARCH_HISTORY, Constant.DEFAULT);
        assert history != null;
        assert list != null;
        if (TextUtils.equals(history, Constant.DEFAULT)) {
            return;
        }
        list.clear();
        list.addAll(Arrays.asList(history.split(",")));
    }
}
