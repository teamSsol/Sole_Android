package cmc.sole.android.Follow

import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.MainActivity
import cmc.sole.android.R
import cmc.sole.android.Utils.BaseFragment
import cmc.sole.android.databinding.FragmentFollowBinding

class FollowFragment: BaseFragment<FragmentFollowBinding>(FragmentFollowBinding::inflate) {

    lateinit var followActivityRVAdapter: FollowActivityRVAdapter
    private var followActivityList = ArrayList<FollowActivityData>()

    override fun initAfterBinding() {
        initAdapter()
        initClickListener()
    }

    private fun initAdapter() {
        followActivityRVAdapter = FollowActivityRVAdapter(followActivityList)
        binding.followActivityRv.adapter = followActivityRVAdapter
        binding.followActivityRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // MEMO: DUMMY DATA
        followActivityList.add(FollowActivityData("프로필", "닉네임 1", true, "테스트 이미지", "코스 이름 1", "안녕"))
        followActivityList.add(FollowActivityData("프로필", "닉네임 2", false, "테스트 이미지", "코스 이름 2", "안녕녕"))
        followActivityList.add(FollowActivityData("프로필", "닉네임 3", true, "테스트 이미지", "코스 이름 3", "안안녕"))
        followActivityList.add(FollowActivityData("프로필", "닉네임 4", true, "테스트 이미지", "코스 이름 4", "녕안녕"))
        followActivityList.add(FollowActivityData("프로필", "닉네임 5", false, "테스트 이미지", "코스 이름 5", "안녕안녕"))
        followActivityList.add(FollowActivityData("프로필", "닉네임 6", false, "테스트 이미지", "코스 이름 6", "안녕안녕"))
    }

    private fun initClickListener() {
        binding.followFollowIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_fl, FollowListFragment()).addToBackStack("FollowList").commit()
        }
    }
}