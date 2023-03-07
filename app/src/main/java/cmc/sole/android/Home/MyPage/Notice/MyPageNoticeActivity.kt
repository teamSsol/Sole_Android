package cmc.sole.android.Home.MyPage.Notice

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivityMyPageNoticeBinding

class MyPageNoticeActivity: BaseActivity<ActivityMyPageNoticeBinding>(ActivityMyPageNoticeBinding::inflate) {

    lateinit var noticeRVAdapter: MyPageNoticeRVAdapter
    private var noticeList = ArrayList<NoticeData>()

    override fun initAfterBinding() {
        initAdapter()
        initClickListener()
    }

    private fun initAdapter() {
        noticeRVAdapter = MyPageNoticeRVAdapter(noticeList)
        binding.myPageNoticeRv.adapter = noticeRVAdapter
        binding.myPageNoticeRv.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        noticeRVAdapter.setOnItemClickListener(object: MyPageNoticeRVAdapter.OnItemClickListener {
            // UPDATE: 공지사항 Detail API 있으면 거기서 정보 받고 없으면 여기서 넘겨주기
            override fun onItemClick(data: NoticeData, position: Int) {
                val intent = Intent(this@MyPageNoticeActivity, MyPageNoticeContentActivity::class.java)
                intent.putExtra("title", data.title)
                intent.putExtra("time", "2023.00.00")
                intent.putExtra("content", "텍스트")
                startActivity(intent)
            }
        })

        // MEMO: DUMMY DATA
        noticeList.add(NoticeData("쏠 1.0 오픈 안내"))
        noticeList.add(NoticeData("쏠 1.1 오픈 안내"))
        noticeList.add(NoticeData("쏠 1.2 오픈 안내"))
        noticeList.add(NoticeData("쏠 1.3 오픈 안내"))
        noticeList.add(NoticeData("쏠 1.4 오픈 안내"))
    }

    private fun initClickListener() {
        binding.myPageNoticeBackIv.setOnClickListener {
            finish()
        }
    }
}