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

        // MEMO: DUMMY DATA
        FAQList.add(FAQData("1 내가 스크랩한 코스를 사용하고 싶어요!", "내가 스크랩한 코스를 사용하고 싶어요!에 대한 답변 내가 스크랩한 코스를 사용하고 싶어요!에 대한 답변 내가 스크랩한 코스를 사용하고 싶어요!에 대한 답변 "))
        FAQList.add(FAQData("2 내가 스크랩한 코스를 사용하고 싶어요!", "내가 스크랩한 코스를 사용하고 싶어요!에 대한 답변 내가 스크랩한 코스를 사용하고 싶어요!에 대한 답변 내가 스크랩한 코스를 사용하고 싶어요!에 대한 답변 "))
        FAQList.add(FAQData("3 내가 스크랩한 코스를 사용하고 싶어요!", "내가 스크랩한 코스를 사용하고 싶어요!에 대한 답변 내가 스크랩한 코스를 사용하고 싶어요!에 대한 답변 내가 스크랩한 코스를 사용하고 싶어요!에 대한 답변 "))
        FAQList.add(FAQData("4 내가 스크랩한 코스를 사용하고 싶어요!", "내가 스크랩한 코스를 사용하고 싶어요!에 대한 답변 내가 스크랩한 코스를 사용하고 싶어요!에 대한 답변 내가 스크랩한 코스를 사용하고 싶어요!에 대한 답변 "))
        FAQList.add(FAQData("5 내가 스크랩한 코스를 사용하고 싶어요!", "내가 스크랩한 코스를 사용하고 싶어요!에 대한 답변 내가 스크랩한 코스를 사용하고 싶어요!에 대한 답변 내가 스크랩한 코스를 사용하고 싶어요!에 대한 답변 "))
    }

    private fun initClickListener() {
        binding.myPageFaqBackIv.setOnClickListener {
            finish()
        }
    }
}