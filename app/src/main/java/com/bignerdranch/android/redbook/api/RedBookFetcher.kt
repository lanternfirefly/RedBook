package com.bignerdranch.android.redbook.api

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.android.redbook.entity.StoryItem
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "RedBookFetcher"

class RedBookFetcher {

    private val redBookApi: RedBookApi

    init {
        //拦截器
        val client = OkHttpClient.Builder()
            .addInterceptor(StoryInterceptor())
            .build()
        //配置Retrofit
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.100:8080/")
//            .baseUrl("http://localhost:8080/")
            .addConverterFactory(GsonConverterFactory.create())
//            .client(client)
            .build()
        //由Retrofit创建Api实例
        redBookApi = retrofit.create(RedBookApi::class.java)
    }

    fun fetchStoryRequest(): Call<RedBookResponse> = redBookApi.fetchStory()

    fun fetchStory(): LiveData<List<StoryItem>> {
        return fetchMetadata(fetchStoryRequest())
    }

    fun searchStoryRequest(query: String): Call<RedBookResponse> = redBookApi.searchStory(query)

    fun searchStory(query: String): LiveData<List<StoryItem>> {
        return fetchMetadata(searchStoryRequest(query))
    }

    private fun fetchMetadata(redBookRequest: Call<RedBookResponse>)
            : LiveData<List<StoryItem>> {
        val responseLiveData: MutableLiveData<List<StoryItem>> = MutableLiveData()

        redBookRequest.enqueue(object : Callback<RedBookResponse> {
            override fun onFailure(call: Call<RedBookResponse>, t: Throwable) {
                Log.e(TAG, "fetch metadata failed", t)
            }

            override fun onResponse(
                call: Call<RedBookResponse>,
                response: Response<RedBookResponse>
            ) {
                Log.d(TAG, "fetch metadata success, response.body(): ${response.body()}")
                //获取到的元数据（可能为空）
                val redBookResponse: RedBookResponse? = response.body()
                //从元数据中取出story
                val storyResponse: StoryResponse? = redBookResponse?.storyResponse
                //将story集去除空数据后作为返回值LiveData
                var storyItems: List<StoryItem> = storyResponse?.storyItems ?: mutableListOf()
//                storyItems = storyItems.filterNot { it.imgUrl.isBlank() }
                responseLiveData.value = storyItems
            }
        })

        return responseLiveData

    }


    //开启新线程，解析url获取bmp图
    @WorkerThread
    fun fetchStory(url: String): Bitmap? {
        val response: Response<ResponseBody> = redBookApi.fetchUrlBytes(url).execute()
        val bitmap = response.body()?.byteStream()?.use(BitmapFactory::decodeStream)
        Log.i(TAG, "Decode bitmap=$bitmap from Response=$response")
        return bitmap
    }


}