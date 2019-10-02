package com.dagger2demo.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dagger2demo.di.ViewModelKey
import com.dagger2demo.base.AppViewModelFactory
import com.dagger2demo.ui.user.UserViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
 abstract class ViewModelModule {

    @Binds
    abstract fun provideViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    abstract fun bindUserViewModel(userViewModel: UserViewModel) :ViewModel
}