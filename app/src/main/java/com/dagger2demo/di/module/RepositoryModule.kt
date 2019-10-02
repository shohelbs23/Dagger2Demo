package com.dagger2demo.di.module

import com.dagger2demo.repository.user.UserRepository
import com.dagger2demo.repository.user.UserRepositoryImp
import dagger.Binds
import dagger.Module

@Module
 abstract class RepositoryModule {

 @Binds
 abstract fun bindUserRepository(userRepositoryImp: UserRepositoryImp):UserRepository
}