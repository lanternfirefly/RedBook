package com.bignerdranch.android.redbook.api

import com.bignerdranch.android.redbook.entity.StoryItem
import com.google.gson.annotations.SerializedName

class StoryResponse {
    @SerializedName("story")
    lateinit var storyItems: List<StoryItem>

    override fun toString(): String {
        return storyItems.toString()
    }
}