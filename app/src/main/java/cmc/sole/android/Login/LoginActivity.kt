package cmc.sole.android.Login

import android.content.Intent
import android.util.Log
import cmc.sole.android.BuildConfig
import cmc.sole.android.CourseDetailActivity
import cmc.sole.android.MainActivity
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

class LoginActivity: BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate),
    SignupCheckView {

    private var fcmToken = ""
    private var accessToken = ""

    private lateinit var signupCheckService: SignupService

    override fun initAfterBinding() {
        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)

        // 카카오 hashKey 를 얻기 위한 코드
        // getKAKAOKeyHash()

        getFireBaseFCMToken()
        initClickListener()
        initRetrofitService()
    }

    private fun getKAKAOKeyHash() {
        var keyHash = Utility.getKeyHash(this)
        Log.e("EXAMPLE", "해시 키 값 : $keyHash")
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

    private fun initClickListener() {
        // MEMO: 홈으로 가기 위해 임시로 넣은 부분
        binding.splashLogoIv.setOnClickListener {
            changeActivity(MainActivity::class.java)
            finish()
        }

        binding.loginKakaoCv.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (error != null) Log.e("EXAMPLE", "로그인 실패", error)
                    else if (token != null) {
                        Log.d("TOKEN-CHECK", "로그인 성공 ${token.accessToken}")
                        accessToken = token.accessToken
                        signupCheckService.signupCheck(SignupCheckRequest(accessToken, fcmToken))
                        // sendAccessToken(accessToken)
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
                    if (error != null) Log.e("EXAMPLE", "로그인 실패", error)
                    else if (token != null) {
                        Log.d("TOKEN-CHECK", "로그인 성공 ${token.accessToken}")
                        accessToken = token.accessToken
                        signupCheckService.signupCheck(SignupCheckRequest(accessToken, fcmToken))
                        // sendAccessToken(accessToken)
                    }
                }
            }
        }
    }

    private fun sendAccessToken(accessToken: String) {
        val intent = Intent(this, SignupAgreeActivity::class.java)
        intent.putExtra("accessToken", accessToken)
        startActivity(intent)
    }

    private fun initRetrofitService() {
        signupCheckService = SignupService()
        signupCheckService.setSignupCheckView(this)
    }

    override fun signupCheckSuccessView(result: SignupCheckResponse) {
        Log.d("SIGNUP-SERVICE", result.toString())
    }

    override fun signupCheckFailureView() {
        showToast("회원가입 체크 실패")
    }
}