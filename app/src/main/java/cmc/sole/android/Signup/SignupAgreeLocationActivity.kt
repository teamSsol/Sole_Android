package cmc.sole.android.Signup


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import cmc.sole.android.databinding.ActivitySignupAgreeBinding
import cmc.sole.android.databinding.ActivitySignupAgreeLocationBinding
import cmc.sole.android.databinding.ActivitySignupAgreeMarketingBinding
import cmc.sole.android.databinding.ActivitySignupAgreeServiceBinding

class SignupAgreeLocationActivity: AppCompatActivity() {

    lateinit var binding: ActivitySignupAgreeLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupAgreeLocationBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}