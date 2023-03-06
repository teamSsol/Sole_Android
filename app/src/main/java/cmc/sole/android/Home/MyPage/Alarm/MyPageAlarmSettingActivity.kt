package cmc.sole.android.Home.MyPage.Alarm

import android.view.View
import androidx.core.content.ContextCompat
import cmc.sole.android.R
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivityMyPageAlarmSettingBinding

class MyPageAlarmSettingActivity: BaseActivity<ActivityMyPageAlarmSettingBinding>(ActivityMyPageAlarmSettingBinding::inflate) {

    override fun initAfterBinding() {
        initClickListener()
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
        }

        binding.alarmSettingActivityToggleOff.setOnClickListener {
            // UPDATE: 이 부분도 API로 주는 것인지
            // UPDATE: API와 연동
            binding.alarmSettingActivityToggleOn.visibility = View.VISIBLE
            binding.alarmSettingActivityToggleOff.visibility = View.GONE
        }

        binding.alarmSettingMarketingToggleOn.setOnClickListener {
            // UPDATE: 이 부분도 API로 주는 것인지
            // UPDATE: API와 연동
            binding.alarmSettingMarketingToggleOn.visibility = View.GONE
            binding.alarmSettingMarketingToggleOff.visibility = View.VISIBLE
        }

        binding.alarmSettingMarketingToggleOff.setOnClickListener {
            // UPDATE: 이 부분도 API로 주는 것인지
            // UPDATE: API와 연동
            binding.alarmSettingMarketingToggleOn.visibility = View.VISIBLE
            binding.alarmSettingMarketingToggleOff.visibility = View.GONE
        }
    }
}