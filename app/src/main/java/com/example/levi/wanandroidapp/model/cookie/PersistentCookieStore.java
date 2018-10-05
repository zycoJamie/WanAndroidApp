package com.example.levi.wanandroidapp.model.cookie;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.levi.wanandroidapp.base.app.MyApplication;
import com.example.levi.wanandroidapp.util.app.LogUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

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

        /*将持久化的cookie加载到内存中，
        sp中存储的形式是：
        1.主机域名为键-以逗号分隔的cookie.name组成的字符串为值;
        2.cookie.name为键-编码为16进制字符串的cookie为值;
        内存中存储形式是：
        主机域名为键-Map为值，Map中键值对为cookie.name-Cookie*/

        Map<String, ?> prefsMap = mCookiePrefs.getAll();
        for (Map.Entry<String, ?> entry : prefsMap.entrySet()) {
            String[] cookieNames = TextUtils.split((String) entry.getValue(), ",");
            for (String name : cookieNames) {
                String encodedCookie = mCookiePrefs.getString(name, null);
                if (encodedCookie != null) {
                    Cookie decodedCookie = decodeCookie(encodedCookie);
                    if (decodedCookie != null) {
                        if (!cookies.containsKey(entry.getKey())) {
                            cookies.put(entry.getKey(), new ConcurrentHashMap<String, Cookie>());
                        }
                        cookies.get(entry.getKey()).put(name, decodedCookie);
                    }
                }
            }

        }
    }

    /**
     * 添加cookie
     */
    public void add(HttpUrl url, Cookie cookie) {
        String name = getCookieToken(cookie);
        //将cookies缓存到内存中 如果缓存过期 就重置此cookie
        if (!cookie.persistent()) {
            if (!cookies.containsKey(url.host())) {
                cookies.put(url.host(), new ConcurrentHashMap<String, Cookie>());
            }
            cookies.get(url.host()).put(name, cookie);
        } else {
            if (cookies.containsKey(url.host())) {
                cookies.get(url.host()).remove(name);
            }
        }
        //将cookies持久化到本地
        SharedPreferences.Editor persistent = mCookiePrefs.edit();
        persistent.putString(url.host(), TextUtils.join(",", cookies.get(url.host()).keySet()));
        persistent.putString(name, encodeCookie(new OkHttpCookies(cookie)));
        persistent.apply();
    }

    /**
     * 得到指定主机的cookies
     */
    public List<Cookie> get(HttpUrl url) {
        ArrayList<Cookie> refCookies = new ArrayList<>();
        if (cookies.containsKey(url.host())) {
            refCookies.addAll(cookies.get(url.host()).values());
        }
        return refCookies;
    }

    /**
     * 删除所有的cookie，包括内存和磁盘上的
     */
    public boolean removeAll(){
        SharedPreferences.Editor persistent=mCookiePrefs.edit();
        persistent.clear();
        persistent.apply();
        cookies.clear();
        return true;
    }

    /**
     * 删除指定cookie
     */
    public boolean remove(HttpUrl url,Cookie cookie){
        String name=getCookieToken(cookie);
        if(cookies.containsKey(url.host()) && cookies.get(url.host()).containsKey(name)){
            cookies.get(url.host()).remove(name);
            SharedPreferences.Editor persistent=mCookiePrefs.edit();
            persistent.putString(url.host(),TextUtils.join(",",cookies.get(url.host()).keySet()));
            if(mCookiePrefs.contains(name)){
                persistent.remove(name);
            }
            persistent.apply();
            return true;
        }else{
            return false;
        }
    }

    /**
     * 得到所有的cookie
     */
    public List<Cookie> getCookies(){
        ArrayList<Cookie> lists=new ArrayList<>();
        for(String host:cookies.keySet()){
            lists.addAll(cookies.get(host).values());
        }
        return lists;
    }


    /**
     * 构造Map中存储cookie的键
     */
    private String getCookieToken(Cookie cookie) {
        return cookie.name() + "@" + cookie.domain();
    }

    /**
     * 编码Cookie为16进制字符串
     */
    private String encodeCookie(OkHttpCookies okHttpCookies) {
        if (okHttpCookies == null) {
            return null;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(okHttpCookies);
        } catch (IOException e) {
            LogUtil.e(TAG, "IOException in encodeCookie");
        }
        return byteArray2HexString(bos.toByteArray());
    }


    /**
     * 解码16进制字符串为Cookie对象
     */
    private Cookie decodeCookie(String encodedCookie) {
        byte[] bytes = hexString2ByteArray(encodedCookie);
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Cookie cookie = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(bis);
            cookie = ((OkHttpCookies) (ois.readObject())).getCookies();
        } catch (IOException e) {
            LogUtil.e(TAG, "IOException in decodeCookie");
        } catch (ClassNotFoundException e) {
            LogUtil.e(TAG, "ClassNotFoundException in decodeCookie");
        }
        return cookie;
    }

    /**
     * 十六进制字符串转字节数组
     */
    private byte[] hexString2ByteArray(String encodedCookie) {
        byte[] bytes = new byte[encodedCookie.length() / 2];
        if (Integer.valueOf(encodedCookie) < 16) {
            bytes[0] = Integer.valueOf(encodedCookie).byteValue();
        } else {
            for (int i = 0; i < encodedCookie.length(); i += 2) {
                bytes[i / 2] = (byte) (((Integer.parseInt(String.valueOf(encodedCookie.charAt(i)), 16)) << 4) + Integer.parseInt(String.valueOf(encodedCookie.charAt(i + 1)), 16));
            }
        }
        return bytes;
    }

    private char[] hexArray = "0123456789ABCDEF".toCharArray();

    /**
     * 字节数组转十六进制字符串
     */
    private String byteArray2HexString(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int temp = bytes[i] & 0xff;
            hexChars[i * 2] = hexArray[temp >>> 4];
            hexChars[i * 2 + 1] = hexArray[temp & 0x0f];
        }
        return String.valueOf(hexChars);
    }

}
