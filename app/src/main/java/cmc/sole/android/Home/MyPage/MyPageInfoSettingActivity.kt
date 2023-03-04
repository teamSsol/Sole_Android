package cmc.sole.android.Home.MyPage

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import cmc.sole.android.R
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivityMyPageInfoSettingBinding

class MyPageInfoSettingActivity: BaseActivity<ActivityMyPageInfoSettingBinding>(ActivityMyPageInfoSettingBinding::inflate) {
    override fun initAfterBinding() {
        initTextWatcher()
    }

    private fun initTextWatcher() {
        binding.myPageInfoIntroEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (binding.myPageInfoEmailEt.text.length < 50) {
                    val on = ContextCompat.getColor(this@MyPageInfoSettingActivity, R.color.main)
                    binding.myPageInfoSaveBtn.setCardBackgroundColor(on)
                } else if (binding.myPageInfoEmailEt.text.length >= 50) {
                    val off = Color.parseColor("#D3D4D5")
                    binding.myPageInfoSaveBtn.setCardBackgroundColor(off)
                    binding.myPageInfoIntroCv.strokeColor = ContextCompat.getColor(this@MyPageInfoSettingActivity, R.color.red)
                }
            }
        })
    }
}