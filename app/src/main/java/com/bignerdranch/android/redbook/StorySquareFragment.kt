package com.bignerdranch.android.redbook

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.redbook.entity.StoryItem
import com.bignerdranch.android.redbook.entity.UserCache
import kotlinx.android.synthetic.main.list_item_story.view.*
import java.util.*

private const val TAG = "StorySquareFragment"
private const val ARG_USER_ID = "user_id"
private const val ARG_USER_NAME = "user_name"
private const val ARG_USER_URL = "user_url"
private const val ARG_USER_EVENING = "user_evening"
private const val ARG_USER_TIME = "user_time"

class StorySquareFragment : Fragment() {

    private lateinit var toolbar: Toolbar
    private lateinit var storySquareViewModel: StorySquareViewModel
    private lateinit var storyRecyclerView: RecyclerView
    private lateinit var thumbnailDownloader: ThumbnailDownloader<StoryHolder>

    interface Callbacks {
        fun onStorySelected(item: StoryItem)
    }

    private var callbacks: Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)


        //由此作为提供者绑定ViewModel
        storySquareViewModel = ViewModelProviders.of(this).get(StorySquareViewModel::class.java)
        storySquareViewModel.userInfo = UserCache(
            arguments?.getSerializable(ARG_USER_ID) as UUID,
            arguments?.getSerializable(ARG_USER_NAME) as String,
            "",
            arguments?.getSerializable(ARG_USER_URL) as String,
            arguments?.getSerializable(ARG_USER_EVENING) as Boolean,
            arguments?.getSerializable(ARG_USER_TIME) as Long
        )
        //处理下载缩略图的response
        val responseHandler = Handler()
        thumbnailDownloader =
            ThumbnailDownloader(responseHandler) { storyHolder, bitmap ->
                val drawable = BitmapDrawable(resources, bitmap)
                storyHolder.bindDrawable(drawable)
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


        toolbar = view.findViewById(R.id.toolbar_ss) as Toolbar
        toolbar.inflateMenu(R.menu.fragment_story_square)
        toolbar.title=""


        //为recyclerView绑定容器
        storyRecyclerView = view.findViewById(R.id.story_recycler_view)
        //配置视图布局
        storyRecyclerView.layoutManager = GridLayoutManager(context, 2)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //生命周期监控
        storySquareViewModel.storyItemLiveData.observe(
            viewLifecycleOwner,
            Observer { storyItems ->
                //storyItems来自ViewModel中联网获取
                storyRecyclerView.adapter = StoryAdapter(storyItems)
            }
        )
    }

    override fun onStart() {
        super.onStart()
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.expanded_menu -> {
                    Log.d(TAG, "expanded_menu")
                    true
                }
                R.id.menu_item_search -> {
                    Log.d(TAG, "menu_item_search")
                    true
                }
                else -> {
                    Log.d(TAG, "other")
                    true
                }
            }
        }
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

    override fun onDetach() {
        //fragment与Activity失去关联
        super.onDetach()
        callbacks = null
    }



    //服务于recyclerView的ViewHolder
    //函数参数为itemView传值
    private inner class StoryHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {

        private lateinit var story: StoryItem

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.i(TAG, "click story id ${story.id}")
            callbacks?.onStorySelected(story)
        }

        fun bind(s: StoryItem) {
            this.story = s
            bindText(story.title ?: getString(R.string.text_load))
        }

        //bindImage
        val bindDrawable: (Drawable) -> Unit =
//            view.findViewById<ImageView>(R.id.story_img)::setImageDrawable
            view.story_img::setImageDrawable

        //bindText
        val bindText: (String) -> Unit =
//            view.findViewById<TextView>(R.id.story_title)::setText
            view.story_title::setText
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
            holder.bindText(getString(R.string.text_load))
            holder.bind(storyItem)
            thumbnailDownloader.queueThumbnail(holder, storyItem.imgUrl)
        }
    }


    companion object {
        fun newInstance(uc: UserCache = UserCache()): StorySquareFragment {
            val args = Bundle().apply {
                putSerializable(ARG_USER_ID, uc.id)
                putSerializable(ARG_USER_NAME, uc.username)
                putSerializable(ARG_USER_URL, uc.url)
                putSerializable(ARG_USER_EVENING, uc.isEvening)
                putSerializable(ARG_USER_TIME, uc.lastTime)
            }

            return StorySquareFragment().apply {
                arguments = args
            }

        }
    }
}