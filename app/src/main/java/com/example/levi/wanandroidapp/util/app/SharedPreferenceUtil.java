package com.example.levi.wanandroidapp.util.app;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {

    /**
     * sp文件名
     */
    public static final String FILE_NAME = "share_data";

    /**
     * 根据默认值类型，调用不同的sp方法
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        }else if(defaultObject instanceof Integer){
            return sp.getInt(key, (Integer) defaultObject);
        }else if(defaultObject instanceof Boolean){
            return sp.getBoolean(key, (Boolean) defaultObject);
        }else if (defaultObject instanceof Float){
            return sp.getFloat(key, (Float) defaultObject);
        }else if(defaultObject instanceof Long){
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }
}
