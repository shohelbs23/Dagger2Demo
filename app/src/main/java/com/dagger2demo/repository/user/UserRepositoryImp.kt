package com.dagger2demo.repository.user

import android.content.Context
import com.dagger2demo.di.qualifires.ApplicationContext
import com.dagger2demo.model.UserDM
import com.dagger2demo.network.onException
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(@ApplicationContext private val context: Context,private val apiSercive:UserService):UserRepository {
    override fun getUser(userId:Int) : Single<UserDM>{
        return apiSercive.getUser(userId).onException(context)
    }
}