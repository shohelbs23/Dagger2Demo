package com.dagger2demo.base

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {
    internal val showLoader by lazy { SingleLiveEvent<Boolean>() }
    internal val toastMessage by lazy { SingleLiveEvent<String>() }
    internal val compositeDisposable by lazy { CompositeDisposable() }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun isNetworkAvailable(context: Context): Boolean {
        context.let {
            val connectivityManager = it.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return (networkInfo != null && networkInfo.isConnectedOrConnecting)
        }
        return false
    }
}