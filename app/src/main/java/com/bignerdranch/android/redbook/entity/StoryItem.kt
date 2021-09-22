package com.bignerdranch.android.redbook.entity

import com.bignerdranch.android.redbook.util.RandomIntUtil
import com.example.demo0701.util.TimeFormatUtil
import com.google.gson.annotations.SerializedName

data class StoryItem(
    var id: Int = 0,
    //标题
    var title: String = "",
    //正文内容
    var descText: String = "",
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


) {

    constructor(title: String, descText: String, url: String, nickName: String, icon: String) : this(
        RandomIntUtil.getRandomInt(),
        title, descText,
        url,
        0, 0, 0,
        nickName, icon,
        TimeFormatUtil.getFormatTimeStr()
    ) {

    }


    override fun toString(): String {
        return "{\n" +
                "id=$id, title='$title', \n" +
                "descText='$descText', \n" +
                "imgUrl='$imgUrl', \n" +
                "viewNum=$viewNum, likeNum=$likeNum, collectNum=$collectNum, \n" +
                "nickName='$nickName', iconUrl='$iconUrl', \n" +
                "createTime='$createTime'\n" +
                "}\n"
    }
}