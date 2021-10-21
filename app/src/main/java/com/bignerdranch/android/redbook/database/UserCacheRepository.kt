package com.bignerdranch.android.redbook.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.bignerdranch.android.redbook.entity.UserCache
import java.lang.IllegalStateException
import java.util.concurrent.Executors

private const val DATABASE_NAME = "user-cache-database"

class UserCacheRepository private constructor(context: Context) {


    private val database: UserCacheDatabase = Room.databaseBuilder(
        context.applicationContext,
        UserCacheDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val userCahceDao = database.userCacheDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getLatestUserCache(): LiveData<UserCache?> = userCahceDao.getLatestUserCache()

    fun updateUserCache(uc: UserCache) {
        executor.execute {
            userCahceDao.updateUserCache(uc)
        }
    }

    fun addUserCache(uc: UserCache) {
        executor.execute {
            userCahceDao.addUserCache(uc)
        }
    }

    companion object {
        private var INSTANCE: UserCacheRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE =
                    UserCacheRepository(
                        context
                    )
            }
        }

        fun get(): UserCacheRepository {
            return INSTANCE
                ?: throw IllegalStateException("UserCacheRepository must be initialized")
        }
    }


}