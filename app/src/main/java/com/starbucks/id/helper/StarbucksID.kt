package com.starbucks.id.helper


import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import android.content.Context
import android.net.ConnectivityManager

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.FirebaseMessaging
import com.starbucks.id.helper.internet.InternetConnectionListener
import com.starbucks.id.helper.internet.NetworkConnectionInterceptor
import com.starbucks.id.rest.ApiClient
import com.starbucks.id.rest.ApiInterface

/*
 * Created by Angga N P on 5/9/2018.
 */

class StarbucksID : Application(), LifecycleObserver {
    private var apiService: ApiInterface? = null
    private var mInternetConnectionListener: InternetConnectionListener? = null

    companion object {
        @get:Synchronized var instance: StarbucksID? = null
        var fg: Boolean = false
        var debug = false
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        FirebaseAnalytics.getInstance(this)
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

    }

    fun getApiService(): ApiInterface {
        if (apiService == null) {
            apiService = ApiClient.getApiService(object : NetworkConnectionInterceptor() {
                override fun isInternetAvailable(): Boolean {
                    return this@StarbucksID.isInternetAvailable()
                }

                override fun onInternetUnavailable() {
                    if (mInternetConnectionListener != null) {
                        mInternetConnectionListener!!.onInternetUnavailable()
                    }
                }
            }
            )
        }
        return apiService!!
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        fg = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        fg = true
    }

    fun setInternetConnectionListener(listener: InternetConnectionListener) {
        mInternetConnectionListener = listener
    }

    fun removeInternetConnectionListener() {
        mInternetConnectionListener = null
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}
