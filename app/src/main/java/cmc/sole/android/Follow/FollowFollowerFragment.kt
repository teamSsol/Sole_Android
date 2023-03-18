package cmc.sole.android.Follow

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Follow.Retrofit.FollowListView
import cmc.sole.android.Follow.Retrofit.FollowService
import cmc.sole.android.MainActivity
import cmc.sole.android.R
import cmc.sole.android.Utils.BaseFragment
import cmc.sole.android.databinding.FragmentFollowerFollowerBinding

class FollowFollowerFragment: BaseFragment<FragmentFollowerFollowerBinding>(FragmentFollowerFollowerBinding::inflate),
    FollowListView {

    private lateinit var followService: FollowService
    lateinit var followListRVAdapter: FollowListRVAdapter
    private var followList = ArrayList<FollowUserDataResult>()

    override fun initAfterBinding() {
        initService()
        initAdapter()
    }

    override fun onResume() {
        super.onResume()
        followService.getFollowerList()
    }

    private fun initService() {
        followService = FollowService()
        followService.setFollowListView(this)
    }

    private fun initAdapter() {
        followListRVAdapter = FollowListRVAdapter(followList)
        binding.followFollowerRv.adapter = followListRVAdapter
        binding.followFollowerRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        followListRVAdapter.setOnItemClickListener(object: FollowListRVAdapter.OnItemClickListener {
            override fun onItemClick(data: FollowUserDataResult, position: Int) {
                (context as MainActivity).supportFragmentManager.beginTransaction().replace(
                    R.id.main_fl,
                    FollowUserFragment().apply {
                        arguments = Bundle().apply {
                            putString("profileImg", data.member.profileImgUrl)
                            putString("nickname", data.member.nickname)
                            putString("follower", data.followerCount.toString())
                            putString("following", data.followingCount.toString())
                        }
                    }
                ).addToBackStack("followerUser").commit()
            }
        })
    }

    override fun followListSuccessView(followerResult: ArrayList<FollowUserDataResult>) {
        followListRVAdapter.addAllItems(followerResult)
    }

    override fun followListFailureView() {
        showToast("팔로우 유저 불러오기 실패")
    }
}