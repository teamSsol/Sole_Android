package cmc.sole.android.Home.MyPage.Alarm

import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import cmc.sole.android.Home.MyPageNotificationResult
import cmc.sole.android.Home.MyPageNotificationUpdateRequest
import cmc.sole.android.Home.MyPageNotificationUpdateResponse
import cmc.sole.android.Home.Retrofit.HomeService
import cmc.sole.android.Home.Retrofit.MyPageNotificationUpdateView
import cmc.sole.android.Home.Retrofit.MyPageNotificationView
import cmc.sole.android.R
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivityMyPageAlarmSettingBinding

class MyPageAlarmSettingActivity: BaseActivity<ActivityMyPageAlarmSettingBinding>(ActivityMyPageAlarmSettingBinding::inflate),
    MyPageNotificationView, MyPageNotificationUpdateView {

    private lateinit var homeService: HomeService

    private var activityFlag: Boolean? = null
    private var marketingFlag: Boolean? = null

    override fun initAfterBinding() {
        initService()
        initClickListener()
    }

    private fun initService() {
        homeService = HomeService()
        homeService.setMyPageNotificationView(this)
        homeService.getMyPageNotification()
        homeService.setMyPageNotificationUpdateView(this)
    }

    private fun initClickListener() {
        binding.alarmSettingBackIv.setOnClickListener {
            finish()
        }

        binding.alarmSettingActivityToggleOn.setOnClickListener {
            // UPDATE: 이 부분도 API로 주는 것인지
            // UPDATE: API와 연동
            binding.alarmSettingActivityToggleOn.visibility = View.GONE
            binding.alarmSettingActivityToggleOff.visibility = View.VISIBLE

            homeService.myPageNotificationUpdate(MyPageNotificationUpdateRequest(false, marketingFlag!!))
            activityFlag = false
        }

        binding.alarmSettingActivityToggleOff.setOnClickListener {
            // UPDATE: 이 부분도 API로 주는 것인지
            // UPDATE: API와 연동
            binding.alarmSettingActivityToggleOn.visibility = View.VISIBLE
            binding.alarmSettingActivityToggleOff.visibility = View.GONE

            homeService.myPageNotificationUpdate(MyPageNotificationUpdateRequest(true, marketingFlag!!))
            activityFlag = true
        }

        binding.alarmSettingMarketingToggleOn.setOnClickListener {
            // UPDATE: 이 부분도 API로 주는 것인지
            // UPDATE: API와 연동
            binding.alarmSettingMarketingToggleOn.visibility = View.GONE
            binding.alarmSettingMarketingToggleOff.visibility = View.VISIBLE

            homeService.myPageNotificationUpdate(MyPageNotificationUpdateRequest(activityFlag!!, false))
            marketingFlag = false
        }

        binding.alarmSettingMarketingToggleOff.setOnClickListener {
            // UPDATE: 이 부분도 API로 주는 것인지
            // UPDATE: API와 연동
            binding.alarmSettingMarketingToggleOn.visibility = View.VISIBLE
            binding.alarmSettingMarketingToggleOff.visibility = View.GONE

            homeService.myPageNotificationUpdate(MyPageNotificationUpdateRequest(activityFlag!!, true))
            marketingFlag = true
        }
    }

    override fun myPageNotificationSuccessView(myPageNotificationResult: MyPageNotificationResult) {
        if (myPageNotificationResult.activityNot) {
            binding.alarmSettingActivityToggleOn.visibility = View.VISIBLE
            binding.alarmSettingActivityToggleOff.visibility = View.GONE
        } else {
            binding.alarmSettingActivityToggleOn.visibility = View.GONE
            binding.alarmSettingActivityToggleOff.visibility = View.VISIBLE
        }

        if (myPageNotificationResult.marketingNot) {
            binding.alarmSettingMarketingToggleOn.visibility = View.VISIBLE
            binding.alarmSettingMarketingToggleOff.visibility = View.GONE
        } else {
            binding.alarmSettingMarketingToggleOn.visibility = View.GONE
            binding.alarmSettingMarketingToggleOff.visibility = View.VISIBLE
        }

        activityFlag = myPageNotificationResult.activityNot
        marketingFlag = myPageNotificationResult.marketingNot
    }

    override fun myPageNotificationFailureView() {
        showToast("알림 설정 확인 실패")
    }

    override fun myPageNotificationUpdateSuccessView(myPageNotificationUpdateResult: MyPageNotificationResult) {
        Log.d("API-TEST", "myPageNotificationUpdateResult = $myPageNotificationUpdateResult")
    }

    override fun myPageNotificationUpdateFailureView() {
        showToast("알림 설정 실패")
    }
}