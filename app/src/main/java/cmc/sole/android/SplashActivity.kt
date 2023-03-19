package cmc.sole.android

import android.os.Handler
import android.os.Looper
import android.util.Log
import cmc.sole.android.Login.LoginActivity
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivitySplashBinding
import com.kakao.sdk.common.util.Utility

class SplashActivity: BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {
    override fun initAfterBinding() {
        // 카카오 hashKey 를 얻기 위한 코드
        getKAKAOKeyHash()

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            changeActivity(LoginActivity::class.java)
            finish()
        }, 1500)
    }

    private fun getKAKAOKeyHash() {
        var keyHash = Utility.getKeyHash(this)
        Log.e("EXAMPLE", "해시 키 값 : $keyHash")
    }
}