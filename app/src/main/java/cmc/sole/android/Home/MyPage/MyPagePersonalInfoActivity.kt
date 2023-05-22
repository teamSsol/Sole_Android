package cmc.sole.android.Home.MyPage


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cmc.sole.android.databinding.ActivityMyPageNoticeBinding
import cmc.sole.android.databinding.ActivityMyPagePersonalInfoBinding
import cmc.sole.android.databinding.ActivityMyPageServiceInfoBinding

class MyPagePersonalInfoActivity: AppCompatActivity() {

    lateinit var binding: ActivityMyPagePersonalInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPagePersonalInfoBinding.inflate(layoutInflater)

        initClickListener()

        setContentView(binding.root)
    }

    private fun initClickListener() {
        binding.myPagePersonalInfoBackIv.setOnClickListener {
            finish()
        }
    }
}