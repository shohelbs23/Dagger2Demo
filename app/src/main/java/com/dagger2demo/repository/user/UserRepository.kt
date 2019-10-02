package com.dagger2demo.repository.user

import com.dagger2demo.model.UserDM
import io.reactivex.Single

interface UserRepository {
    fun getUser(userId:Int) :Single<UserDM>
}