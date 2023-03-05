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

        // MEMO: 알림 설정
        binding.myPageOption1.setOnClickListener {
            changeActivity(ActivityMyPageAlarm::class.java)

        }

        // MEMO: 공지사항
        binding.myPageOption2.setOnClickListener {
            changeActivity(ActivityMyPageNotice::class.java)
        }

        // MEMO: FAQ
        binding.myPageOption3.setOnClickListener {
            changeActivity(ActivityMyPageFAQ::class.java)
        }

        // MEMO: 문의하기
        // UPDATE: 문의하기 연결
        binding.myPageOption4.setOnClickListener {

        }

        // MEMO: 이용약관
        // UPDATE: 이용약관 연결
        binding.myPageOption5.setOnClickListener {

        }

        // MEMO: 개인정보 처리방침
        // UPDATE: 개인정보 처리방침 연결
        binding.myPageOption6.setOnClickListener {

        }

        binding.myPageLogoutTv.setOnClickListener {
            val logoutDialog = DialogMyPageLogout()
            logoutDialog.show(supportFragmentManager, "LogoutDialog")
        }
    }
}