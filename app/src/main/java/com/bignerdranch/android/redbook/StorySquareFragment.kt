package com.bignerdranch.android.redbook

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_story.view.*

private const val TAG = "StorySquareFragment"

class StorySquareFragment : Fragment() {

    private lateinit var storySquareViewModel: StorySquareViewModel
    private lateinit var storyRecyclerView: RecyclerView
    private lateinit var thumbnailDownloader: ThumbnailDownloader<StoryHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        retainInstance = true

        //由此作为提供者绑定ViewModel
        storySquareViewModel = ViewModelProviders.of(this).get(StorySquareViewModel::class.java)
        //处理下载缩略图的response
        val responseHandler = Handler()
        thumbnailDownloader =
            ThumbnailDownloader(responseHandler) { storyHolder, bitmap ->
                val drawable = BitmapDrawable(resources, bitmap)
                val text = ""
                storyHolder.bindDrawable(drawable)
                storyHolder.bindText(text)
            }
        //生命周期监控
        lifecycle.addObserver(thumbnailDownloader.fragmentLifecycleObserver)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //生命周期监控
        viewLifecycleOwner.lifecycle
            .addObserver(thumbnailDownloader.viewLifecycleObserver)
        //生成视图
        val view = inflater.inflate(R.layout.fragment_story_square, container, false)
        //为recyclerView绑定容器
        storyRecyclerView = view.findViewById(R.id.story_recycler_view)
        //配置视图布局
        storyRecyclerView.layoutManager = GridLayoutManager(context, 2)
        return view
//        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //生命周期监控
        storySquareViewModel.storyItemLiveData.observe(
            viewLifecycleOwner,
            Observer { storyItems ->
                storyRecyclerView.adapter = StoryAdapter(storyItems)
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //移除生命周期监控
        viewLifecycleOwner.lifecycle
            .removeObserver(thumbnailDownloader.viewLifecycleObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        //移除生命周期监控
        lifecycle
            .removeObserver(thumbnailDownloader.fragmentLifecycleObserver)

    }


    //服务于recyclerView的ViewHolder
    //函数参数为itemView传值
    private class StoryHolder(view: View) : RecyclerView.ViewHolder(view) {
        //bindImage
        val bindDrawable: (Drawable) -> Unit =
            view.findViewById<ImageView>(R.id.story_img)::setImageDrawable

        //bindText
        val bindText: (String) -> Unit =
            view.findViewById<TextView>(R.id.story_title)::setText
//            view.story_title::setText
    }

    //服务于recyclerView的Adapter
    private inner class StoryAdapter(private val storyItems: List<StoryItem>) :
        RecyclerView.Adapter<StoryHolder>() {

        //创建holder，绑定xml
        override fun onCreateViewHolder(
            parent: ViewGroup
            , viewType: Int
        ): StoryHolder {
            val view = layoutInflater.inflate(
                R.layout.list_item_story,
                parent,
                false
            ) as View
            return StoryHolder(view)
        }

        //统计item数量
        override fun getItemCount(): Int = storyItems.size


        override fun onBindViewHolder(holder: StoryHolder, position: Int) {
            //获取当前的item
            val storyItem = storyItems[position]
            val placeholder: Drawable = ContextCompat.getDrawable(
                requireContext(),
                //缓冲过程中预显示的图片
                R.drawable.ic_launcher_background
            ) ?: ColorDrawable()
            //绑定预设图
            holder.bindDrawable(placeholder)
            //绑定预设标题
            holder.bindText(R.string.text_load.toString())
            thumbnailDownloader.queueThumbnail(holder, storyItem.imgUrl)
        }
    }


    companion object {
        fun newInstance() = StorySquareFragment()
    }
}