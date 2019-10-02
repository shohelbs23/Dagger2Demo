package com.dagger2demo.repository.user

import com.dagger2demo.model.UserDM
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

//    @Headers("ApplyOfflineCache: true", "ApplyResponseCache: true")
//    @GET("home-contents/{outlet_id}")
//    fun getHomeContents(@Path("outlet_id") outletId: String): Single<Response<OutletResponse>>


    @GET("users/{userId}")
    fun getUser(@Path("userId") userId: Int): Single<UserDM>
}