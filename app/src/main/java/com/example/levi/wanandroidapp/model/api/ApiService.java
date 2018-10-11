package com.example.levi.wanandroidapp.model.api;

import com.example.levi.wanandroidapp.data.login.UserInfo;
import com.example.levi.wanandroidapp.data.main.BannerBean;
import com.example.levi.wanandroidapp.data.main.HomePageArticleBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Author: Levi
 * CreateDate: 2018/10/6 8:39
 */
public interface ApiService {
    /**
     * 主页文章
     */
    @Headers({"baseUrl:normal"})
    @GET("article/list/{page}/json")
    Observable<BaseResponse<HomePageArticleBean>> getArticleList(@Path("page") int num);

    /**
     * banner
     */
    @Headers({"baseUrl:normal"})
    @GET("banner/json")
    Observable<BaseResponse<List<BannerBean>>> getBanner();


    /**
     * 登录
     */
    @Headers({"baseUrl:normal"})
    @POST("user/login")
    @FormUrlEncoded
    Observable<BaseResponse<UserInfo>> login(@Field("username") String username,@Field("password")String password);

    /**
     * 注册
     */
    @Headers({"baseUrl:normal"})
    @POST("user/register")
    @FormUrlEncoded
    Observable<BaseResponse<UserInfo>> register(@Field("username") String username,@Field("password") String password,@Field("repassword") String confirmPWD);

    /**
     * 收藏文章
     */
    @Headers({"baseUrl:normal"})
    @POST("lg/collect/{id}/json")
    Observable<BaseResponse> collectArticle(@Path("id") int id);

    /**
     * 取消收藏的文章
     * @param id 主页文章列表中，文章的id
     */
    @Headers({"baseUrl:normal"})
    @POST("lg/uncollect_originId/{id}/json")
    Observable<BaseResponse> cancleCollectArticle(@Path("id") int id);


}
