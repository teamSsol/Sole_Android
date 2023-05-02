package cmc.sole.android.Login

import android.content.Intent
import android.util.Base64
import android.util.Log
import cmc.sole.android.*
import cmc.sole.android.CourseTag.placeCategories
import cmc.sole.android.CourseTag.transCategories
import cmc.sole.android.CourseTag.withCategories
import cmc.sole.android.Signup.Retrofit.SignupCheckRequest
import cmc.sole.android.Signup.Retrofit.SignupCheckResponse
import cmc.sole.android.Signup.Retrofit.SignupCheckView
import cmc.sole.android.Signup.Retrofit.SignupService
import cmc.sole.android.Signup.SignupAgreeActivity
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivityLoginBinding
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import org.json.JSONArray

class LoginActivity: BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate),
    SignupCheckView, NewTokenView {

    var TAG = "AUTO-LOGIN"

    private var fcmToken = ""
    private var accessToken = ""

    private lateinit var signupCheckService: SignupService
    private lateinit var tokenService: TokenService

    override fun initAfterBinding() {
        KakaoSdk.init(this, getString(R.string.kakao_api_key))

        // getPlayStoreHashKey()
        getFireBaseFCMToken()
        initClickListener()
        initRetrofitService()
        checkAutoLogin()
    }

    private fun getFireBaseFCMToken(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if(task.isSuccessful) {
                fcmToken = task.result?:""
                Log.d("TOKEN-CHECK", "fcmToken = $fcmToken")
            } else {
                Log.d("TOKEN-CHECK", "task = $task")
                Log.d("TOKEN-CHECK", "task.result = ${task.result}")
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
                        Log.d("SIGNUP-CHECK", "로그인 성공 ${token.accessToken}")
                        accessToken = token.accessToken
                        signupCheckService.signupCheck(SignupCheckRequest(accessToken, fcmToken))
                        // sendAccessToken(accessToken)
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
                    if (error != null) Log.e("EXAMPLE", "로그인 실패", error)
                    else if (token != null) {
                        Log.d("SIGNUP-CHECK", "로그인 성공 ${token.accessToken}")
                        accessToken = token.accessToken
                        signupCheckService.signupCheck(SignupCheckRequest(accessToken, fcmToken))
                        // sendAccessToken(accessToken)
                    }
                }
            }
        }
    }

    private fun initRetrofitService() {
        signupCheckService = SignupService()
        signupCheckService.setSignupCheckView(this)
        tokenService = TokenService()
        tokenService.setNewTokenView(this)
    }

    private fun checkAutoLogin() {
//        if (getAccessToken() != null) {
//            signupCheckService.signupCheck(SignupCheckRequest(getAccessToken().toString(), getFCMToken().toString()))
//        }
    }

    override fun signupCheckSuccessView(result: SignupCheckResponse) {
        Log.d("API-TEST", "result = $result")
        if (result.data.check) {
            // MEMO: 가입한 사용자
            saveAccessToken(result.data.accessToken)
            saveRefreshToken(result.data.refreshToken)
            saveFCMToken(fcmToken)

            Log.d(TAG, "getAccessToken = ${getAccessToken().toString()}")
            Log.d(TAG, "getFCMToken = ${getFCMToken()}")
            Log.d(TAG, "getRefreshToken = ${getRefreshToken()}")

            if (getAccessToken() != null) changeActivity(MainActivity::class.java)
            else changeActivity(MainActivity::class.java)
            finish()
        } else {
            // MEMO: 가입하지 않은 사용자
            var intent = Intent(this, SignupAgreeActivity::class.java)
            intent.putExtra("accessToken", accessToken)
            startActivity(intent)
//            changeActivity(SignupAgreeActivity::class.java)
        }
    }

    override fun signupCheckFailureView(code: Int) {
        Log.d(TAG, "실패 code $code")
        if (code == 400) {
            tokenService.getNewToken(getAccessToken().toString(), getRefreshToken().toString())
        }
    }

    override fun getNewTokenSuccessView(result: NewTokenResult) {
        Log.d(TAG, "getNewTokenSuccessView")
        Log.d(TAG, "result = $result")
    }

    override fun getNewTokenFailureView() {
        Log.d(TAG, "getNewToken 실패")
    }
}