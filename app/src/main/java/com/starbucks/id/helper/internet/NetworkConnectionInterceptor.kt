package com.starbucks.id.helper.internet

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by Angga N P on 2/26/2019.
 */

abstract class NetworkConnectionInterceptor : Interceptor {

    abstract fun isInternetAvailable(): Boolean

    abstract fun onInternetUnavailable()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (!isInternetAvailable()) {
            onInternetUnavailable()
        }
        return chain.proceed(request)
    }

}