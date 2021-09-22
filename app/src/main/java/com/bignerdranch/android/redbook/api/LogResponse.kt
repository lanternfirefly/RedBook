package com.bignerdranch.android.redbook.api

import com.bignerdranch.android.redbook.entity.UserCache

class LogResponse {

    var verify: Boolean = false
    lateinit var userInfo: UserCache

    override fun toString(): String {
        return "{\n" +
                "verify: $verify\n" +
                "userInfo: $userInfo\n" +
                "}\n"
    }

}