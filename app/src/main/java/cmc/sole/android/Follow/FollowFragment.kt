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
//        followActivityList.add(FollowActivityData("프로필", "밍밍", true, "테스트 이미지", "행궁동 로컬 추천 코스", "안녕"))
//        followActivityList.add(FollowActivityData("프로필", "제이", false, "테스트 이미지", "물고기, 고기", "안녕녕"))
    }

    private fun initClickListener() {
        binding.followFollowIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_fl, FollowListFragment()).addToBackStack("FollowList").commit()
        }
    }
}