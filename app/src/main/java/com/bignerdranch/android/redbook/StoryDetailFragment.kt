package com.bignerdranch.android.redbook

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bignerdranch.android.redbook.entity.StoryItem

private const val ARG_STORY_ID = "story_id"
private const val TAG = "StoryDetailFragment"


class StoryDetailFragment : Fragment() {

    //    private lateinit var iv_preview: ImageView
//    private lateinit var plvv: PLVideoView
    private lateinit var vv_detail: VideoView
    private lateinit var iv_head: ImageView
    private lateinit var tv_name: TextView
    private lateinit var ib_like: ImageButton
    private lateinit var ib_focus: ImageButton
    private lateinit var tv_desc: TextView
    private lateinit var storyItemBinded: StoryItem
    private val vm: StoryDetailViewModel by lazy {
        ViewModelProviders.of(this).get(StoryDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        storyItemBinded = StoryItem(
            arguments?.getSerializable(ARG_STORY_ID) as Int,
            arguments?.getSerializable("story_title") as String,
            arguments?.getSerializable("story_descText") as String,
            arguments?.getSerializable("story_imgUrl") as String,
            arguments?.getSerializable("story_viewNum") as Int,
            arguments?.getSerializable("story_likeNum") as Int,
            arguments?.getSerializable("story_collectNum") as Int,
            arguments?.getSerializable("story_nickName") as String,
            arguments?.getSerializable("story_iconUrl") as String,
            arguments?.getSerializable("story_createTime") as String
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_story_detail, container, false)

        vv_detail = view.findViewById(R.id.story_img_video)
        iv_head = view.findViewById(R.id.icon_log_in)
        tv_name = view.findViewById(R.id.tv_detail_nick_name)
        ib_like = view.findViewById(R.id.btn_like_detail)
        ib_focus = view.findViewById(R.id.btn_focus)
        tv_desc = view.findViewById(R.id.tv_detail_desc)

        Log.d(TAG, "img url: ${storyItemBinded.imgUrl}")
//        iv_preview.setImageURI(Uri.parse(storyItemBinded.imgUrl))
//        iv_preview.setImageResource(R.drawable.bg_test_5)


//        val videoPath = "http://vjs.zencdn.net/v/oceans.mp4"
        val videoPath = "android.resource://${context?.packageName}/${R.raw.idolsvideo}"
        vv_detail.setVideoPath(videoPath)
        vv_detail.setMediaController(MediaController(context))

//        iv_head.setImageURI(Uri.parse(storyItemBinded.iconUrl))
        iv_head.setImageResource(R.drawable.bg_ump45)

        tv_name.text = storyItemBinded.nickName
        tv_desc.text = storyItemBinded.descText

        return view

    }

    override fun onStart() {
        super.onStart()

        /*iv_preview.setOnClickListener {
            Log.d(TAG, "iv_preview ")
        }*/

        ib_like.setOnClickListener {
            Log.d(TAG, "ib_like")
        }

        ib_focus.setOnClickListener {
            Log.d(TAG, "ib_focus")
        }
    }

    override fun onResume() {
        super.onResume()
        vv_detail.start()
    }

    override fun onPause() {
        super.onPause()
        vv_detail.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        vv_detail.stopPlayback()
    }

    companion object {
        fun newInstance(item: StoryItem): StoryDetailFragment {
            val args = Bundle().apply {
                putSerializable(ARG_STORY_ID, item.id)
                putSerializable("story_title", item.title)
                putSerializable("story_descText", item.descText)
                putSerializable("story_imgUrl", item.imgUrl)
                putSerializable("story_viewNum", item.viewNum)
                putSerializable("story_likeNum", item.likeNum)
                putSerializable("story_collectNum", item.collectNum)
                putSerializable("story_nickName", item.nickName)
                putSerializable("story_iconUrl", item.iconUrl)
                putSerializable("story_createTime", item.createTime)
            }

            return StoryDetailFragment().apply {
                arguments = args
            }
        }
    }


}