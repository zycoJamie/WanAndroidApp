package com.example.levi.wanandroidapp.util.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by zyco
 * on 2018/9/7
 */
public class GlideUtil {
    /**
     * 加载图片
     */
    public static void loadImage(Context context, Object resId, ImageView imageView) {
        Glide.with(context)
                .load(resId)
                .into(imageView);

    }

    /**
     * 加载圆形图片
     */
    public static void loadCircleImage(Context context, Object resId, ImageView imageView) {
        RequestOptions requestOptions = RequestOptions
                .circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);
        Glide.with(context)
                .load(resId)
                .apply(requestOptions)
                .into(imageView);

    }
}
