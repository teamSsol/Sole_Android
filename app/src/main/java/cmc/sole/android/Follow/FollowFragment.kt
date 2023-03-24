package cmc.sole.android.Follow

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Follow.Retrofit.FollowCourseView
import cmc.sole.android.Follow.Retrofit.FollowService
import cmc.sole.android.Home.Retrofit.HomeScrapAddAndCancelView
import cmc.sole.android.Home.Retrofit.HomeService
import cmc.sole.android.MainActivity
import cmc.sole.android.R
import cmc.sole.android.Utils.BaseFragment
import cmc.sole.android.databinding.FragmentFollowBinding

class FollowFragment: BaseFragment<FragmentFollowBinding>(FragmentFollowBinding::inflate),
    FollowCourseView, HomeScrapAddAndCancelView {

    lateinit var homeService: HomeService
    lateinit var followService: FollowService
    lateinit var followActivityRVAdapter: FollowActivityRVAdapter
    private var followActivityList = ArrayList<FollowCourseResult>()

    override fun initAfterBinding() {
        initService()
        initAdapter()
        initClickListener()
    }

    private fun initService() {
        homeService = HomeService()
        homeService.setHomeScrapAddAndCancelView(this)
        followService = FollowService()
        followService.setFollowCourseView(this)
        followService.getFollowCourse()
    }

    private fun initAdapter() {
        followActivityRVAdapter = FollowActivityRVAdapter(followActivityList)
        binding.followActivityRv.adapter = followActivityRVAdapter
        binding.followActivityRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        followActivityRVAdapter.setOnItemClickListener(object: FollowActivityRVAdapter.OnItemClickListener {
            override fun onItemClick(data: FollowCourseResult, position: Int) {
                homeService.scrapAddAndCancel(data.courseId!!)
            }
        })
    }

    private fun initClickListener() {
        binding.followFollowIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_fl, FollowListFragment()).addToBackStack("FollowList").commit()
        }
    }

    override fun followCourseSuccessView(followCourse: ArrayList<FollowCourseResult>) {
        followActivityRVAdapter.addAllItems(followCourse)
    }

    override fun followCourseFailureView() {
        showToast("팔로잉 유저 코스 불러오기 실패")
    }

    override fun homeScrapAddAndCancelSuccessView() {
        Log.d("API-TEST", "SCRAP 성공")
    }

    override fun homeScrapAddAndCancelFailureView() {
        showToast("스크랩 실패")
    }
}