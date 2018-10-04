package com.example.levi.wanandroidapp.model.api;

import com.example.levi.wanandroidapp.model.constant.Constant;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: Levi
 * CreateDate: 2018/10/4 16:20
 */
public class BaseUrlInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original=chain.request();
        HttpUrl oldUrl = original.url();
        Request.Builder builder=original.newBuilder();
        List<String> urlNameList=original.headers("baseUrl");
        if(urlNameList!=null && urlNameList.size()>0){
            builder.removeHeader("baseUrl");
            String urlName=urlNameList.get(0);
            HttpUrl baseUrl=null;
            //根据自定义header，得到不同的BaseUrl
            if(urlName.equals(Constant.WAN_ANDROID)){
                baseUrl=HttpUrl.parse(AppConfig.BASE_URL);
            }else if(urlName.equals(Constant.DOU_YU)){
                baseUrl=HttpUrl.parse(AppConfig.DOUYU_URL);
            }else if(urlName.equals(Constant.GET_LIVE)){
                baseUrl=HttpUrl.parse(AppConfig.GETLIVE_URL);
            }
            //构造新的请求URL
            HttpUrl newHttpUrl=oldUrl.newBuilder()
                    .scheme(baseUrl.scheme())
                    .host(baseUrl.host())
                    .port(baseUrl.port())
                    .build();
            Request newRequest=builder.url(newHttpUrl).build();
            return chain.proceed(newRequest);
        }
        return chain.proceed(original);
    }
}
