package cmc.sole.android.Home.MyPage.Alarm

import androidx.core.content.ContextCompat
import cmc.sole.android.R
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivityMyPageAlarmSettingBinding

class MyPageAlarmSettingActivity: BaseActivity<ActivityMyPageAlarmSettingBinding>(ActivityMyPageAlarmSettingBinding::inflate) {

    var activityFlag: Boolean = true
    var marketingFlag: Boolean = false

    override fun initAfterBinding() {
        initClickListener()
    }

    private fun initClickListener() {
        binding.alarmSettingBackIv.setOnClickListener {
            finish()
        }

        binding.alarmSettingActivityToggle.setOnClickListener {
            // UPDATE: 이 부분도 API로 주는 것인지
            // UPDATE: API와 연동
            if (activityFlag) {
                binding.alarmSettingActivityToggle.setImageResource(R.drawable.ic_toggle_off)
            }
            else binding.alarmSettingActivityToggle.setImageResource(R.drawable.ic_toggle_on)

            activityFlag = !activityFlag
        }

        binding.alarmSettingMarketingToggle.setOnClickListener {
            // UPDATE: 이 부분도 API로 주는 것인지
            // UPDATE: API와 연동
            if (marketingFlag)
                binding.alarmSettingMarketingToggle.setImageResource(R.drawable.ic_toggle_off)
            else binding.alarmSettingMarketingToggle.setImageResource(R.drawable.ic_toggle_on)

            marketingFlag = !marketingFlag
        }
    }
}