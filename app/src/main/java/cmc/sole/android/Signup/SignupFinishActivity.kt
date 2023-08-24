package cmc.sole.android.Signup

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import cmc.sole.android.Login.LoginActivity
import cmc.sole.android.MainActivity
import cmc.sole.android.StartCourseTagActivity

import cmc.sole.android.databinding.ActivitySignupAgreeBinding
import cmc.sole.android.databinding.ActivitySignupFinishBinding
import cmc.sole.android.databinding.ActivitySignupNicknameBinding

class SignupFinishActivity: AppCompatActivity() {
    lateinit var binding: ActivitySignupFinishBinding

    // UPDATE: 회원가입 API 연동 필요!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupFinishBinding.inflate(layoutInflater)

        initClickListener()

        setContentView(binding.root)
    }

    private fun initClickListener() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, StartCourseTagActivity::class.java)
            intent.putExtra("flag", "signupFinish")
            startActivity(intent)
            finishAffinity()
        }, 3000)
    }

    private fun changeActivity(activity: Class<*>) {
        startActivity(Intent(this, activity))
    }
}