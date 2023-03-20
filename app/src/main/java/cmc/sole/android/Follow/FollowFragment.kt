package cmc.sole.android.Follow

import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Follow.Retrofit.FollowCourseView
import cmc.sole.android.Follow.Retrofit.FollowService
import cmc.sole.android.MainActivity
import cmc.sole.android.R
import cmc.sole.android.Utils.BaseFragment
import cmc.sole.android.databinding.FragmentFollowBinding

class FollowFragment: BaseFragment<FragmentFollowBinding>(FragmentFollowBinding::inflate),
    FollowCourseView {

    lateinit var followService: FollowService
    lateinit var followActivityRVAdapter: FollowActivityRVAdapter
    private var followActivityList = ArrayList<FollowCourseResult>()

    override fun initAfterBinding() {
        initService()
        initAdapter()
        initClickListener()
    }

    private fun initService() {
        followService = FollowService()
        followService.setFollowCourseView(this)
        followService.getFollowCourse()
    }

    private fun initAdapter() {
        followActivityRVAdapter = FollowActivityRVAdapter(followActivityList)
        binding.followActivityRv.adapter = followActivityRVAdapter
        binding.followActivityRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // MEMO: DUMMY DATA
//        followActivityList.add(FollowActivityData("프로필", "밍밍", true, "테스트 이미지", "행궁동 로컬 추천 코스", "안녕"))
//        followActivityList.add(FollowActivityData("프로필", "제이", false, "테스트 이미지", "물고기, 고기", "안녕녕"))
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
}