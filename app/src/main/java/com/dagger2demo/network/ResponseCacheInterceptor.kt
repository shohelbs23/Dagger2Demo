package com.dagger2demo.network

import com.dagger2demo.ui.MyApp
import com.dagger2demo.utils.NetworkHelper
import com.dagger2demo.utils.NetworkHelper.isNetworkConnected
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


/**
 * Interceptor to cache data and maintain it for a minute.
 *
 *
 * If the same network request is sent within a minute,
 * the response is retrieved from cache.
 */

class ResponseCacheInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (java.lang.Boolean.valueOf(request.header("ApplyResponseCache"))) {
            val originalResponse = chain.proceed(chain.request())
            return originalResponse.newBuilder()
                    .removeHeader("ApplyResponseCache")
                    .header(
                            "Cache-Control",
                            if (isNetworkConnected(MyApp.instance)) "public, max-age=" + 60 else "public, only-if-cached, max-stale=" + 2419200
                    )
                    .build()
        } else {
            return chain.proceed(chain.request())
        }
    }
}