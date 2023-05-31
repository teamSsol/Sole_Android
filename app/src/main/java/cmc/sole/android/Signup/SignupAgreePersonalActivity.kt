package cmc.sole.android.Signup


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cmc.sole.android.databinding.ActivitySignupAgreeMarketingBinding
import cmc.sole.android.databinding.ActivitySignupAgreePersonalBinding
import cmc.sole.android.databinding.ActivitySignupAgreeServiceBinding

class SignupAgreePersonalActivity: AppCompatActivity() {

    lateinit var binding: ActivitySignupAgreePersonalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupAgreePersonalBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}