package com.example.levi.wanandroidapp.widget;


import android.view.animation.Interpolator;

/**
 * Author: Levi
 * CreateDate: 2018/10/18 14:44
 */
public class ElasticOutInterpolator implements Interpolator {

    @Override
    public float getInterpolation(float input) {
        float p = .3f;
        float s = p / 4;
        return ((float) Math.pow(2, -10 * input) * (float) Math.sin((input - s) * (2 * (float) Math.PI) / p) + 1);
    }
}
