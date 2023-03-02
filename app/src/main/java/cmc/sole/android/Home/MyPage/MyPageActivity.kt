package cmc.sole.android.Home.MyPage

import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivityMyPageBinding

class MyPageActivity: BaseActivity<ActivityMyPageBinding>(ActivityMyPageBinding::inflate) {
    override fun initAfterBinding() {
        initClickListener()
    }

    private fun initClickListener() {
        binding.myPageBackIv.setOnClickListener {
            finish()
        }
    }
}