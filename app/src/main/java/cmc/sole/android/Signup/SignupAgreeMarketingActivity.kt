package cmc.sole.android.Signup


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cmc.sole.android.databinding.ActivitySignupAgreeLocationBinding
import cmc.sole.android.databinding.ActivitySignupAgreeMarketingBinding
import cmc.sole.android.databinding.ActivitySignupAgreeServiceBinding

class SignupAgreeMarketingActivity: AppCompatActivity() {

    lateinit var binding: ActivitySignupAgreeMarketingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupAgreeMarketingBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}