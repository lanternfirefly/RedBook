package com.bignerdranch.android.redbook

import com.google.gson.annotations.SerializedName

data class StoryItem(
    var id: Int = 0,
    //标题
    var title: String = "",
    //正文内容
    var text: String = "",
    //图片url
    @SerializedName("url")
    var imgUrl: String = "",
    //浏览数量
    var viewNum: Int = 0,
    //点赞数量
    var likeNum: Int = 0,
    //收藏数量
    var collectNum: Int = 0,
    //分享者昵称
    var nickName: String = "",
    //分享者头像图片url
    @SerializedName("icon")
    var iconUrl: String = "",
    //创建时间
    var createTime: String = ""
)