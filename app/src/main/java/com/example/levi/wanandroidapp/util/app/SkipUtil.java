package com.example.levi.wanandroidapp.util.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by zyco
 * on 2018/9/7
 */
public class SkipUtil {
    /**
     * 不带参数跳转activity
     */
    public static void overlay(Context context, Class<? extends Activity> target){
        Intent intent=new Intent(context,target);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
