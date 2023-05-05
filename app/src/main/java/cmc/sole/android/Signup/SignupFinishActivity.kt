package cmc.sole.android.Signup

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModelProvider
import cmc.sole.android.Login.LoginActivity
import cmc.sole.android.MainActivity
import cmc.sole.android.StartCourseTagActivity
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivitySignupAgreeBinding
import cmc.sole.android.databinding.ActivitySignupFinishBinding
import cmc.sole.android.databinding.ActivitySignupNicknameBinding

class SignupFinishActivity: BaseActivity<ActivitySignupFinishBinding>(ActivitySignupFinishBinding::inflate) {

    // UPDATE: 회원가입 API 연동 필요!
    override fun initAfterBinding() {
        initClickListener()
    }

    private fun initClickListener() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            changeActivity(StartCourseTagActivity::class.java)
            finishAffinity()
        }, 3000)
    }
}