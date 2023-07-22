package cmc.sole.android.Login

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import cmc.sole.android.*
import cmc.sole.android.Signup.Retrofit.SignupCheckRequest
import cmc.sole.android.Signup.Retrofit.SignupCheckResponse
import cmc.sole.android.Signup.Retrofit.SignupCheckView
import cmc.sole.android.Signup.Retrofit.SignupService
import cmc.sole.android.Signup.SignupAgreeActivity
import cmc.sole.android.databinding.ActivityLoginBinding
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.user.UserApiClient
import com.sole.android.ApplicationClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginActivity: AppCompatActivity(),
    SignupCheckView {

    lateinit var binding: ActivityLoginBinding

    private var fcmToken: MutableLiveData<String> = MutableLiveData()
    private var accessToken = ""

    private lateinit var signupCheckService: SignupService
    private lateinit var tokenService: TokenService

    var test = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        KakaoSdk.init(this, getString(R.string.kakao_api_key))

        // getPlayStoreHashKey()
        initClickListener()
        initRetrofitService()
        getFireBaseFCMToken()

        fcmToken.observe(this, Observer<String?> { s ->
            saveFCMToken(fcmToken.value.toString())
        })

        setContentView(binding.root)
    }

    private fun getFireBaseFCMToken(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var taskResult = task.result
                if (taskResult != null) {
                    fcmToken.value = taskResult
                    saveFCMToken(fcmToken.value.toString())
                }
            }
        }
    }

    private fun getPlayStoreHashKey() {
        var sha1 = byteArrayOf(
            0xCA.toByte(), 0xCA.toByte(), 0x34.toByte(), 0x52.toByte(), 0xBC.toByte(), 0x68.toByte(), 0xB9.toByte(), 0x99.toByte(), 0xA4.toByte(), 0x42.toByte(), 0xC7.toByte(), 0xB0.toByte(),
            0xDA.toByte(), 0x20.toByte(), 0x90.toByte(), 0x38.toByte(), 0xBF.toByte(), 0x1B.toByte(), 0x66.toByte(), 0x3C.toByte())

        Log.d("STORE-HASH-KEY", Base64.encodeToString(sha1, Base64.NO_WRAP))
    }

    private fun initClickListener() {
        binding.loginKakaoCv.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (error != null) Log.e("EXAMPLE", "로그인 실패", error)
                    else if (token != null) {
                        accessToken = token.accessToken
                        saveKakaoAccessToken(accessToken)
                        signupCheckService.signupCheck(SignupCheckRequest(accessToken, getFCMToken().toString()))
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
                    if (error != null) Log.e("EXAMPLE", "로그인 실패", error)
                    else if (token != null) {
                        accessToken = token.accessToken
                        saveKakaoAccessToken(accessToken)
                        signupCheckService.signupCheck(SignupCheckRequest(accessToken, getFCMToken().toString()))
                    }
                }
            }
        }
    }

    private fun initRetrofitService() {
        signupCheckService = SignupService()
        signupCheckService.setSignupCheckView(this)
    }

    override fun signupCheckSuccessView(result: SignupCheckResponse) {
        if (result.data.check) {
            // MEMO: 가입한 사용자
            saveAccessToken(result.data.accessToken)
            saveRefreshToken(result.data.refreshToken)

            Log.d("API-TEST", "AccessToken = ${getAccessToken()}")
            Log.d("API-TEST", "RefreshToken = ${getRefreshToken()}")

            changeActivity(MainActivity::class.java)
            finish()
        } else {
            // MEMO: 가입하지 않은 사용자
            var intent = Intent(this, SignupAgreeActivity::class.java)
            intent.putExtra("accessToken", accessToken)
            startActivity(intent)
        }
    }

    override fun signupCheckFailureView(errorResponse: ErrorResponse?) {
        Log.d("API-TEST", "signupCheckFailureView errorResponse = $errorResponse")
    }

    private fun changeActivity(activity: Class<*>) {
        startActivity(Intent(this, activity))
    }
}