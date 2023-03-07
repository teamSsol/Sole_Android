package cmc.sole.android.Signup.Retrofit

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path

interface SignupRetrofitInterface {

    @POST("/api/members/{provider}")
    fun socialCheck(
        @Path("provider") provider: String?,
        @Body signupCheckRequest: SignupCheckRequest
    ): Call<SignupCheckResponse>

    @POST("/api/members/nickname")
    fun checkNickname(
        @Body nicknameRequest: SignupNicknameRequest
    ): Call<Boolean>

    @Multipart
    @POST("/api/members/{provider}/signup")
    fun socialSignup(
        @Path("provider") provider: String?,
        @PartMap memberRequestDto: HashMap<String, RequestBody>,
        @Part multipartFile: MultipartBody.Part?
    ): Call<SignupSocialResponse>

    /*
    @Multipart
    @POST("/api/members/{provider}")
    fun socialLogin(
        @Path("provider") provider: String?,
        // @PartMap socialRequest: HashMap<String, RequestBody>,
        @Part socialRequest: MultipartBody.Part,
        @Part multipartFile: MultipartBody.Part?,
        // @Part("accessToken") accessToken: SignupSocialAccessToken
        // @PartMap accessToken: HashMap<String, RequestBody>
        @Part accessToken: MultipartBody.Part
    ): Call<SignupSocialResponse>


    @Multipart
    @POST("/api/members/{provider}")
    fun socialLogin2(
        @Path("provider") provider: String?,
        @PartMap socialRequest: HashMap<String, RequestBody>,
        @Part multipartFile: MultipartBody.Part?,
        @PartMap accessToken: HashMap<String, RequestBody>
    ): Call<SignupSocialResponse>

    @Multipart
    @POST("/api/members/{provider}")
    fun socialLogin3(
        @Path("provider") provider: String?,
        @Part socialRequest: MultipartBody.Part,
        @Part multipartFile: MultipartBody.Part?,
        @Part accessToken: MultipartBody.Part
    ): Call<SignupSocialResponse>

    @Multipart
    @POST("/api/members/{provider}")
    fun socialLogin(
        @Path("provider") provider: String?,
        // @PartMap socialRequest: HashMap<String, RequestBody>,
        @Part("memberRequestDto") memberRequestDto: SignupSocialRequest,
        @Part multipartFile: MultipartBody.Part?,
        @Part("accessToken") accessToken: SignupSocialAccessToken
    ): Call<SignupSocialResponse>
     */
}