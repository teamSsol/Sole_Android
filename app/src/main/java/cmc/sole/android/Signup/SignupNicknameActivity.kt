package cmc.sole.android.Signup

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.OnFocusChangeListener
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cmc.sole.android.R
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivitySignupNicknameBinding


class SignupNicknameActivity: BaseActivity<ActivitySignupNicknameBinding>(ActivitySignupNicknameBinding::inflate) {

    override fun initAfterBinding() {
        nicknameListener()
        initClickListener()
    }

    private fun nicknameListener() {
        binding.signupNicknameEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                /*
                if (binding.signupNicknameEt.selectionEnd > 9) {
                    nicknameOption(0, "닉네임은 최대 10자까지 작성이 가능해요.")
                } else if (binding.signupNicknameEt.selectionEnd <= 9) {
                    nicknameOption(1, null)
                }
                */
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            override fun afterTextChanged(p0: Editable?) {
                if (binding.signupNicknameEt.length() in 1..10) {
                    // UPDATE: API 연결해서 다른 사용자가 사용 중인 닉네임인지 연결
                    // MEMO: if (만약 사용 중인 닉네임이 아니라면)
                    nicknameOption(1, "사용 가능한 닉네임입니다.")
                    // MEMO: else if (만약 사용 중인 닉네임이 맞다면)
                    // nicknameOption(0, "이 닉네임은 이미 다른 사람이 사용하고 있어요.")
                } else if (binding.signupNicknameEt.length() > 10) {
                    nicknameOption(0, "닉네임은 최대 10자까지 작성이 가능해요.")
                }
            }
        })

        /*
        binding.signupNicknameEt.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (binding.signupNicknameEt.length() in 1..10) {
                    nicknameOption(1, null)
                }
            }
        }
        */
    }

    private fun initClickListener() {
        binding.signupNicknameNextBtn.setOnClickListener {
            if (binding.signupNicknameEt.length() in 1..10)
                changeActivity(SignupFinishActivity::class.java)
        }
    }

    private fun nicknameOption(option: Int, message: String?) {
        val on = ContextCompat.getColor(this@SignupNicknameActivity, R.color.main)
        val onText = ContextCompat.getColor(this@SignupNicknameActivity, R.color.black)
        val off = Color.parseColor("#D3D4D5")
        val offText = Color.parseColor("#FFFFFF")

        binding.signupNicknameErrorTv.text = message
        binding.signupNicknameResult.visibility = View.VISIBLE

        if (option == 0) {
            binding.signupNicknameResult.setImageResource(R.drawable.ic_signup_error)
            binding.signupNicknameNextBtn.setCardBackgroundColor(off)
            binding.signupNicknameNextTv.setTextColor(offText)
            binding.signupNicknameNextBtn.isEnabled = false
        } else if (option == 1) {
            binding.signupNicknameResult.setImageResource(R.drawable.ic_signup_check)
            binding.signupNicknameNextBtn.setCardBackgroundColor(on)
            binding.signupNicknameNextTv.setTextColor(onText)
            binding.signupNicknameNextBtn.isEnabled = true
        }
    }
}