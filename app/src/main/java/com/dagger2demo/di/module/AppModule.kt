package com.dagger2demo.di.module

import android.app.Application
import android.content.Context
import com.dagger2demo.di.qualifires.ApplicationContext
import dagger.Binds
import dagger.Module

@Module
 abstract class  AppModule {
    @Binds
    @ApplicationContext
    abstract fun provideApplicationContext(application: Application): Context
}