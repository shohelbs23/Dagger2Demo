package com.dagger2demo.network

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.dagger2demo.model.BaseResponse
import com.dagger2demo.utils.AppHelper
import io.reactivex.SingleObserver

abstract class BaseApiObserver<T : BaseResponse>(private val context: Context) : SingleObserver<T> {

    abstract fun successResponse(response: T)

    override fun onSuccess(response: T) {
        when (response.responseCode) {
            ApiResponseCode.OPERATION_SUCCESSFUL -> successResponse(response)
            ApiResponseCode.RECORD_NOT_FOUNT -> successResponse(response)
//            ApiResponseCode.AUTHENTICATION_FAILED -> {
//                AppHelper.TOKEN=""
//                Toast.makeText(context, response.getCustomMessage(), Toast.LENGTH_SHORT).show()
//                val intent = Intent(context, LoginActivity::class.java)
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//                context.startActivity(intent)
//            }

            else -> {
                Toast.makeText(
                    context,
                    response.getCustomMessage(),
                    Toast.LENGTH_SHORT
                ).show()
                successResponse(response)
            }
        }
    }

    override fun onError(throwable: Throwable) {
        Toast.makeText(
            context,
            throwable.localizedMessage,
            Toast.LENGTH_SHORT
        ).show()
    }
}