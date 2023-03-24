package cmc.sole.android.Home.MyPage

import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivityMyPagePersonalInfoBinding
import cmc.sole.android.databinding.ActivityMyPageServiceInfoBinding

class MyPagePersonalInfoActivity: BaseActivity<ActivityMyPagePersonalInfoBinding>(ActivityMyPagePersonalInfoBinding::inflate) {
    override fun initAfterBinding() {
        binding.myPagePersonalInfoBackIv.setOnClickListener {
            finish()
        }
    }
}