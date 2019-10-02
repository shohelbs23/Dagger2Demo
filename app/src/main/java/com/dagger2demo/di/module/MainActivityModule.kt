package com.dagger2demo.di.module

import com.dagger2demo.di.scopes.FragmentScope
import com.dagger2demo.ui.user.UserFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {


    @FragmentScope
    @ContributesAndroidInjector
    abstract fun bindUserFragment() :UserFragment

}