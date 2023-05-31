package cmc.sole.android.MyPage


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cmc.sole.android.databinding.ActivityMyPageNoticeBinding
import cmc.sole.android.databinding.ActivityMyPageServiceInfoBinding

class MyPageServiceInfoActivity: AppCompatActivity() {

    lateinit var binding: ActivityMyPageServiceInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageServiceInfoBinding.inflate(layoutInflater)

        initClickListener()

        setContentView(binding.root)
    }

    private fun initClickListener() {
        binding.myPageServiceInfoBackIv.setOnClickListener {
            finish()
        }
    }
}