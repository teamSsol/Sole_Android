package cmc.sole.android.Signup


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cmc.sole.android.databinding.ActivitySignupAgreeMarketingBinding
import cmc.sole.android.databinding.ActivitySignupAgreeServiceBinding

class SignupAgreeServiceActivity: AppCompatActivity() {

    lateinit var binding: ActivitySignupAgreeServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupAgreeServiceBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}