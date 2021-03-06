package com.bignerdranch.android.redbook.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface RedBookApi {

    //获取一组story数据
    @GET("story/display")
    fun fetchStory(): Call<RedBookResponse>

    //解析url
    @GET
    fun fetchUrlBytes(@Url url: String): Call<ResponseBody>

//    @GET("services/rest?method=flickr.photos.search")
    @GET("story/display")
    fun searchStory(@Query("text") query: String): Call<RedBookResponse>




}