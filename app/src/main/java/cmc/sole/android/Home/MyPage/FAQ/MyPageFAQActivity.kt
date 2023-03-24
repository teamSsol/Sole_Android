package cmc.sole.android.Home.MyPage.FAQ

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Home.MyPage.Notice.MyPageNoticeRVAdapter
import cmc.sole.android.R
import cmc.sole.android.Utils.BaseActivity
import cmc.sole.android.databinding.ActivityMyPageFaqBinding

class MyPageFAQActivity: BaseActivity<ActivityMyPageFaqBinding>(ActivityMyPageFaqBinding::inflate) {

    lateinit var FAQRVAdapter: MyPageFAQRVAdapter
    private var FAQList = ArrayList<FAQData>()

    override fun initAfterBinding() {
        initAdapter()
        initClickListener()
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