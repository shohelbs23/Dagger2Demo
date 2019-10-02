package com.dagger2demo.di.module

import android.content.Context
import com.dagger2demo.di.qualifires.ApplicationContext
import com.dagger2demo.network.ApiFactory
import com.dagger2demo.repository.user.UserService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ApiServiceModule {

    @JvmStatic
    @Provides
    @Singleton
    fun provideUserService(@ApplicationContext application: Context): UserService =
        ApiFactory.createService(application, UserService::class.java)
}