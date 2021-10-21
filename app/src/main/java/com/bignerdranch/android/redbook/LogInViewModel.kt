package com.bignerdranch.android.redbook

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.redbook.api.LogResponse
import com.bignerdranch.android.redbook.database.UserCacheRepository
import com.bignerdranch.android.redbook.entity.UserCache

class LogInViewModel : ViewModel() {


    val userCacheLiveData: LiveData<UserCache?> = UserCacheRepository.get().getLatestUserCache()

    fun verify(uc: UserCache): LogResponse {
        //发送网络请求，验证name-pwd，获取UserInfo
        return LogResponse().apply {
            verify = true
            userInfo = UserCache()
        }

    }

}