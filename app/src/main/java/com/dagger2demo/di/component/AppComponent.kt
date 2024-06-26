package com.dagger2demo.di.component

import android.app.Application
import com.dagger2demo.ui.MyApp
import com.dagger2demo.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class,AppModule::class,ActivitiyBuilders::class,ApiServiceModule::class,RepositoryModule::class,ViewModelModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application): Builder
        fun build() : AppComponent

    }

    fun inject(app: MyApp)
}