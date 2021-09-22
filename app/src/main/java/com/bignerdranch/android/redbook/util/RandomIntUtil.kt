package com.bignerdranch.android.redbook.util

import kotlin.random.Random

class RandomIntUtil {

    companion object {
        fun getRandomInt(): Int {
            val i = Random.nextInt()
            return i
        }
    }
}