package com.bignerdranch.android.redbook

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.bignerdranch.android.redbook.api.RedBookFetcher

class StorySquareViewModel(private val app: Application) : AndroidViewModel(app) {

    //数据集
    val storyItemLiveData: LiveData<List<StoryItem>>
    //网络动作接口
    private val fetcher = RedBookFetcher()
    //搜索
    private val mutableSearchTerm = MutableLiveData<String>()

    val searchTerm:String
        get() = mutableSearchTerm.value ?: ""


    init {
//        mutableSearchTerm.value = QueryPreferences.getStoredQuery(app)

        storyItemLiveData =
            Transformations.switchMap(mutableSearchTerm) { searchTerm ->
                if (searchTerm.isBlank()) {
                    //无搜索关键字，调用默认的获取story方法
                    fetcher.fetchStory()
                } else {
                    //按照关键字获取story
                    fetcher.searchStory(searchTerm)
                }
            }
    }


    fun fetchStoryItems(query: String = "") {
//        QueryPreferences.setStoredQuery(app, query)
        mutableSearchTerm.value = query
    }



}