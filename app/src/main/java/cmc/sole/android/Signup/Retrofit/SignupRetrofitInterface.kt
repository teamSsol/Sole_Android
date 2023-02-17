package cmc.sole.android.Signup.Retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignupRetrofitInterface {
    @POST("/api/members/nickname")
    fun checkNickname(
        @Body nicknameRequest: SignupNicknameRequest
    ): Call<Boolean>
}