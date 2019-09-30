package com.dagger2demo.di.module

import com.dagger2demo.MainActivity
import com.dagger2demo.di.scopes.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
 abstract class ActivitiyBuilders {

@ActivityScope
@ContributesAndroidInjector
abstract fun bindMainactivity() : MainActivity


}