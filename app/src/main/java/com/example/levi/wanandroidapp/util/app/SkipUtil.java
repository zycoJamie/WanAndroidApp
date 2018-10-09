package com.example.levi.wanandroidapp.util.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by zyco
 * on 2018/9/7
 */
public class SkipUtil {
    /**
     * 不带参数跳转activity
     */
    public static void overlay(Context context, Class<? extends Activity> target) {
        Intent intent = new Intent(context, target);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 带参数跳转activity，没有动画
     */
    public static void overlay(Context context, Class<? extends Activity> target, Bundle bundle) {
        Intent intent = new Intent(context, target);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}
