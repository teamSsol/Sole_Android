package cmc.sole.android.Signup

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import cmc.sole.android.R
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivitySignupAgreeBinding

class SignupAgreeActivity: BaseActivity<ActivitySignupAgreeBinding>(ActivitySignupAgreeBinding::inflate) {

    private lateinit var signupAgreeVM: SignupViewModel

    override fun initAfterBinding() {
        signupAgreeVM = ViewModelProvider(this)[SignupViewModel::class.java]
        signupAgreeVM.setAccessToken(intent.getStringExtra("accessToken").toString())

        initRadioSetting()
        initClickListener()
    }

    private fun initRadioSetting() {
        checkOption(0, signupAgreeVM.getAll())
        checkOption(1, signupAgreeVM.getService())
        checkOption(2, signupAgreeVM.getPersonal())
        checkOption(3, signupAgreeVM.getMarketing())
    }

    private fun initClickListener() {
        binding.signupAgreeAllIv.setOnClickListener {
            checkEvent("all")
        }

        binding.signupAgreeAllTv.setOnClickListener {
            checkEvent("all")
        }

        binding.signupAgreeServiceIv.setOnClickListener {
            checkEvent("service")
        }

        binding.signupAgreeServiceTv.setOnClickListener {
            checkEvent("service")
        }

        binding.signupAgreeServiceArrow.setOnClickListener {
            changeActivity(SignupAgreeServiceActivity::class.java)
        }

        binding.signupAgreePersonalIv.setOnClickListener {
            checkEvent("personal")
        }

        binding.signupAgreePersonalTv.setOnClickListener {
            checkEvent("personal")
        }

        binding.signupAgreePersonalArrow.setOnClickListener {
            changeActivity(SignupAgreePersonalActivity::class.java)
        }

        binding.signupAgreeMarketingIv.setOnClickListener {
            checkEvent("marketing")
        }

        binding.signupAgreeMarketingTv.setOnClickListener {
            checkEvent("marketing")
        }

        binding.signupAgreeMarketingArrow.setOnClickListener {
            changeActivity(SignupAgreeMarketingActivity::class.java)
        }

        binding.signupAgreeNextBtn.setOnClickListener {
            if ((signupAgreeVM.getService() == true) && (signupAgreeVM.getPersonal() == true)) {
                changeActivity(SignupNicknameActivity::class.java)
            }
        }
    }

    private fun checkEvent(option: String) {
        when (option) {
            "all" -> {
                signupAgreeVM.setAll()

                if (signupAgreeVM.getAll() == true) {
                    checkOption(0, true)

                    checkOption(1, true)
                    if (signupAgreeVM.getService() != true) signupAgreeVM.setService()

                    checkOption(2, true)
                    if (signupAgreeVM.getPersonal() != true) signupAgreeVM.setPersonal()

                    checkOption(3, true)
                    if (signupAgreeVM.getMarketing() != true) signupAgreeVM.setMarketing()

                } else {
                    checkOption(0, false)

                    checkOption(1, false)
                    if (signupAgreeVM.getService() == true) signupAgreeVM.setService()

                    checkOption(2, false)
                    if (signupAgreeVM.getPersonal() == true) signupAgreeVM.setPersonal()

                    checkOption(3, false)
                    if (signupAgreeVM.getMarketing() == true) signupAgreeVM.setMarketing()
                }

                checkNext()
            }
            "service" -> {
                signupAgreeVM.setService()
                checkOption(1, signupAgreeVM.getService())
                checkAll()
                checkNext()
            }
            "personal" -> {
                signupAgreeVM.setPersonal()
                checkOption(2, signupAgreeVM.getPersonal())
                checkAll()
                checkNext()
            }
            else -> {
                signupAgreeVM.setMarketing()
                checkOption(3, signupAgreeVM.getMarketing())
                checkAll()
            }
        }
    }

    // SignupAgreeVM 에서 받아온 값이 True 인지 False 인지에 따라 이미지 설정
    private fun checkOption(order: Int, option: Boolean?) {
        if (order == 0) {
            if (option == true) binding.signupAgreeAllIv.setImageResource(R.drawable.ic_radio_check)
            else binding.signupAgreeAllIv.setImageResource(R.drawable.ic_radio_default)
        } else if (order == 1) {
            if (option == true) binding.signupAgreeServiceIv.setImageResource(R.drawable.ic_radio_check)
            else binding.signupAgreeServiceIv.setImageResource(R.drawable.ic_radio_default)
        } else if (order == 2) {
            if (option == true) binding.signupAgreePersonalIv.setImageResource(R.drawable.ic_radio_check)
            else binding.signupAgreePersonalIv.setImageResource(R.drawable.ic_radio_default)
        } else if (order == 3) {
            if (option == true) binding.signupAgreeMarketingIv.setImageResource(R.drawable.ic_radio_check)
            else binding.signupAgreeMarketingIv.setImageResource(R.drawable.ic_radio_default)
        }
    }

    // 전체 다 선택했는지 확인
    private fun checkAll(): Boolean {
        if ((signupAgreeVM.getService() == true) && (signupAgreeVM.getPersonal() == true) && (signupAgreeVM.getMarketing()) == true) {
            binding.signupAgreeAllIv.setImageResource(R.drawable.ic_radio_check)
            signupAgreeVM.setAll()
            return true
        } else if (signupAgreeVM.getAll() != false) {
            signupAgreeVM.setAll()
        }

        binding.signupAgreeAllIv.setImageResource(R.drawable.ic_radio_default)
        return false
    }

    // 다음으로 넘어가는 버튼 활성화/비활성화
    private fun checkNext(): Boolean {
        val on = ContextCompat.getColor(this, R.color.main)
        val off = Color.parseColor("#D3D4D5")

        val onText = ContextCompat.getColor(this, R.color.black)
        val offText = Color.parseColor("#FFFFFF")

        if ((signupAgreeVM.getService() == true) && (signupAgreeVM.getPersonal() == true)) {
            binding.signupAgreeNextBtn.setCardBackgroundColor(on)
            binding.signupAgreeNextTv.setTextColor(onText)
            return true
        }

        binding.signupAgreeNextBtn.setCardBackgroundColor(off)
        binding.signupAgreeNextTv.setTextColor(offText)
        return false
    }
}