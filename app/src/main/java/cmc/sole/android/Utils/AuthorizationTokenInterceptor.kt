package cmc.sole.android.Utils

import android.content.SharedPreferences
import android.util.Log
import cmc.sole.android.Login.NewTokenResponse
import cmc.sole.android.Login.NewTokenView
import cmc.sole.android.Login.TokenRetrofitInterface
import cmc.sole.android.Login.TokenService
import cmc.sole.android.getAccessToken
import cmc.sole.android.getRefreshToken
import com.example.geeksasaeng.Utils.NetworkModule
import com.sole.android.ApplicationClass.Companion.Authorization_TOKEN
import com.sole.android.ApplicationClass.Companion.mSharedPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback

class AuthorizationTokenInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        builder.addHeader(Authorization_TOKEN, "${getAccessToken()}")
        return chain.proceed(builder.build())
    }
}