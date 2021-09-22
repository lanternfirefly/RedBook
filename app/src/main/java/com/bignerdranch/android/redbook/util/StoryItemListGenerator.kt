package com.bignerdranch.android.redbook.util

import com.bignerdranch.android.redbook.entity.StoryItem

class StoryItemListGenerator {

    private var storyList: MutableList<StoryItem> = mutableListOf<StoryItem>(
        StoryItem(
            "抽象3D图片", "来自图虫创意",
            "https://cdn3-banquan.ituchong.com/weili/l/917898368785448986.jpg",
            "cocoon", "iconUrl"
        ),
        StoryItem(
            "抽象3D图片", "来自图虫创意",
            "https://cdn9-banquan.ituchong.com/weili/l/919833990281560162.jpg",
            "cocoon", "iconUrl"
        ),
        StoryItem(
            "小巷", "来自花瓣网",
            "https://hbimg.huabanimg.com/f16f06ffe0a8cfc50e900598fbd4482c600d2084f1d89-MlwKpR_fw658/format/jpg",
            "nickName", "iconUrl"
        ),
        StoryItem(
            "乔巴", "来自花瓣网",
            "https://hbimg.huabanimg.com/a0078bfda7a52222f2a039acb79311d3741d934e1dc5f-7w7qcH_fw658/format/jpg",
            "nickName", "iconUrl"
        ),
        StoryItem(
            "人像", "由某位韩国画师创作",
            "https://wx2.sinaimg.cn/mw690/926c15c8gy1g82di6vnb2j20u016b7ht.jpg",
            "nickName", "iconUrl"
        ),
        StoryItem(
            "插画", "来自花瓣网",
            "https://hbimg.huabanimg.com/de04014bc0ed35df3e2db6a392bd1a44aaa7fd9250c77-w1lRdd_fw658/format/jpg",
            "nickName", "iconUrl"
        ),
        StoryItem(
            "春分", "来自花瓣网",
            "https://hbimg.huabanimg.com/5507bae5a473c3ccd52674efd2b1386559e2b6f115f986-bUgKpv_fw658/format/jpg",
            "nickName", "iconUrl"
        ),
        StoryItem(
            "奖牌", "来自花瓣网",
            "https://hbimg.huabanimg.com/1e8ea9425927b15cf4cca39b5486437919d1f642a2f9-6hoKeY_fw658/format/jpg",
            "agiya", "iconUrl"
        )
    )

    private var storePath:String

    init {
        storePath=""
    }

    fun generateStoryItemList():List<StoryItem> {
        return storyList
    }


}