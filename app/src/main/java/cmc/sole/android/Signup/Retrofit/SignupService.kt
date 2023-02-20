package cmc.sole.android.Signup.Retrofit

import android.util.Log
import com.example.geeksasaeng.Utils.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupService {
    private val TAG = "SIGNUP-SERVICE"

    private lateinit var signupNicknameView: SignupNicknameView

    private val signupService = NetworkModule.getInstance()?.create(SignupRetrofitInterface::class.java)

    fun setSignupNicknameView(signupNicknameView: SignupNicknameView) {
        this.signupNicknameView = signupNicknameView
    }

    fun checkNickname(nickname: SignupNicknameRequest) {
        signupService?.checkNickname(nickname)?.enqueue(object: Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.code() == 200) {
                    val signupResponse = response.body()
                    if (signupResponse != null) {
                        signupNicknameView.signupNicknameSuccessView(signupResponse)
                    }
                } else {
                    signupNicknameView.signupNicknameFailureView()
                }
            }
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.e("SIGNUP-SERVICE", "SignupService-CheckNickname-onFailure", t)
            }
        })
    }
}