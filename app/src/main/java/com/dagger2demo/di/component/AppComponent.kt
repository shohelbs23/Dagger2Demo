package com.dagger2demo.di.component

import android.app.Application
import com.dagger2demo.MyApp
import com.dagger2demo.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class,AppModule::class])
interface AppComponent {

    fun inject(app:MyApp)

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application): Builder
        fun build() : AppComponent

    }
}