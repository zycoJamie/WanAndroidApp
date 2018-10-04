package com.example.levi.wanandroidapp.model.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

/**
 * Author: Levi
 * CreateDate: 2018/10/4 15:59
 */
public class ApiStore {
    private static Retrofit sRetrofit;
    private static String sBaseUrl=AppConfig.BASE_URL;

    static {
        createProxy();
    }

    private static void createProxy() {
        Gson gson=new GsonBuilder().setDateFormat("yyyy.MM.dd HH:mm:ss").create();
        OkHttpClient.Builder builder=new OkHttpClient().newBuilder()
                .addInterceptor(chain -> {
                    Request original=chain.request();
                    Request request=original.newBuilder().build();
                    return chain.proceed(request);
                })
                .addInterceptor(new BaseUrlInterceptor())
                .addInterceptor(new HttpLoggingInterceptor());
    }
}
