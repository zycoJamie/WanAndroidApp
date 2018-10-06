package com.example.levi.wanandroidapp.model.api;

import com.example.levi.wanandroidapp.model.cookie.CookiesManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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
                .addInterceptor(new HttpLoggingInterceptor())
                .cookieJar(new CookiesManager());
        sRetrofit=new Retrofit.Builder()
                .baseUrl(sBaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .build();
    }

    public static <T> T createApi(Class<T> serviceClass){
        return sRetrofit.create(serviceClass);
    }
}
