package cmc.sole.android.Follow

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Course.CourseDetailActivity
import cmc.sole.android.Home.DefaultCourse
import cmc.sole.android.Home.HomeDefaultCourseRVAdapter
import cmc.sole.android.Utils.BaseFragment
import cmc.sole.android.databinding.FragmentFollowUserBinding

class FollowUserFragment: BaseFragment<FragmentFollowUserBinding>(FragmentFollowUserBinding::inflate) {

    lateinit var followUserRecentRVAdapter: HomeDefaultCourseRVAdapter
    var recentCourseList = ArrayList<DefaultCourse>()

    override fun initAfterBinding() {
        // UPDATE: 프로필 부분 연결 필요
        // binding.itemFollowUserProfileIv.setImageResource()
        // arguments?.getString("profileImg")
        binding.itemFollowUserNicknameTv.text = arguments?.getString("nickname")
        binding.followUserPopularTv.text = arguments?.getString("nickname") + "의 인기 코스"
        binding.followUserRecentTv.text = arguments?.getString("nickname") + "의 최근 코스"
        binding.itemFollowUserFollowerTv.text = arguments?.getString("follower") + "팔로워"
        binding.itemFollowUserFollowingTv.text = arguments?.getString("following") + "팔로잉"

        initAdapter()
        initClickListener()
    }

    private fun initAdapter() {
        followUserRecentRVAdapter = HomeDefaultCourseRVAdapter(recentCourseList)
        binding.followUserPopularRv.adapter = followUserRecentRVAdapter
        binding.followUserPopularRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        followUserRecentRVAdapter.setOnItemClickListener(object: HomeDefaultCourseRVAdapter.OnItemClickListener {
            override fun onItemClick(data: DefaultCourse, position: Int) {
                startActivity(Intent(activity, CourseDetailActivity::class.java))
            }
        })

        // MEMO: DUMMY DATA
//        recentCourseList.add(DefaultCourse("img", "베이커리 맞은 편 일식당", false, "경기 수원", "4시간", "104m", arrayListOf("test"), null))
//        recentCourseList.add(DefaultCourse("img", "발리 다녀와서 파이", true, "서울 마포구", "4시간", "247m", arrayListOf("test"), null))
//        recentCourseList.add(DefaultCourse("img", "관람차로 내다보는 속초 바다", true, "강원 속초시", "30분", "91m", arrayListOf("test"), null))
//        recentCourseList.add(DefaultCourse("img", "행궁동 로컬 추천 코스", true, "경기 수원시", "3시간", "406m", arrayListOf("test"), null))
//        recentCourseList.add(DefaultCourse("img", "물고기, 고기", true, "제주도 서귀포", "5시간", "701m", arrayListOf("test"), null))
    }

    private fun initClickListener() {
        binding.followUserBackIv.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }
}