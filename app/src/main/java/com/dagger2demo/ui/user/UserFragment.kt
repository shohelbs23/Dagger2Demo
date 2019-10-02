package com.dagger2demo.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dagger2demo.R
import com.dagger2demo.di.injectViewModel
import com.dagger2demo.base.BaseFragment
import com.dagger2demo.ui.user.user_details.UserDetailsFragment
import kotlinx.android.synthetic.main.fragment_user.*
import timber.log.Timber
import javax.inject.Inject


class UserFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var userViewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel=injectViewModel(viewModelFactory)
        tv_sent.setOnClickListener {
           userViewModel.getUser(5)
        }

        userViewModel.userModel.observe(this, Observer {
            Timber.d(it.toString())
        })
    }


}