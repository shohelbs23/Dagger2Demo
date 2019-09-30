package com.dagger2demo.di.module

import androidx.lifecycle.ViewModelProvider
import com.dagger2demo.ui.base.AppViewModelFactory
import dagger.Binds
import dagger.Module

@Module
 abstract class ViewModelModule {

    @Binds
    abstract fun provideViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}