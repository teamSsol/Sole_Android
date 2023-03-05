package cmc.sole.android.Home.MyPage

import androidx.fragment.app.DialogFragment
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

        binding.myPageInfoLayout.setOnClickListener {
            changeActivity(MyPageInfoSettingActivity::class.java)
        }

        binding.myPageLogoutTv.setOnClickListener {
            val logoutDialog = DialogMyPageLogout()
            logoutDialog.show(supportFragmentManager, "LogoutDialog")
        }
    }
}