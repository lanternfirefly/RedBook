package com.bignerdranch.android.redbook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.redbook.database.StoryRepository
import com.bignerdranch.android.redbook.entity.StoryItem
import java.io.File
import java.util.*

class StoryDetailViewModel() : ViewModel() {

    private val storyRepository = StoryRepository.get()
    private val storyIdLiveData = MutableLiveData<UUID>()

    /*    val storyLiveData:LiveData<StoryItem?> =
            Transformations.switchMap(storyIdLiveData){storyId ->
                storyRepository.getStory(storyId)
            }*/
    private lateinit var story: StoryItem

    fun getStoryImageFile(storyItem: StoryItem): File {
        return storyRepository.getStoryImageFile(storyItem)
    }


}