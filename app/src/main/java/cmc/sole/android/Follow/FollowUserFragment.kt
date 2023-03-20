package cmc.sole.android.Follow

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Course.CourseDetailActivity
import cmc.sole.android.Follow.Retrofit.FollowService
import cmc.sole.android.Follow.Retrofit.FollowUserInfoView
import cmc.sole.android.Home.DefaultCourse
import cmc.sole.android.Home.HomeDefaultCourseRVAdapter
import cmc.sole.android.R
import cmc.sole.android.Utils.BaseFragment
import cmc.sole.android.databinding.FragmentFollowUserBinding
import com.bumptech.glide.Glide

class FollowUserFragment: BaseFragment<FragmentFollowUserBinding>(FragmentFollowUserBinding::inflate),
    FollowUserInfoView {

    private lateinit var followService: FollowService
    lateinit var followUserRecentRVAdapter: HomeDefaultCourseRVAdapter
    var recentCourseList = ArrayList<DefaultCourse>()
    private var followInfoMemberSocialId = -1
    var courseId = -1

    // UPDATE: courseId 연결해주기!
    override fun initAfterBinding() {
        followInfoMemberSocialId = requireArguments().getInt("followInfoMemberSocialId")

        initService()
        initAdapter()
        initClickListener()
    }

    private fun initService() {
        followService = FollowService()
        followService.setFollowUserInfoView(this)
        followService.getFollowUserInfo(followInfoMemberSocialId, courseId)
    }

    private fun initAdapter() {
        followUserRecentRVAdapter = HomeDefaultCourseRVAdapter(recentCourseList)
        binding.followUserRecentRv.adapter = followUserRecentRVAdapter
        binding.followUserRecentRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        followUserRecentRVAdapter.setOnItemClickListener(object: HomeDefaultCourseRVAdapter.OnItemClickListener {
            override fun onItemClick(data: DefaultCourse, position: Int) {
                startActivity(Intent(activity, CourseDetailActivity::class.java))
            }
        })
    }

    private fun initClickListener() {
        binding.followUserBackIv.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    override fun followUserInfoSuccessView(followUserInfo: FollowUserInfoResult) {
        // UPDATE: View 연결
        Glide.with(this).load(followUserInfo.profileImg).into(binding.followUserProfileIv)
        binding.followUserNicknameTv.text = followUserInfo.nickname
        binding.followUserFollowerTv.text = "팔로워 ${followUserInfo.followerCount}"
        binding.followUserFollowingTv.text = "팔로잉 ${followUserInfo.followingCount}"
        binding.followerUserInfoTv.text = followUserInfo.description

        if (followUserInfo.followStatus == "FOLLOWER") {
            binding.itemFollowFollowBtn.visibility = View.VISIBLE
            binding.itemFollowFollowingBtn.visibility = View.GONE
        } else if (followUserInfo.followStatus == "FOLLOWING") {
            binding.itemFollowFollowBtn.visibility = View.GONE
            binding.itemFollowFollowingBtn.visibility = View.VISIBLE
        }

        var popularCourse = followUserInfo.popularCourse
        Glide.with(this).load(popularCourse.thumbnailImg).into(binding.followUserPopularImg)
        binding.followUserPopularTitleTv.text = popularCourse.title
        binding.followUserPopularLocationTv.text = popularCourse.address
        binding.followUserPopularTimeTv.text = popularCourse.duration.toString() + "시간 소요"
        binding.followUserPopularDistanceTv.text = popularCourse.distance.toString() + "km 이동"

        if (popularCourse.like)
            binding.followUserPopularHeartIv.setImageResource(R.drawable.ic_heart_color)
        else binding.followUserPopularHeartIv.setImageResource(R.drawable.ic_heart_empty)

        // UPDATE: 태그 추가
        followUserRecentRVAdapter.addAllItems(followUserInfo.recentCourses)
    }

    override fun followUserInfoFailureView() {
        showToast("팔로우 유저 정보 불러오기 실패")
    }
}