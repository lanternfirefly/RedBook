package com.bignerdranch.android.redbook.api

import com.google.gson.annotations.SerializedName

class RedBookResponse {
    var code: Int = 102

    @SerializedName("data")
    lateinit var storyResponse: StoryResponse

    @SerializedName("log")
    lateinit var logResponse: LogResponse

    var msg: String = ""

    override fun toString(): String {
        return "{\n" +
                "code:${code},\n" +
                "data:${storyResponse},\n" +
                "log:${logResponse},\n" +
                "msg:${msg}\n" +
                "}\n"
    }

}