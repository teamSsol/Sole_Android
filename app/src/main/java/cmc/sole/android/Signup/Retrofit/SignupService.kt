package cmc.sole.android.Signup.Retrofit

import android.util.Log
import cmc.sole.android.ErrorResponse
import com.example.geeksasaeng.Utils.NetworkModule
import com.google.gson.Gson
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupService {
    private lateinit var signupCheckView: SignupCheckView
    private lateinit var signupNicknameView: SignupNicknameView
    private lateinit var signupSocialView: SignupSocialView
    private lateinit var logoutView: LogoutView

    private val signupService = NetworkModule.getInstance()?.create(SignupRetrofitInterface::class.java)

    fun setSignupCheckView(signupCheckView: SignupCheckView) {
        this.signupCheckView = signupCheckView
    }
    fun setSignupNicknameView(signupNicknameView: SignupNicknameView) {
        this.signupNicknameView = signupNicknameView
    }
    fun setSignupSocialView(signupSocialView: SignupSocialView) {
        this.signupSocialView = signupSocialView
    }

    fun setLogoutView(logoutView: LogoutView) {
        this.logoutView = logoutView
    }

    fun signupCheck(body: SignupCheckRequest) {
        signupService?.socialCheck("kakao", body)?.enqueue(object: Callback<SignupCheckResponse> {
            override fun onResponse(call: Call<SignupCheckResponse>, response: Response<SignupCheckResponse>) {
                if (response.code() == 200) {
                    val signupCheckResponse = response.body()
                    if (signupCheckResponse?.success == true) {
                        signupCheckView.signupCheckSuccessView(signupCheckResponse)
                    } else {
                        val gson = Gson()
                        val errorResponse = gson.fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                        signupCheckView.signupCheckFailureView(errorResponse)
                    }
                } else {
                    if (response.errorBody() != null) {
                        val gson = Gson()
                        val errorResponse = gson.fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                        signupCheckView.signupCheckFailureView(errorResponse)
                    } else signupCheckView.signupCheckFailureView(null)
                }
            }
            override fun onFailure(call: Call<SignupCheckResponse>, t: Throwable) {
                Log.e("SIGNUP-SERVICE" , "signup check failure", t)
            }
        })
    }

    fun checkNickname(nickname: SignupNicknameRequest) {
        signupService?.checkNickname(nickname)?.enqueue(object: Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.code() == 200) {
                    val signupNicknameResponse = response.body()
                    if (signupNicknameResponse == false) {
                        signupNicknameView.signupNicknameSuccessView(signupNicknameResponse)
                    } else {
                        signupNicknameView.signupNicknameFailureView()
                    }
                } else {
                    Log.d("SIGNUP-SERVICE" , "nickname failure")
                }
            }
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.e("SIGNUP-SERVICE", "SignupService-CheckNickname-onFailure", t)
            }
        })
    }

    fun socialSignup(provider: String?, memberRequestDto: MultipartBody.Part, multipartFile: MultipartBody.Part?) {
        signupService?.socialSignup(provider, memberRequestDto, multipartFile)?.enqueue(object: Callback<SignupSocialResponse> {
            override fun onResponse(call: Call<SignupSocialResponse>, response: Response<SignupSocialResponse>) {
                if (response.code() == 200) {
                    val signupSocialResponse = response.body()
                    if (signupSocialResponse != null) {
                        signupSocialView.signupSocialSuccessView(signupSocialResponse)
                    }
                } else {
                    signupSocialView.signupSocialFailureView()
                }
            }
            override fun onFailure(call: Call<SignupSocialResponse>, t: Throwable) {
                Log.e("API-TEST", "SignupService-SocialLogin-onFailure", t)
            }
        })
    }

    fun logout() {
        signupService?.logout()?.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code() == 200) {
                    logoutView.setLogoutSuccessView()
                } else {
                    logoutView.setLogoutFailureView()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("SIGNUP-SERVICE", "SignupService-logout-onFailure", t)
            }
        })
    }
}