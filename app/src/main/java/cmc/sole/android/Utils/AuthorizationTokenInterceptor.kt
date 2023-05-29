package cmc.sole.android.Utils

import android.content.SharedPreferences
import android.util.Log
import cmc.sole.android.Login.NewTokenResponse
import cmc.sole.android.Login.NewTokenView
import cmc.sole.android.Login.TokenRetrofitInterface
import cmc.sole.android.Login.TokenService
import cmc.sole.android.Signup.Retrofit.SignupRetrofitInterface
import cmc.sole.android.Signup.Retrofit.SignupService
import cmc.sole.android.getAccessToken
import cmc.sole.android.getRefreshToken
import cmc.sole.android.saveAccessToken
import cmc.sole.android.saveRefreshToken
import com.example.geeksasaeng.Utils.NetworkModule
import com.sole.android.ApplicationClass
import com.sole.android.ApplicationClass.Companion.Authorization_TOKEN
import com.sole.android.ApplicationClass.Companion.mSharedPreferences
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create

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


    /*
    MEMO: 1. accessToken 만료 -> response.code == 401인 경우
    MEMO: 2. 이전에 저장한 RefreshToken을 이용해 새로운 AccessToken을 받아옴
    MEMO: 3. 동시에 새로운 refreshToken 받아오기 -> getNewToken API 연동
     */

    private fun updateRefreshToken() {
        val client: OkHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(AuthorizationTokenInterceptor())
            .build()

        var retrofit = Retrofit.Builder()
            .baseUrl(ApplicationClass.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(NetworkModule.gson))
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(client)
            .build()

        var service = retrofit.create(TokenService::class.java)
        return service.getNewToken(getRefreshToken().toString())
    }

    // MEMO: 일반적인 경우
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        builder.addHeader(Authorization_TOKEN, "${getAccessToken()}")

        request = chain.request()
        response = chain.proceed(builder.build())

        // Log.d("API-TEST", "AuthorizationTokenInterceptor Response = $response")
        // Log.d("API-TEST", "AuthorizationTokenInterceptor Response.body = ${response.body}")
        if (response.code == 401) {
            Log.d("API-TEST", "intercept ${response}")
            Log.d("API-TEST", "updateRefreshToken = ${updateRefreshToken()}")
        }

        /*
        {
            "code": "AUTH001",
            "success": false,
            "message": "만료된 엑세스 토큰입니다",
            "timestamp": "2023-05-28T15:40:10.668203",
            "status": 401
        }
         */

        return response
    }
}