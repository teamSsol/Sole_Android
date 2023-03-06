package cmc.sole.android.Home.MyPage

import cmc.sole.android.Home.MyPage.Alarm.MyPageAlarmActivity
import cmc.sole.android.Home.MyPage.Alarm.MyPageAlarmSettingActivity
import cmc.sole.android.Home.MyPage.FAQ.MyPageFAQActivity
import cmc.sole.android.Home.MyPage.Notice.MyPageNoticeActivity
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

        binding.myPageAlarmIv.setOnClickListener {
            changeActivity(MyPageAlarmActivity::class.java)
        }

        // MEMO: 내 정보 수정 페이지
        binding.myPageInfoLayout.setOnClickListener {
            changeActivity(MyPageInfoSettingActivity::class.java)
        }

        // MEMO: 내 정보 수정 페이지
        binding.myPageSettingIv.setOnClickListener {
            changeActivity(MyPageInfoSettingActivity::class.java)
        }

        // MEMO: 알림 설정
        binding.myPageOption1.setOnClickListener {
            changeActivity(MyPageAlarmSettingActivity::class.java)
        }

        // MEMO: 공지사항
        binding.myPageOption2.setOnClickListener {
            changeActivity(MyPageNoticeActivity::class.java)
        }

        // MEMO: FAQ
        binding.myPageOption3.setOnClickListener {
            changeActivity(MyPageFAQActivity::class.java)
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