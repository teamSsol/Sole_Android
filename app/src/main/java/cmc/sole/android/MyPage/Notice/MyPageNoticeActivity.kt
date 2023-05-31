package cmc.sole.android.MyPage.Notice

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Home.MyPageNoticeAddRequest
import cmc.sole.android.Home.MyPageNoticeResult
import cmc.sole.android.Home.Retrofit.HomeService
import cmc.sole.android.Home.Retrofit.MyPageNoticeAddView
import cmc.sole.android.Home.Retrofit.MyPageNoticeView

import cmc.sole.android.databinding.ActivityMyPageNoticeBinding

class MyPageNoticeActivity: AppCompatActivity(),
    MyPageNoticeView {

    lateinit var binding: ActivityMyPageNoticeBinding

    private lateinit var homeService: HomeService
    lateinit var noticeRVAdapter: MyPageNoticeRVAdapter
    private var noticeList = ArrayList<MyPageNoticeResult>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageNoticeBinding.inflate(layoutInflater)

        initService()
        initAdapter()
        initClickListener()

        setContentView(binding.root)
    }

    private fun initService() {
        homeService = HomeService()
        homeService.setMyPageNoticeView(this)
        homeService.getMyPageNotice()
    }

    private fun initAdapter() {
        noticeRVAdapter = MyPageNoticeRVAdapter(noticeList)
        binding.myPageNoticeRv.adapter = noticeRVAdapter
        binding.myPageNoticeRv.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        noticeRVAdapter.setOnItemClickListener(object: MyPageNoticeRVAdapter.OnItemClickListener {
            // UPDATE: 공지사항 Detail API 있으면 거기서 정보 받고 없으면 여기서 넘겨주기
            override fun onItemClick(data: MyPageNoticeResult, position: Int) {
                val intent = Intent(this@MyPageNoticeActivity, MyPageNoticeContentActivity::class.java)
                intent.putExtra("title", data.title)
                var time = data.createdAt.substring(0, 4) + "." + data.createdAt.substring(5, 7) + "." + data.createdAt.substring(8)
                intent.putExtra("time", time)
                intent.putExtra("content", data.content)
                startActivity(intent)
            }
        })
    }

    private fun initClickListener() {
        binding.myPageNoticeBackIv.setOnClickListener {
            finish()
        }
    }

    override fun myPageNoticeSuccessView(myPageNoticeResult: ArrayList<MyPageNoticeResult>) {
        noticeRVAdapter.addAllItems(myPageNoticeResult)
    }

    override fun myPageNoticeFailureView() {

    }
}