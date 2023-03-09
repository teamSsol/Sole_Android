package cmc.sole.android.Follow

import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Home.DefaultCourse
import cmc.sole.android.Home.HomeDefaultCourseRVAdapter
import cmc.sole.android.MainActivity
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
        binding.itemFollowUserFollowerTv.text = arguments?.getString("follower")
        binding.itemFollowUserFollowingTv.text = arguments?.getString("following")

        initAdapter()
        initClickListener()
    }

    private fun initAdapter() {
        followUserRecentRVAdapter = HomeDefaultCourseRVAdapter(recentCourseList)
        binding.followUserPopularRv.adapter = followUserRecentRVAdapter
        binding.followUserPopularRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        
        // MEMO: DUMMY DATA
        recentCourseList.add(DefaultCourse("img", "코스1 이름", false, "위치1", "시간1", "거리", arrayListOf("test")))
        recentCourseList.add(DefaultCourse("img", "코스2 이름", true, "위치2", "시간2", "거리", arrayListOf("test")))
        recentCourseList.add(DefaultCourse("img", "코스3 이름", true, "위치3", "시간3", "거리", arrayListOf("test")))
        recentCourseList.add(DefaultCourse("img", "코스4 이름", true, "위치4", "시간4", "거리", arrayListOf("test")))
        recentCourseList.add(DefaultCourse("img", "코스5 이름", true, "위치5", "시간5", "거리", arrayListOf("test")))
    }

    private fun initClickListener() {
        binding.followUserBackIv.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }
}