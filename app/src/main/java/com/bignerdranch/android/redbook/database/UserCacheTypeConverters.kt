package com.bignerdranch.android.redbook.database

import androidx.room.TypeConverter
import java.util.*

class UserCacheTypeConverters {
    /*
    * SQLite能够存储基本类型，但不知道如何存储UUID
    * 因此要做类型转换
    * 如将UUID转为String
    * */


    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }


}