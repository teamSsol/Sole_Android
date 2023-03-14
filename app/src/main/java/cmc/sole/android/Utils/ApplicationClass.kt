package com.sole.android

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import cmc.sole.android.Utils.AuthorizationTokenInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass: Application() {
    companion object {
        const val Authorization_TOKEN: String = "Authorization"
        const val BASE_URL = "https://www.api-teamsole.site"
        const val TAG: String = "sole-pref" // Log, SharedPreference

        lateinit var retrofit: Retrofit
        lateinit var mSharedPreferences: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()

        var sharedPreferences = applicationContext.getSharedPreferences(TAG, Context.MODE_PRIVATE)
        mSharedPreferences = sharedPreferences

        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(30000, TimeUnit.MILLISECONDS)
            .connectTimeout(30000, TimeUnit.MILLISECONDS)
            // .addNetworkInterceptor(AuthorizationTokenInterceptor())
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}