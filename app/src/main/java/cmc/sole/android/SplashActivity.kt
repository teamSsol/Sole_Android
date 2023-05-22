package cmc.sole.android

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import cmc.sole.android.Login.LoginActivity
import cmc.sole.android.databinding.ActivitySignupAgreeMarketingBinding

import cmc.sole.android.databinding.ActivitySplashBinding
import com.kakao.sdk.common.util.Utility

class SplashActivity: AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)

        // 카카오 hashKey 를 얻기 위한 코드
        // getKAKAOKeyHash()

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            changeActivity(LoginActivity::class.java)
            finish()
        }, 1500)

        setContentView(binding.root)
    }

    private fun getKAKAOKeyHash() {
        var keyHash = Utility.getKeyHash(this)
        Log.e("EXAMPLE", "해시 키 값 : $keyHash")
    }

    private fun changeActivity(activity: Class<*>) {
        startActivity(Intent(this, activity))
    }
}