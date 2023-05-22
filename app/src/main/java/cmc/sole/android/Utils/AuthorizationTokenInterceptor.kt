package cmc.sole.android.Utils

import android.content.SharedPreferences
import android.util.Log
import cmc.sole.android.Login.NewTokenResponse
import cmc.sole.android.Login.NewTokenView
import cmc.sole.android.Login.TokenRetrofitInterface
import cmc.sole.android.Login.TokenService
import cmc.sole.android.getAccessToken
import cmc.sole.android.getRefreshToken
import cmc.sole.android.saveAccessToken
import cmc.sole.android.saveRefreshToken
import com.example.geeksasaeng.Utils.NetworkModule
import com.sole.android.ApplicationClass.Companion.Authorization_TOKEN
import com.sole.android.ApplicationClass.Companion.mSharedPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback

class AuthorizationTokenInterceptor: Interceptor {
    private lateinit var response: Response
    private lateinit var chain: Interceptor.Chain
    private lateinit var request: Request

    private fun refreshToken(): Response {
        // make an API call to get new token
        return if (response.isSuccessful) {
            // saveAccessToken(response.body.accessToken)
            // saveRefreshToken(response.body.refreshToken)

            val newRequest = request
                .newBuilder()
                .header("Authorization", getAccessToken()!!)
                .build()

            response = chain.proceed(newRequest)

            response
        } else {
            chain.proceed(request)
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        builder.addHeader(Authorization_TOKEN, "${getAccessToken()}")

        request = chain.request()
        response = chain.proceed(builder.build())

        if (response.code == 401) {
            return refreshToken()
        }

        return response
    }
}