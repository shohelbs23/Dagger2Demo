package com.dagger2demo.ui.user

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dagger2demo.R
import com.dagger2demo.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_user.*


class UserFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("User","onCreateView Call")
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("User","onViewCreated Call")
        tv_sent.setOnClickListener {
            myCommunicator?.setContentFragment(UserDetailsFragment(),true)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("User","onActivityCreated Call")
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.d("User","onAttach Call")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("User","onDestroy Call")
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.d("User","onDestroy Call")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("User","onCreate Call")
    }


}