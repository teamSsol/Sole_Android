package cmc.sole.android

import androidx.lifecycle.ViewModelProvider
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivitySignupAgreeBinding

class SignupAgreeActivity: BaseActivity<ActivitySignupAgreeBinding>(ActivitySignupAgreeBinding::inflate) {

    private lateinit var signupAgreeVM: SignupAgreeViewModel

    override fun initAfterBinding() {
        signupAgreeVM = ViewModelProvider(this).get(SignupAgreeViewModel::class.java)

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
            signupAgreeVM.setAll()

            if (signupAgreeVM.getAll() == true) {
                checkOption(0, true)

                checkOption(1, true)
                if (signupAgreeVM.getService() == false) {
                    signupAgreeVM.setService()
                }

                checkOption(2, true)
                if (signupAgreeVM.getPersonal() == false) {
                    signupAgreeVM.setPersonal()
                }

                checkOption(3, true)
                if (signupAgreeVM.getMarketing() == false) {
                    signupAgreeVM.setMarketing()
                }
            } else {
                checkOption(0, false)

                checkOption(1, false)
                if (signupAgreeVM.getService() == true) {
                    signupAgreeVM.setService()
                }

                checkOption(2, false)
                if (signupAgreeVM.getPersonal() == true) {
                    signupAgreeVM.setPersonal()
                }

                checkOption(3, false)
                if (signupAgreeVM.getMarketing() == true) {
                    signupAgreeVM.setMarketing()
                }
            }
        }

        binding.signupAgreeServiceIv.setOnClickListener {
            signupAgreeVM.setService()
            checkOption(1, signupAgreeVM.getService())
            checkAll()
        }

        binding.signupAgreePersonalIv.setOnClickListener {
            signupAgreeVM.setPersonal()
            checkOption(2, signupAgreeVM.getPersonal())
            checkAll()
        }

        binding.signupAgreeMarketingIv.setOnClickListener {
            signupAgreeVM.setMarketing()
            checkOption(3, signupAgreeVM.getMarketing())
            checkAll()
        }
    }

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

    private fun checkAll(): Boolean {
        if ((signupAgreeVM.getService() == true) && (signupAgreeVM.getPersonal() == true) && (signupAgreeVM.getMarketing()) == true) {
            binding.signupAgreeAllIv.setImageResource(R.drawable.ic_radio_check)
            showLog("EXAMPLE", "1 ${signupAgreeVM.getAll()} / ${signupAgreeVM.getService()} / ${signupAgreeVM.getPersonal()} / ${signupAgreeVM.getMarketing()}")
            signupAgreeVM.setAll()
            showLog("EXAMPLE", "2 ${signupAgreeVM.getAll()} / ${signupAgreeVM.getService()} / ${signupAgreeVM.getPersonal()} / ${signupAgreeVM.getMarketing()}")
            return true
        }

        binding.signupAgreeAllIv.setImageResource(R.drawable.ic_radio_default)
        showLog("EXAMPLE", "3 ${signupAgreeVM.getAll()} / ${signupAgreeVM.getService()} / ${signupAgreeVM.getPersonal()} / ${signupAgreeVM.getMarketing()}")
        signupAgreeVM.setAll()
        showLog("EXAMPLE", "4 ${signupAgreeVM.getAll()} / ${signupAgreeVM.getService()} / ${signupAgreeVM.getPersonal()} / ${signupAgreeVM.getMarketing()}")
        return false
    }
}