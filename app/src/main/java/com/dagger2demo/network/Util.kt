package com.dagger2demo.network

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.dagger2demo.BuildConfig
import com.google.gson.JsonParseException
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

fun isConnected(appContext: Context): Boolean {
    val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = connectivityManager.activeNetworkInfo
    return netInfo != null && netInfo.isConnected
}

fun <T> Single<T>.withScheduler(): Single<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.withScheduler(): Flowable<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<Response<T>>.onResponse(): Single<T> {
    return this.map { response ->
        if (response.isSuccessful) {
            response.body()
        } else {
            throw ApiException(response.code(), response.errorBody(), response.message())
        }
    }
}

fun <T> Flowable<Response<T>>.onResponse(): Flowable<T> {
    return this.map { response ->
        if (response.isSuccessful) {
            response.body()
        } else {
            throw ApiException(response.code(), response.errorBody(), response.message())
        }
    }
}

fun <T> Single<T>.onException(appContext: Context): Single<T> {
    return this.onErrorResumeNext {
        if(BuildConfig.DEBUG) {
            Log.e("error", it.message)
        }
        Single.create<T> { emitter ->
            when (it) {
                is ApiException -> emitter.onError(ErrorHandler.parseRequestException(appContext, it.code, it.errorBody))
                is JsonParseException -> emitter.onError(it)
                else -> emitter.onError(ErrorHandler.parseIOException(appContext))
            }
        }
    }
}

fun <T> Flowable<T>.onException(appContext: Context): Flowable<T> {
    return this.onErrorResumeNext { it: Throwable ->
        Flowable.create<T>({ emitter ->
            when (it) {
                is ApiException -> emitter.onError(ErrorHandler.parseRequestException(appContext, it.code, it.errorBody))
                is JsonParseException -> emitter.onError(it)
                else -> emitter.onError(ErrorHandler.parseIOException(appContext))
            }
        }, BackpressureStrategy.BUFFER)
    }
}
