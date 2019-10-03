package com.dagger2demo.ui

import android.app.Activity
import android.content.Context
import androidx.multidex.MultiDexApplication
import com.dagger2demo.di.component.DaggerAppComponent
import com.orhanobut.hawk.Hawk
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MyApp :MultiDexApplication(),HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)

        Hawk.init(this).build()
    }

    companion object {

        private const val TAG = "App"

        @get:Synchronized
        lateinit var instance: MyApp

        fun getApplication(activity: Activity): MyApp {
            return activity.application as MyApp
        }

        fun get(context: Context): MyApp {
            return context.applicationContext as MyApp
        }
    }

}