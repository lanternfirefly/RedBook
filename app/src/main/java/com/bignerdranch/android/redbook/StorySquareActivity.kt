package com.bignerdranch.android.redbook

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.bignerdranch.android.redbook.entity.StoryItem
import com.bignerdranch.android.redbook.entity.UserCache

class StorySquareActivity : AppCompatActivity(), StorySquareFragment.Callbacks,
    LogInFragment.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_square)



        if (Build.VERSION.SDK_INT >= 21) {
            //顶部任务栏透明
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
            //顶部任务栏半透明
            /*window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)*/
        }

        val isFragmentContainerEmpty = savedInstanceState == null
        //当没有缓存的已配置fragment时，配置fragment容器
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, LogInFragment.newInstance(1))
//                .add(R.id.fragmentContainer, StorySquareFragment.newInstance())
                .commit()
        }
    }

    override fun onLogIn(user: UserCache) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, StorySquareFragment.newInstance(user))
            .commit()
    }

    override fun onStorySelected(item: StoryItem) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, StoryDetailFragment.newInstance(item))
            .addToBackStack(null)
            .commit()
    }

    //伴生对象封装调用
    companion object {
        fun newIntent(context: Context): Intent =
            Intent(context, StorySquareActivity::class.java)
    }


}