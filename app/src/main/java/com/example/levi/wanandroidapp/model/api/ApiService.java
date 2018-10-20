package com.example.levi.wanandroidapp.model.api;

import com.example.levi.wanandroidapp.data.collection.CollectionListBean;
import com.example.levi.wanandroidapp.data.knowledge.KnowledgeClassifyListBean;
import com.example.levi.wanandroidapp.data.knowledge.KnowledgeListBean;
import com.example.levi.wanandroidapp.data.login.UserInfo;
import com.example.levi.wanandroidapp.data.main.BannerBean;
import com.example.levi.wanandroidapp.data.main.HomePageArticleBean;
import com.example.levi.wanandroidapp.data.main.HotBean;
import com.example.levi.wanandroidapp.data.main.SearchHotWordBean;
import com.example.levi.wanandroidapp.data.project.ProjectBean;
import com.example.levi.wanandroidapp.data.project.ProjectListBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    Observable<BaseResponse<UserInfo>> login(@Field("username") String username, @Field("password") String password);

    /**
     * 注册
     */
    @Headers({"baseUrl:normal"})
    @POST("user/register")
    @FormUrlEncoded
    Observable<BaseResponse<UserInfo>> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String confirmPWD);

    /**
     * 收藏文章
     */
    @Headers({"baseUrl:normal"})
    @POST("lg/collect/{id}/json")
    Observable<BaseResponse> collectArticle(@Path("id") int id);

    /**
     * 取消收藏的文章
     *
     * @param id 主页文章列表中，文章的id
     */
    @Headers({"baseUrl:normal"})
    @POST("lg/uncollect_originId/{id}/json")
    Observable<BaseResponse> cancelCollectArticle(@Path("id") int id);

    /**
     * 收藏列表
     */
    @Headers({"baseUrl:normal"})
    @GET("lg/collect/list/{page}/json")
    Observable<BaseResponse<CollectionListBean>> collectionListArticle(@Path("page") int page);

    /**
     * 取消收藏列表里面的文章
     */
    @Headers({"baseUrl:normal"})
    @POST("lg/uncollect/{id}/json")
    @FormUrlEncoded
    Observable<BaseResponse> cancelCollectionListArticle(@Path("id") int id, @Field("originId") int originId);

    /**
     * 某一类知识下的文章列表
     */
    @Headers({"baseUrl:normal"})
    @GET("article/list/{page}/json")
    Observable<BaseResponse<KnowledgeClassifyListBean>> getKnowledgeClassifyList(@Path("page") int page, @Query("cid") int cid);

    /**
     * 整个知识体系
     */
    @Headers({"baseUrl:normal"})
    @GET("tree/json")
    Observable<BaseResponse<List<KnowledgeListBean>>> getHierarchy();

    /**
     * 整个项目分类
     */
    @Headers({"baseUrl:normal"})
    @GET("project/tree/json")
    Observable<BaseResponse<List<ProjectBean>>> getProjectTitle();

    /**
     * 单个项目种类下的项目列表
     */
    @Headers({"baseUrl:normal"})
    @GET("project/list/{page}/json")
    Observable<BaseResponse<ProjectListBean>> getProjectList(@Path("page") int page, @Query("cid") int cid);

    /**
     * 获取热门网站
     */
    @Headers({"baseUrl:normal"})
    @GET("friend/json")
    Observable<BaseResponse<List<HotBean>>> getHotWebsite();

    /**
     * 获取搜索热词
     */
    @Headers({"baseUrl:normal"})
    @GET("hotkey/json")
    Observable<BaseResponse<List<SearchHotWordBean>>> getHotWord();

}
