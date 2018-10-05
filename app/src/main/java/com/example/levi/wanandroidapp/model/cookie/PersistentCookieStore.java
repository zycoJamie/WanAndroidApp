package com.example.levi.wanandroidapp.model.cookie;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.levi.wanandroidapp.base.app.MyApplication;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;

/**
 * Author: Levi
 * CreateDate: 2018/10/5 11:25
 */
public class PersistentCookieStore {
    private static final String TAG = PersistentCookieStore.class.getSimpleName();
    private static final String COOKIE_PREFS = "Cookie_Prefs";
    private final Map<String, ConcurrentHashMap<String, Cookie>> cookies;
    private final SharedPreferences mCookiePrefs;

    public PersistentCookieStore() {
        cookies = new HashMap<>();
        mCookiePrefs = MyApplication.getInstance().getSharedPreferences(COOKIE_PREFS, Context.MODE_PRIVATE);

    }
}
