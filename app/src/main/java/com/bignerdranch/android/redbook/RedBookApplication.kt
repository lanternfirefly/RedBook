package com.bignerdranch.android.redbook

import android.app.Application

class RedBookApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        UserCacheRepository.initialize(this)
    }
}