package com.example.levi.wanandroidapp.model.cookie;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Author: Levi
 * CreateDate: 2018/10/5 17:56
 */
public class CookiesManager implements CookieJar {
    private static final PersistentCookieStore sCookieStore=new PersistentCookieStore();

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if(cookies.size()>0){
           for (Cookie item:cookies){
               sCookieStore.add(url,item);
           }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return sCookieStore.get(url);
    }

    /**
     * 清除所有cookies
     */
    public static void clearAllCookies(){
        sCookieStore.removeAll();
    }

    /**
     * 删除指定cookie
     */
    public static boolean clearCookies(HttpUrl url,Cookie cookie){
        return sCookieStore.remove(url,cookie);
    }

    /**
     * 获取所有cookies
     */
    public static List<Cookie> getCookies(){
        return sCookieStore.getCookies();
    }
}
