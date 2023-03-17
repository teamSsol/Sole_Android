package cmc.sole.android.Home.MyPage

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import cmc.sole.android.R
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivityMyPageInfoSettingBinding

class MyPageInfoSettingActivity: BaseActivity<ActivityMyPageInfoSettingBinding>(ActivityMyPageInfoSettingBinding::inflate) {
    override fun initAfterBinding() {
        initEmailSetting()
        initTextWatcher()
        initClickListener()
    }

    private fun initEmailSetting() {
        // UPDATE: 카카오 이메일 불러오기
        var email = "wlals2987@naver.com"
        binding.myPageInfoEmailTv.text = email
    }

    private fun initTextWatcher() {
        binding.myPageInfoIntroEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                binding.myPageInfoIntroResult.text="${binding.myPageInfoIntroEt.length()}/50"

                if (binding.myPageInfoIntroEt.length() < 50) {
                    binding.myPageInfoSaveBtn.setBackgroundResource(R.drawable.default_button_o)
                    binding.myPageInfoSaveBtn.isEnabled = true
                    binding.myPageInfoIntroResult.setTextColor(Color.parseColor("#D3D4D5"))
                    binding.myPageInfoIntroCv.strokeColor = Color.parseColor("#D3D4D5")
                } else if (binding.myPageInfoIntroEt.length() >= 50) {
                    binding.myPageInfoSaveBtn.setBackgroundResource(R.drawable.default_button_x)
                    binding.myPageInfoSaveBtn.isEnabled = false
                    binding.myPageInfoIntroCv.strokeColor = ContextCompat.getColor(this@MyPageInfoSettingActivity, R.color.red)
                    binding.myPageInfoIntroResult.setTextColor(ContextCompat.getColor(this@MyPageInfoSettingActivity, R.color.red))
                }
            }
        })
    }

    private fun initClickListener() {
        binding.myPageInfoBackIv.setOnClickListener {
            finish()
        }

        binding.myPageInfoSaveBtn.setOnClickListener {
            // UPDATE: API 연동 필요
            finish()
        }

        binding.myPageInfoWithdrawal.setOnClickListener {
            val dialogWithdrawal = DialogMyPageWithdrawal()
            dialogWithdrawal.show(supportFragmentManager, "WithdrawalDialog")
        }
    }
}