package cmc.sole.android.Home.MyPage.FAQ

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Home.MyPage.Notice.MyPageNoticeRVAdapter
import cmc.sole.android.R

import cmc.sole.android.databinding.ActivityMyPageFaqBinding

class MyPageFAQActivity: AppCompatActivity() {

    lateinit var binding: ActivityMyPageFaqBinding
    lateinit var FAQRVAdapter: MyPageFAQRVAdapter
    private var FAQList = ArrayList<FAQData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageFaqBinding.inflate(layoutInflater)

        initAdapter()
        initClickListener()

        setContentView(binding.root)
    }

    private fun initAdapter() {
        FAQRVAdapter = MyPageFAQRVAdapter(FAQList)
        binding.myPageFaqRv.adapter = FAQRVAdapter
        binding.myPageFaqRv.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        FAQRVAdapter.setOnItemClickListener(object: MyPageFAQRVAdapter.OnItemClickListener {
            override fun onItemClick(data: FAQData, position: Int) {

            }
        })
    }

    private fun initClickListener() {
        binding.myPageFaqBackIv.setOnClickListener {
            finish()
        }
    }
}