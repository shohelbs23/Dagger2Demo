package com.dagger2demo.network

import android.content.Context
import com.dagger2demo.R
import okhttp3.ResponseBody

/**
 * parse exception and generate proper message for the subscriber
 */
object ErrorHandler {

    /**
     * parse [RequestException] from IO exception returned from [okhttp3.OkHttpClient]
     */
    fun parseIOException(appContext: Context): RequestException {
        return if (!isConnected(appContext)) {
            RequestException(message = appContext.getString(R.string.msg_no_connection))
        } else {
            RequestException(message = appContext.getString(R.string.msg_unknown_exception))
        }
    }

    /**
     * parse [RequestException] from [okhttp3.OkHttpClient] response
     */
    fun parseRequestException(appContext: Context, code: Int, errorBody: ResponseBody?): RequestException {
        return errorBody?.let { body ->
            // parse error model from response
            val requestError: ApiError = getConverter(appContext, ApiError::class.java, body)

            // if error response does not contain any specific message use a generic error message from resource
            RequestException().apply {
                message = if (requestError.error.message.isBlank()) {
                    if (requestError.error.title.isBlank()) {
                        appContext.getString(R.string.msg_failed_try_again)
                    } else {
                        requestError.error.title
                    }
                } else {
                    requestError.error.message
                }

                httpCode = code
            }
        } ?: RequestException(message = appContext.getString(R.string.msg_failed_try_again))
    }

    /**
     * Convert error response to a data class
     */
    private fun <T> getConverter(appContext: Context, clazz: Class<T>, responseBody: ResponseBody): T {
        return try {
            ApiFactory.getRetrofit(appContext)
                    .responseBodyConverter<T>(clazz, arrayOfNulls<Annotation>(0))
                    .convert(responseBody)?.let { it } ?: throw Exception()
        } catch (ex: Exception) {
            clazz.newInstance()
        }
    }
}