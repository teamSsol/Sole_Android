package cmc.sole.android.Signup.Retrofit

import android.util.Log
import com.example.geeksasaeng.Utils.NetworkModule
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.Part
import retrofit2.http.Path

class SignupService {
    private val TAG = "SIGNUP-SERVICE"

    private lateinit var signupCheckView: SignupCheckView
    private lateinit var signupNicknameView: SignupNicknameView
    private lateinit var signupSocialView: SignupSocialView

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

    fun signupCheck(body: SignupCheckRequest) {
        signupService?.socialCheck("kakao", body)?.enqueue(object: Callback<SignupCheckResponse> {
            override fun onResponse(call: Call<SignupCheckResponse>, response: Response<SignupCheckResponse>) {
                Log.d("SIGNUP-SERVICE", "response = $response")
                if (response.code() == 200) {
                    val signupCheckResponse = response.body()
                    if (signupCheckResponse?.success == true) {
                        signupCheckView.signupCheckSuccessView(signupCheckResponse)
                    } else {
                        signupCheckView.signupCheckFailureView()
                    }
                } else {
                    Log.d("SIGNUP-SERVICE" , "signup check failure")
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

    // fun socialSignup(provider: String, memberRequestDto: HashMap<String, RequestBody>, multipartFile: MultipartBody.Part?) {
    fun socialSignup(provider: String, memberRequestDto: MultipartBody.Part, multipartFile: MultipartBody.Part?) {
        signupService?.socialSignup(provider, memberRequestDto, multipartFile)?.enqueue(object: Callback<SignupSocialResponse> {
            override fun onResponse(call: Call<SignupSocialResponse>, response: Response<SignupSocialResponse>) {
                Log.d("SIGNUP-SERVICE", "social-response = $response")
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
                Log.e("SIGNUP-SERVICE", "SignupService-SocialLogin-onFailure", t)
            }
        })
    }
}