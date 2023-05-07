package cmc.sole.android.Follow

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Course.CourseDetailActivity
import cmc.sole.android.Follow.Retrofit.FollowService
import cmc.sole.android.Follow.Retrofit.FollowUserInfoView
import cmc.sole.android.Home.DefaultCourse
import cmc.sole.android.Home.HomeDefaultCourseRVAdapter
import cmc.sole.android.MyCourse.MyCourseTagRVAdapter
import cmc.sole.android.MyCourse.TagButton
import cmc.sole.android.R
import cmc.sole.android.Utils.BaseFragment
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewHorizontalDecoration
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.Utils.Translator
import cmc.sole.android.databinding.FragmentFollowUserBinding
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayoutManager
import kotlin.math.roundToInt

class FollowUserFragment: BaseFragment<FragmentFollowUserBinding>(FragmentFollowUserBinding::inflate),
    FollowUserInfoView {

    private lateinit var followService: FollowService
    lateinit var followUserRecentRVAdapter: HomeDefaultCourseRVAdapter
    lateinit var tagRVAdapter: MyCourseTagRVAdapter
    private var tagList = ArrayList<String>()
    var recentCourseList = ArrayList<DefaultCourse>()
    private var followInfoMemberSocialId = ""
    var courseId: Int? = null

    // UPDATE: courseId 연결해주기!
    override fun initAfterBinding() {
        followInfoMemberSocialId = requireArguments().getString("followInfoMemberSocialId").toString()

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
            override fun onItemClick(data: DefaultCourse, position: Int, mode: String) {
                val intent = Intent(activity, CourseDetailActivity::class.java)
                intent.putExtra("courseId", data.courseId)
                startActivity(intent)
            }
        })

        tagRVAdapter = MyCourseTagRVAdapter(tagList)
        binding.followUserPopularTagRv.adapter = tagRVAdapter
        binding.followUserPopularTagRv.layoutManager = FlexboxLayoutManager(activity)
        binding.followUserPopularTagRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 20))
        binding.followUserPopularTagRv.addItemDecoration(RecyclerViewVerticalDecoration("top", 20))
    }

    private fun initClickListener() {
        binding.followUserBackIv.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    override fun followUserInfoSuccessView(followUserInfo: FollowUserInfoResult) {
        Glide.with(this).load(followUserInfo.profileImg)
            .placeholder(R.drawable.ic_profile).centerCrop().circleCrop().into(binding.followUserProfileIv)
        binding.followUserNicknameTv.text = followUserInfo.nickname
        binding.followUserPopularTitleTv.text = "${followUserInfo.nickname}님의 인기 코스"
        binding.followUserRecentTv.text = "${followUserInfo.nickname}님의 최근 코스"
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
        binding.followUserPopularTimeTv.text = "${(popularCourse.duration.toDouble() / 60).toInt()} 시간 소요"
        binding.followUserPopularDistanceTv.text = ((popularCourse.distance * 100.0).roundToInt() / 100.0).toString() + "km 이동"

        for (i in 0 until popularCourse.categories.size) {
            tagRVAdapter.addItem(Translator.tagEngToKor(activity as AppCompatActivity, popularCourse.categories.elementAt(i).toString()))
        }
        tagRVAdapter.addItem("")

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