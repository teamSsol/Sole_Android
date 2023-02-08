package cmc.sole.android

import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivityLoginBinding

class LoginActivity: BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    override fun initAfterBinding() {
        initClickListener()
    }

    private fun initClickListener() {
        // 임시로 넣은 부분
        // UPDATE: 카카오 로그인으로 업데이트 필요
        binding.loginKakaoCv.setOnClickListener {
            changeActivity(SignupAgreeActivity::class.java)
        }
    }
}