package com.example.levi.wanandroidapp.ui.main.viewholder;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Author: Levi
 * CreateDate: 2018/10/8 15:16
 */
public class HomePageViewHolder extends BaseViewHolder {
    /**
     * BRVAH框架在onCreateViewHolder方法执行时会通过反射去获取自定义ViewHolder的实例，并且return
     * onBindViewHolder接收ViewHolder，并执行抽象方法convert，convert方法在自定义adapter中实现
     */
    public HomePageViewHolder(View view){
        super(view);
    }
}
