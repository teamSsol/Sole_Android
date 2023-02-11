package cmc.sole.android

import android.os.Handler
import android.os.Looper
import cmc.sole.android.Login.LoginActivity
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivitySplashBinding

class SplashActivity: BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {
    override fun initAfterBinding() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            changeActivity(LoginActivity::class.java)
            finish()
        }, 1500)
    }
}