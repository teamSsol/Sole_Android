package cmc.sole.android.Login

import android.util.Log
import cmc.sole.android.DefaultResponse
import cmc.sole.android.Home.Retrofit.*
import cmc.sole.android.saveAccessToken
import cmc.sole.android.saveRefreshToken
import com.example.geeksasaeng.Utils.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TokenService {
    private val tokenService = NetworkModule.getInstance()?.create(TokenRetrofitInterface::class.java)

    private lateinit var getNewTokenView: NewTokenView

    fun setNewTokenView(getNewTokenView: NewTokenView) {
        this.getNewTokenView = getNewTokenView
    }

    // fun getNewToken(authorization: String, refresh: String) {
        // tokenService?.getNewToken(authorization, refresh)?.enqueue(object: Callback<NewTokenResponse> {
    fun getNewToken(refresh: String) {
        tokenService?.getNewToken(refresh)?.enqueue(object: Callback<NewTokenResponse> {
            override fun onResponse(
                call: Call<NewTokenResponse>,
                response: Response<NewTokenResponse>
            ) {
                Log.d("API-TEST", "getNewToken")
                Log.d("API-TEST", "response = $response")
                Log.d("API-TEST", "response.code = ${response.code()}")
                if (response.code() == 200) {
                    val resp = response.body()
                    if (resp?.success == true) {
                        saveAccessToken(resp.data.accessToken)
                        saveRefreshToken(resp.data.refreshToken)
                        // getNewTokenView.getNewTokenSuccessView(resp.data)
                    } else {
                        getNewTokenView.getNewTokenFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<NewTokenResponse>, t: Throwable) {
                Log.e("TOKEN-SERVICE", "TOKEN-SERVICE-GET-NEW-TOKEN-FAILURE", t)
            }
        })
    }
}