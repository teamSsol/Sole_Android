package cmc.sole.android.Home.MyPage.Notice


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cmc.sole.android.databinding.ActivityMyPageNoticeBinding
import cmc.sole.android.databinding.ActivityMyPageNoticeContentBinding

class MyPageNoticeContentActivity: AppCompatActivity() {

    lateinit var binding: ActivityMyPageNoticeContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageNoticeContentBinding.inflate(layoutInflater)

        initBinding()
        initClickListener()

        setContentView(binding.root)
    }

    private fun initBinding() {
        binding.myPageNoticeTitle.text = intent.getStringExtra("title")
        binding.myPageNoticeTime.text = intent.getStringExtra("time")
        binding.myPageNoticeContent.text = intent.getStringExtra("content")
    }

    private fun initClickListener() {
        binding.myPageNoticeBackIv.setOnClickListener {
            finish()
        }
    }
}