package com.example.levi.wanandroidapp.util.app;

import android.content.Context;
import android.widget.Toast;

/**
 * Author: Levi
 * CreateDate: 2018/10/4 12:47
 */
public class ToastUtil {
    public static boolean isShow = true;

    public static void show(Context context, CharSequence message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
