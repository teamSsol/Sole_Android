package cmc.sole.android.Home.MyPage.Alarm

import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.R
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivityMyPageAlarmBinding

class MyPageAlarmActivity: BaseActivity<ActivityMyPageAlarmBinding>(ActivityMyPageAlarmBinding::inflate) {

    lateinit var alarmRVAdapter: MyPageAlarmRVAdapter
    private var alarmList = ArrayList<AlarmData>()

    override fun initAfterBinding() {
        initAdapter()
        initClickListener()
    }

    private fun initAdapter() {
        alarmRVAdapter = MyPageAlarmRVAdapter(alarmList)
        binding.myPageAlarmRv.adapter = alarmRVAdapter
        binding.myPageAlarmRv.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        // MEMO: DUMMY DATA
        alarmList.add(AlarmData(R.drawable.ic_alarm_follow, "닉네임님이 회원님의 발자국을 따라가기 시작했어요.", "0일전"))
        alarmList.add(AlarmData(R.drawable.ic_alarm_heart, "회원님이 게시한 코스를 50명 이상이 따라가고 있어요.", "0일전"))
        alarmList.add(AlarmData(R.drawable.ic_alarm_gift, "(광고) SOLE 베타 런칭 이벤트! 4월 20일까지 코스 기록 시 200 크레딧을 받을 수 있어요.", "0일전"))
    }

    private fun initClickListener() {
        binding.myPageAlarmBackIv.setOnClickListener {
            finish()
        }
    }
}