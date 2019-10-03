package com.dagger2demo.network

import com.orhanobut.hawk.Hawk
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException


const val COOKIES_PREFS_KEY = "com.example.android.appCookies"

class SendSavedCookiesInterceptor/*(private val context: Context)*/ : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val cookies: HashSet<String> = Hawk.get(COOKIES_PREFS_KEY, HashSet())

        cookies.forEach {
            builder.addHeader("Cookie", it)
        }

        return chain.proceed(builder.build())
    }
}

class SaveReceivedCookiesInterceptor/*(private val context: Context)*/ : Interceptor {

    @JvmField
    val cookieHeaderKey = "Set-Cookie"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())

        if (!Hawk.contains(COOKIES_PREFS_KEY)) {
            if (!originalResponse.headers(cookieHeaderKey).isEmpty()) {
                val cookies: HashSet<String> = Hawk.get(COOKIES_PREFS_KEY, HashSet())

                originalResponse.headers(cookieHeaderKey).forEach {
                    cookies.add(it)
                }

                Hawk.put(COOKIES_PREFS_KEY, cookies)
            }
        }

        return originalResponse
    }
}

fun OkHttpClient.Builder.setCookieStore(/*context: Context*/): OkHttpClient.Builder {
    return this
            .addInterceptor(SendSavedCookiesInterceptor(/*context*/))
            .addInterceptor(SaveReceivedCookiesInterceptor(/*context*/))
}

/*val cookies: HashSet<String> = PreferenceManager
    .getDefaultSharedPreferences(context)
    .getStringSet(COOKIES_KEY, HashSet())

PreferenceManager
    .getDefaultSharedPreferences(context)
    .edit()
    .putStringSet(cookiesKey, cookies)
    .apply()*/
