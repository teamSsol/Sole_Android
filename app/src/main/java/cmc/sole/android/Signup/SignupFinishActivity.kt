package cmc.sole.android.Signup

import androidx.lifecycle.ViewModelProvider
import cmc.sole.android.MainActivity
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
        binding.signupFinishNextBtn.setOnClickListener {
            changeActivity(MainActivity::class.java)
            finishAffinity()
        }
    }
}