package com.bignerdranch.android.redbook.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.demo0701.util.TimeFormatUtil
import java.util.*

@Entity
data class UserCache(
    //主键
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var username: String = "ump45",
    var password: String = "123456",
    //头像url
    var url: String = "",
    //夜间模式
    var isEvening: Boolean = false,
    //更新时间
    var lastTime: Long = TimeFormatUtil.getNowTimeStamp()

) {


    override fun toString(): String {
        return "{\n" +
                "id: $id\n" +
                "username: $username\n" +
                "password: $password\n" +
                "url: $url\n" +
                "isEvening: $isEvening\n" +
                "lastTime: $lastTime\n" +
                "}\n"
    }

}