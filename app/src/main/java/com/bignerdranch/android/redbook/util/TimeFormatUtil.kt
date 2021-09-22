package com.example.demo0701.util

import java.text.SimpleDateFormat
import java.util.*

class TimeFormatUtil {

    companion object {

        fun getFormatTimeStr(): String {
            val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm")
            return sdf.format(Calendar.getInstance().time)
        }

        fun getNowTimeStamp(): Long = Date().time

    }


}