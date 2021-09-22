package com.bignerdranch.android.redbook.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bignerdranch.android.redbook.entity.UserCache


@Database(entities = [UserCache::class], version = 1)
@TypeConverters(UserCacheTypeConverters::class)
abstract class UserCacheDatabase : RoomDatabase() {

    abstract fun userCacheDao(): UserCacheDao
}