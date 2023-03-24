package cmc.sole.android.Utils

import android.util.Log
import cmc.sole.android.getAccessToken
import com.sole.android.ApplicationClass.Companion.Authorization_TOKEN
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthorizationTokenInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        builder.addHeader(Authorization_TOKEN, "${getAccessToken()}")
        Log.d("API-TEST", "${getAccessToken()}")
        return chain.proceed(builder.build())
    }
}