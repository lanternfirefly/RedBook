package com.bignerdranch.android.redbook

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bignerdranch.android.redbook.database.UserCacheRepository
import com.bignerdranch.android.redbook.entity.UserCache
import com.bignerdranch.android.redbook.widget.DLRoundImageView


private const val TAG="LogInFragment"
private const val START_MODE = "mode"

class LogInFragment : Fragment() {


    private lateinit var dlriv_icon: DLRoundImageView
    private lateinit var et_name: EditText
    private lateinit var et_pwd: EditText
    private lateinit var btn_log_in: Button
    private var mode: Int = 1
    private val vm: LogInViewModel by lazy {
        ViewModelProviders.of(this).get(LogInViewModel::class.java)
    }
    private val userRepository: UserCacheRepository by lazy {
        UserCacheRepository.get()
    }

    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onLogIn(user: UserCache)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mode = arguments?.getSerializable(START_MODE) as Int
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_log_in, container, false)

        dlriv_icon = view.findViewById(R.id.icon_log_in)
        et_name = view.findViewById(R.id.editTextTextPersonName)
        et_pwd = view.findViewById(R.id.editTextTextPassword)
        btn_log_in = view.findViewById(R.id.btn_log_in)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")
    }


    override fun onStart() {
        super.onStart()

        if (mode == 1) {
            dlriv_icon.setImageResource(R.drawable.bg_ump45)
            //            dlriv.setImageBitmap()
            et_name.setText(vm.userCacheLiveData.value?.username)
            et_pwd.setText(vm.userCacheLiveData.value?.password)
        }


        btn_log_in.setOnClickListener { v ->
            val anim: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.alpha_anim)
            v.clearAnimation()
            v.startAnimation(anim)

            verifyLogIn()
        }
    }


    override fun onResume() {
        super.onResume()
        if (mode == 2) {
            verifyLogIn()
        }
    }

    override fun onDetach() {
        //fragment与Activity失去关联
        super.onDetach()
        callbacks = null
    }

    private fun verifyLogIn() {
        //send info to verify in ViewModel
        //success: save info in Repository and replace fragment in Activity
        var cache = UserCache(
            username = et_name.text.toString(),
            password = et_pwd.text.toString()
        )
        val logResponse = vm.verify(cache)
        if (!logResponse.verify) {
            Toast.makeText(requireContext(), "用户名或密码错误！", Toast.LENGTH_SHORT).show()
        } else {
            cache = logResponse.userInfo
            userRepository.addUserCache(cache)
            callbacks?.onLogIn(cache)
        }
    }

    companion object {
        fun newInstance(mode: Int = 1): LogInFragment {
            val args = Bundle().apply {
                putSerializable(START_MODE, mode)
            }
            return LogInFragment().apply {
                arguments = args
            }

        }
    }

}