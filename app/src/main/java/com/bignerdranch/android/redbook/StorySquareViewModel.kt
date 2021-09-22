package com.bignerdranch.android.redbook

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.android.redbook.api.RedBookFetcher
import com.bignerdranch.android.redbook.entity.StoryItem
import com.bignerdranch.android.redbook.entity.UserCache
import com.bignerdranch.android.redbook.util.StoryItemListGenerator

class StorySquareViewModel(private val app: Application) : AndroidViewModel(app) {

    lateinit var userInfo: UserCache

    //数据集
    val storyItemLiveData: LiveData<List<StoryItem>>

    //网络动作接口
    private val fetcher = RedBookFetcher()

    //搜索
    private val mutableSearchTerm = MutableLiveData<String>()


    val searchTerm: String
        get() = mutableSearchTerm.value ?: ""


    init {
//        mutableSearchTerm.value = QueryPreferences.getStoredQuery(app)

        val tempLiveData: MutableLiveData<List<StoryItem>> = MutableLiveData()
        tempLiveData.value = StoryItemListGenerator().generateStoryItemList()
        storyItemLiveData = tempLiveData
//        storyItemLiveData = fetcher.fetchStory()

/*        storyItemLiveData =
            Transformations.switchMap(mutableSearchTerm) { searchTerm ->
                if (searchTerm.isBlank()) {
                    //无搜索关键字，调用默认的获取story方法
                    fetcher.fetchStory()
                } else {
                    //按照关键字获取story
                    fetcher.searchStory(searchTerm)
                }
            }*/
    }


    fun fetchStoryItems(query: String = "") {
//        QueryPreferences.setStoredQuery(app, query)
        mutableSearchTerm.value = query
    }


}