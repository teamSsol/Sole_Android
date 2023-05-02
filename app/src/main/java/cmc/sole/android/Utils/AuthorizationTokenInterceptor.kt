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
        val response = chain.proceed(builder.build())

        Log.d("API-TEST", "response.code = ${response.code}")
        Log.d("API-TEST", "response.body = ${response}")

        when (response.code) {

        }

        return response
    }

    /*
    val request = chain.request();
    val response = chain.proceed(request);

    when (response.code()) {
            400 -> {
                //Show Bad Request Error Message
            }
            401 -> {
                //Show UnauthorizedError Message
            }

            403 -> {
                //Show Forbidden Message
            }

            404 -> {
                //Show NotFound Message
            }

            // ... and so on

        }
        return response
     */
}