package com.example.levi.wanandroidapp.model.api;

import com.example.levi.wanandroidapp.util.app.LogUtil;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Author: Levi
 * CreateDate: 2018/10/4 17:03
 */
public class HttpLoggingInterceptor implements Interceptor {
    private final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        LogUtil.i("发送请求: " + request.method() + "\n" +
                "url: " + request.url());
        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        //okio库中source是输入流
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }
        String respBody = buffer.clone().readString(charset);
        LogUtil.i("响应码: " + response.code() + "\n" +
                "响应体: " + respBody);
        return response;
    }
}
