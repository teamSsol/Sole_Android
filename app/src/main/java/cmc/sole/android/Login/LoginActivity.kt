package cmc.sole.android.Login

import android.util.Log
import cmc.sole.android.BuildConfig
import cmc.sole.android.Signup.SignupAgreeActivity
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivityLoginBinding
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient

class LoginActivity: BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

    override fun initAfterBinding() {
        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)

        // 카카오 hashKey 를 얻기 위한 코드
        // getKAKAOKeyHash()

        initClickListener()
    }

    private fun getKAKAOKeyHash() {
        var keyHash = Utility.getKeyHash(this)
        Log.e("EXAMPLE", "해시 키 값 : $keyHash")
    }

    private fun initClickListener() {
        // 임시로 넣은 부분
        // UPDATE: 카카오 로그인으로 업데이트 필요
        binding.loginKakaoCv.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (error != null) {
                        Log.e("EXAMPLE", "로그인 실패", error)
                    }
                    else if (token != null) {
                        showLog("EXAMPLE", "로그인 성공 ${token.accessToken}")

                        changeActivity(SignupAgreeActivity::class.java)
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
                    if (error != null) {
                        Log.e("EXAMPLE", "로그인 실패", error)
                    }
                    else if (token != null) {
                        showLog("EXAMPLE", "로그인 성공 ${token.accessToken}")

                        changeActivity(SignupAgreeActivity::class.java)
                    }
                }
            }
        }
    }
}