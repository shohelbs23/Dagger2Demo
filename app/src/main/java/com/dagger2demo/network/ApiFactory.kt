package com.dagger2demo.network

import android.content.Context
import com.dagger2demo.BuildConfig
import com.dagger2demo.R
import com.dagger2demo.utils.AppHelper
import com.google.gson.GsonBuilder
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object ApiFactory {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    private const val TIME_OUT = 30L

    private const val cacheSize: Long = 100 * 1024 * 1024 // 100 MB

    private lateinit var retrofit: Retrofit
    private lateinit var okHttpClient: OkHttpClient
    val gson by lazy { GsonBuilder().create() }

    /**
     * @return new service class instance with base url and the converter factories
     */
    fun <Service> createService(context: Context, serviceClass: Class<Service>, timeOut: Long = TIME_OUT): Service {
        return getRetrofit(context, timeOut).create(serviceClass)
    }

    /**
     * @return new service class instance with the provided base url and the converter factories
     */
    fun <Service> createService(
        context: Context,
        serviceClass: Class<Service>,
        baseUrl: String,
        timeOut: Long = TIME_OUT
    ): Service {
        return getRetrofit(context, baseUrl, timeOut).create(serviceClass)
    }

    /**
     * @return new retrofit instance with the base url and the converter factories
     */
    fun getRetrofit(context: Context, timeOut: Long = TIME_OUT): Retrofit {
        if (!::retrofit.isInitialized) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient(context, timeOut))
                .build()
        }

        return retrofit
    }

    /**
     * @return new retrofit instance with the provided base url and the converter factories
     */
    private fun getRetrofit(context: Context, baseUrl: String, timeOut: Long = TIME_OUT): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(getOkHttpClient(context, timeOut))
            .build()
    }

    /**
     * Creates OkHttpClient and add interceptors
     */
    private fun getOkHttpClient(context: Context, timeOut: Long = TIME_OUT): OkHttpClient {
        if (!::okHttpClient.isInitialized) {
            val okHttpBuilder = OkHttpClient.Builder()
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .addNetworkInterceptor(ResponseCacheInterceptor())
                .addInterceptor(OfflineResponseCacheInterceptor())
                .addInterceptor(HeaderInterceptor())
                .setCookieStore(/*context*/)


            okHttpBuilder.addInterceptor(
                LoggingInterceptor.Builder()
                    .loggable(BuildConfig.DEBUG)
                    .setLevel(Level.BASIC)
                    .log(Platform.INFO)
                    .tag(context.getString(R.string.app_name))
                    .request("Request")
                    .response("Response")
                    .executor(Executors.newSingleThreadExecutor())
                    .build()
            )

            okHttpBuilder.cache(Cache(context.cacheDir, cacheSize))

            okHttpClient = okHttpBuilder.build()
        }
        return okHttpClient
    }
}

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("AuthenticationToken", AppHelper.TOKEN)
                .build()
        )
    }
}
