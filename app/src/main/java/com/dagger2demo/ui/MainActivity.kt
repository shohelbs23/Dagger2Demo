package com.dagger2demo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dagger2demo.R
import com.dagger2demo.callback.CommunicatorFragmentInterface
import com.dagger2demo.base.BaseActivity
import com.dagger2demo.ui.user.UserFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : BaseActivity(), CommunicatorFragmentInterface, HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchFragmentInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContentFragment(UserFragment(),false)
    }

    override fun setContentFragment(fragment: Fragment?, addToBackStack: Boolean) {
        if (!isFinishing){
            if (fragment == null) {
                return
            }
            supportFragmentManager?.let {
                val fragmentManager = supportFragmentManager
                val currentFragment = fragmentManager.findFragmentById(R.id.home_layout)

                if (currentFragment != null && fragment.javaClass.isAssignableFrom(currentFragment.javaClass)) {
                    return
                }

                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.home_layout, fragment, fragment.javaClass.name)
                if (addToBackStack) {
                    fragmentTransaction.addToBackStack(fragment.javaClass.name)
                }
                fragmentTransaction.commit()
                fragmentManager.executePendingTransactions()
            }
        }
    }

    override fun addContentFragment(fragment: Fragment?, addToBackStack: Boolean) {
        if (!isFinishing){
            if (fragment == null) {
                return
            }
            supportFragmentManager?.let {
                val fragmentManager = supportFragmentManager
                val currentFragment = fragmentManager.findFragmentById(R.id.home_layout)

                if (currentFragment != null && fragment.javaClass.isAssignableFrom(currentFragment.javaClass)) {
                    return
                }

                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.add(R.id.home_layout, fragment, fragment.javaClass.name)
                if (addToBackStack) {
                    fragmentTransaction.addToBackStack(fragment.javaClass.name)
                }
                fragmentTransaction.commitAllowingStateLoss()
                fragmentManager.executePendingTransactions()
            }
        }
    }
}
