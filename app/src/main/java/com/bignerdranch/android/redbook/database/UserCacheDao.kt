package com.bignerdranch.android.redbook.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bignerdranch.android.redbook.entity.UserCache
import java.util.*

@Dao
interface UserCacheDao {

    @Query("SELECT * FROM usercache")
    fun getUserCaches(): LiveData<List<UserCache>>

    @Query("SELECT * FROM usercache WHERE id=(:id)")
    fun getUserCache(id: UUID): LiveData<UserCache?>

    @Query("SELECT * FROM usercache ORDER BY lastTime DESC LIMIT 1")
    fun getLatestUserCache():LiveData<UserCache?>

    @Update
    fun updateUserCache(userCache: UserCache)

    @Insert
    fun addUserCache(userCache: UserCache)

}