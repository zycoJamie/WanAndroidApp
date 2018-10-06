package com.example.levi.wanandroidapp.util.network;

import android.annotation.SuppressLint;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: Levi
 * CreateDate: 2018/10/6 13:48
 */
public class RxUtil {

    /**
     * 对Observable进行统一的线程切换
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 类型转换
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object object){
        return (T)object;
    }

}
