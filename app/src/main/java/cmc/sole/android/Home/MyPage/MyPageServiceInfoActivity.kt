package cmc.sole.android.Home.MyPage

import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivityMyPageServiceInfoBinding

class MyPageServiceInfoActivity: BaseActivity<ActivityMyPageServiceInfoBinding>(ActivityMyPageServiceInfoBinding::inflate) {
    override fun initAfterBinding() {
        binding.myPageServiceInfoBackIv.setOnClickListener {
            finish()
        }
    }
}