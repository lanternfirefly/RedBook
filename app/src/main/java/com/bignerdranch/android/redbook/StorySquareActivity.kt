package com.bignerdranch.android.redbook

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class StorySquareActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_square)

        val isFragmentContainerEmpty = savedInstanceState == null
        //当没有缓存的已配置fragment时，配置fragment容器
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, StorySquareFragment.newInstance())
                .commit()
        }


    }

    //伴生对象封装调用
    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, StorySquareActivity::class.java)
    }

}