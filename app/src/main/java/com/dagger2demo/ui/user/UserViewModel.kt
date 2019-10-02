package com.dagger2demo.ui.user

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import com.dagger2demo.R
import com.dagger2demo.base.BaseViewModel
import com.dagger2demo.base.SingleLiveEvent
import com.dagger2demo.di.qualifires.ApplicationContext
import com.dagger2demo.model.UserDM
import com.dagger2demo.network.withScheduler
import com.dagger2demo.repository.user.UserRepository
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject


class UserViewModel @Inject constructor(@ApplicationContext private val context: Context,private val repository: UserRepository) : BaseViewModel() {

    val userModel by lazy { SingleLiveEvent<UserDM>() }

    @SuppressLint("CheckResult")
    fun getUser(userId:Int){
        if (!isNetworkAvailable(context)){
            Toast.makeText(context,context.getString(R.string.no_internet_msg), Toast.LENGTH_SHORT).show()
            return
        }

        repository.getUser(userId)
            .withScheduler()
            .doOnSubscribe { showLoader.value = true }
            .doAfterTerminate { showLoader.value = false }
            .subscribeWith(object : SingleObserver<UserDM> {
                override fun onSuccess(response: UserDM) {
                    userModel.value=response
                }

                override fun onSubscribe(disposable: Disposable) {
                    compositeDisposable.add(disposable)
                }

                override fun onError(throwable: Throwable) {
                    Toast.makeText(context, throwable.localizedMessage, Toast.LENGTH_SHORT).show()
                }

            })
    }
}
