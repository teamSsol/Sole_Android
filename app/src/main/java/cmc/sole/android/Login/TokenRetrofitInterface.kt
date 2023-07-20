package cmc.sole.android.Login

import cmc.sole.android.DefaultResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface TokenRetrofitInterface {
    // getNewToken
    @POST("/api/members/reissue")
    fun getNewToken(
        @Header("Authorization") authorization: String,
        @Header("Refresh") Refresh: String
    ): Call<NewTokenResponse>
}