package com.bignerdranch.android.redbook

import android.app.Application
import com.bignerdranch.android.redbook.database.UserCacheRepository

class RedBookApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        UserCacheRepository.initialize(this)
    }
}