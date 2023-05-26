package cmc.sole.android.MyPage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import cmc.sole.android.MyPage.Alarm.MyPageAlarmActivity
import cmc.sole.android.MyPage.Alarm.MyPageAlarmSettingActivity
import cmc.sole.android.MyPage.FAQ.MyPageFAQActivity
import cmc.sole.android.MyPage.Notice.MyPageNoticeActivity
import cmc.sole.android.Home.MyPageInfoResponse
import cmc.sole.android.Home.MyPageInfoResult
import cmc.sole.android.Home.Retrofit.HomeService
import cmc.sole.android.Home.Retrofit.MyPageInfoView
import cmc.sole.android.Login.LoginActivity
import cmc.sole.android.R

import cmc.sole.android.databinding.ActivityMyPageBinding
import cmc.sole.android.databinding.ActivityMyPageNoticeBinding
import com.bumptech.glide.Glide

class MyPageActivity: AppCompatActivity(), MyPageInfoView {

    lateinit var binding: ActivityMyPageBinding

    private lateinit var homeService: HomeService
    private lateinit var myInfo: MyPageInfoResult

    override fun onResume() {
        super.onResume()
        homeService.getMyPageInfo()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageBinding.inflate(layoutInflater)

        initService()
        initClickListener()

        setContentView(binding.root)
    }

    private fun initService() {
        homeService = HomeService()
        homeService.setMyPageInfoView(this)
        homeService.getMyPageInfo()
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
            var intent = Intent(this, MyPageInfoSettingActivity::class.java)
            intent.putExtra("profileImgUrl", myInfo.profileImgUrl)
            intent.putExtra("socialId", myInfo.socialId)
            intent.putExtra("nickname", myInfo.nickname)
            intent.putExtra("description", myInfo.description)
            startActivity(intent)
        }

        // MEMO: 내 정보 수정 페이지
        binding.myPageSettingIv.setOnClickListener {
            var intent = Intent(this, MyPageInfoSettingActivity::class.java)
            intent.putExtra("profileImgUrl", myInfo.profileImgUrl)
            intent.putExtra("socialId", myInfo.socialId)
            intent.putExtra("nickname", myInfo.nickname)
            intent.putExtra("description", myInfo.description)
            startActivity(intent)
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
            changeActivity(MyPageServiceInfoActivity::class.java)
        }

        // MEMO: 개인정보 처리방침
        // UPDATE: 개인정보 처리방침 연결
        binding.myPageOption6.setOnClickListener {
            changeActivity(MyPagePersonalInfoActivity::class.java)
        }

        binding.myPageLogoutTv.setOnClickListener {
            val logoutDialog = DialogMyPageLogout()
            logoutDialog.show(supportFragmentManager, "LogoutDialog")
            logoutDialog.setOnFinishListener(object: DialogMyPageLogout.OnFinishListener {
                override fun finish(result: Boolean) {
                    if (result) {
                        startActivity(Intent(this@MyPageActivity, LoginActivity::class.java))
                        finishAffinity()
                    }
                }
            })
        }
    }

    override fun myPageInfoSuccessView(myPageInfoResponse: MyPageInfoResult) {
        myInfo = myPageInfoResponse
        Glide.with(this).load(myPageInfoResponse.profileImgUrl)
            .placeholder(R.drawable.ic_profile).centerCrop().circleCrop().into(binding.myPageProfileIv)
        binding.myPageNicknameTv.text = myPageInfoResponse.nickname
        binding.myPageFollowerTv.text = "팔로워 " + myPageInfoResponse.follower.toString()
        binding.myPageFollowingTv.text = "팔로잉 " + myPageInfoResponse.following.toString()
    }

    override fun myPageInfoFailureView() {

    }

    private fun changeActivity(activity: Class<*>) {
        startActivity(Intent(this, activity))
    }
}