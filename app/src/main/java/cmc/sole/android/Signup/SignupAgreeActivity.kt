package cmc.sole.android.Signup

import android.content.Intent
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cmc.sole.android.R
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivitySignupAgreeBinding
import cmc.sole.android.getAccessToken
import cmc.sole.android.saveAccessToken

class SignupAgreeActivity: BaseActivity<ActivitySignupAgreeBinding>(ActivitySignupAgreeBinding::inflate) {

    private lateinit var signupVM: SignupAgreeViewModel
    override fun initAfterBinding() {
        signupVM = ViewModelProvider(this)[SignupAgreeViewModel::class.java]

        initRadioSetting()
        initClickListener()
    }

    private fun initRadioSetting() {
        checkOption(0, signupVM.getAll())
        checkOption(1, signupVM.getService())
        checkOption(2, signupVM.getPersonal())
        checkOption(3, signupVM.getMarketing())
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
            if ((signupVM.getService() == true) && (signupVM.getPersonal() == true)) {
                val intent = Intent(this, SignupNicknameActivity::class.java)
                intent.putExtra("all", signupVM.getAll().toString())
                intent.putExtra("service", signupVM.getService().toString())
                intent.putExtra("personal", signupVM.getPersonal().toString())
                intent.putExtra("marketing", signupVM.getMarketing().toString())
                showLog("SIGNUP-SERVICE", "all = ${signupVM.getAll()} / service = ${signupVM.getService()} / personal = ${signupVM.getPersonal()} / marketing = ${signupVM.getMarketing()}")
                startActivity(intent)
            }
        }
    }

    private fun checkEvent(option: String) {
        when (option) {
            "all" -> {
                signupVM.setAll()

                if (signupVM.getAll() == true) {
                    checkOption(0, true)

                    checkOption(1, true)
                    if (signupVM.getService() != true) signupVM.setService()

                    checkOption(2, true)
                    if (signupVM.getPersonal() != true) signupVM.setPersonal()

                    checkOption(3, true)
                    if (signupVM.getMarketing() != true) signupVM.setMarketing()

                } else {
                    checkOption(0, false)

                    checkOption(1, false)
                    if (signupVM.getService() == true) signupVM.setService()

                    checkOption(2, false)
                    if (signupVM.getPersonal() == true) signupVM.setPersonal()

                    checkOption(3, false)
                    if (signupVM.getMarketing() == true) signupVM.setMarketing()
                }

                checkNext()
            }
            "service" -> {
                signupVM.setService()
                checkOption(1, signupVM.getService())
                checkAll()
                checkNext()
            }
            "personal" -> {
                signupVM.setPersonal()
                checkOption(2, signupVM.getPersonal())
                checkAll()
                checkNext()
            }
            else -> {
                signupVM.setMarketing()
                checkOption(3, signupVM.getMarketing())
                checkAll()
            }
        }
    }

    // signupVM 에서 받아온 값이 True 인지 False 인지에 따라 이미지 설정
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
        if ((signupVM.getService() == true) && (signupVM.getPersonal() == true) && (signupVM.getMarketing()) == true) {
            binding.signupAgreeAllIv.setImageResource(R.drawable.ic_radio_check)
            signupVM.setAll()
            return true
        } else if (signupVM.getAll() != false) {
            signupVM.setAll()
        }

        binding.signupAgreeAllIv.setImageResource(R.drawable.ic_radio_default)
        return false
    }

    // 다음으로 넘어가는 버튼 활성화/비활성화
    private fun checkNext(): Boolean {
        val on = ContextCompat.getColor(this, R.color.main)
        val off = Color.parseColor("#D3D4D5")

        if ((signupVM.getService() == true) && (signupVM.getPersonal() == true)) {
            binding.signupAgreeNextBtn.setCardBackgroundColor(on)
            return true
        }

        binding.signupAgreeNextBtn.setCardBackgroundColor(off)
        return false
    }
}