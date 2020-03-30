package com.starbucks.id.rest

import com.google.gson.GsonBuilder
import com.starbucks.id.helper.StarbucksID
import com.starbucks.id.helper.internet.NetworkConnectionInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by Angga N P on 11/29/2018.
 */

object ApiClient {
    val BASE_URL_END = "https://beta2.sbuxcard.com/"
//    val BASE_URL_END = "https://api.sbuxcard.com/";

    val CARDS_IMG_URL = BASE_URL_END + "images/cards/"
    val HTML_FILE = BASE_URL_END + "mcs/html/"
    val IMG_DEFAULT = CARDS_IMG_URL + "default.jpg"
    val IMG_DEFAULT_WN = CARDS_IMG_URL + "default_wn.jpg"
    var retrofit: Retrofit? = null
    private var apiService: ApiInterface? = null

    fun getApiService(itc: NetworkConnectionInterceptor): ApiInterface {
        if (apiService == null) apiService = provideRetrofit(itc).create(ApiInterface::class.java)
        return apiService!!


    }

    fun provideRetrofit(itc: NetworkConnectionInterceptor): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL_END)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(provideOkHttpClient(itc))
            .build()

    }

    private fun provideOkHttpClient(itc: NetworkConnectionInterceptor): OkHttpClient {
        return if (StarbucksID.debug) {
            val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(itc)
                    .addInterceptor(logging)
                    .build()
        } else {
            OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(itc)
                    .build()
        }
    }
}
