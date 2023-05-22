package cmc.sole.android.Home.MyPage.Alarm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Home.MyPageNotificationHistoryResult
import cmc.sole.android.Home.Retrofit.HomeService
import cmc.sole.android.Home.Retrofit.MyPageNotificationHistoryView
import cmc.sole.android.R

import cmc.sole.android.databinding.ActivityMyPageAlarmBinding

class MyPageAlarmActivity: AppCompatActivity(),
    MyPageNotificationHistoryView {

    lateinit var binding: ActivityMyPageAlarmBinding
    lateinit var homeService: HomeService
    lateinit var alarmRVAdapter: MyPageAlarmRVAdapter
    private var alarmList = ArrayList<MyPageNotificationHistoryResult?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyPageAlarmBinding.inflate(layoutInflater)

        initService()
        initAdapter()
        initClickListener()

        setContentView(binding.root)
    }

    private fun initService() {
        homeService = HomeService()
        homeService.setMyPageNotificationHistoryView(this)
        homeService.getMyPageNotificationHistory()
    }

    private fun initAdapter() {
        alarmRVAdapter = MyPageAlarmRVAdapter(alarmList)
        binding.myPageAlarmRv.adapter = alarmRVAdapter
        binding.myPageAlarmRv.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
    }

    private fun initClickListener() {
        binding.myPageAlarmBackIv.setOnClickListener {
            finish()
        }
    }

    override fun myPageNotificationHistorySuccessView(myPageNotificationHistoryResult: ArrayList<MyPageNotificationHistoryResult?>) {
        if (myPageNotificationHistoryResult?.size!! > 0)
            alarmRVAdapter.addAllItems(myPageNotificationHistoryResult)
    }

    override fun myPageNotificationHistoryFailureView() {

    }
}