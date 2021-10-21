package com.bignerdranch.android.redbook.database

import android.content.Context
import com.bignerdranch.android.redbook.entity.StoryItem
import java.io.File
import java.lang.IllegalStateException
import java.util.concurrent.Executors

private const val DATABASE_NAME = "story-database"


class StoryRepository private constructor(context: Context) {

    private val executor = Executors.newSingleThreadExecutor()
    private val filesDir=context.applicationContext.filesDir

//    fun getStory()

    fun getStoryImageFile(storyItem: StoryItem):File=File(filesDir,storyItem.imgFileName)

    companion object {
        private var INSTANCE: StoryRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE =
                    StoryRepository(
                        context
                    )
            }
        }

        fun get(): StoryRepository {
            return INSTANCE
                ?: throw IllegalStateException("StoryRepository must be initialized")
        }
    }


}