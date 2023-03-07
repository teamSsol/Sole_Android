package cmc.sole.android.Home.MyPage.Notice

import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivityMyPageNoticeContentBinding

class MyPageNoticeContentActivity: BaseActivity<ActivityMyPageNoticeContentBinding>(ActivityMyPageNoticeContentBinding::inflate) {
    override fun initAfterBinding() {
        initBinding()
        initClickListener()
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