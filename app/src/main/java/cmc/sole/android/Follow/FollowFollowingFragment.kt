package cmc.sole.android.Follow

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Follow.Retrofit.FollowListView
import cmc.sole.android.Follow.Retrofit.FollowService
import cmc.sole.android.MainActivity
import cmc.sole.android.R
import cmc.sole.android.User
import cmc.sole.android.Utils.BaseFragment
import cmc.sole.android.databinding.FragmentFollowerFollowingBinding

class FollowFollowingFragment: BaseFragment<FragmentFollowerFollowingBinding>(FragmentFollowerFollowingBinding::inflate),
    FollowListView {

    private lateinit var followService: FollowService
    private lateinit var followListRVAdapter: FollowListRVAdapter
    private var followList = ArrayList<FollowUserDataResult>()

    override fun initAfterBinding() {
        initService()
        initAdapter()
    }

    override fun onResume() {
        super.onResume()
        followService.getFollowingList()
    }
    private fun initService() {
        followService = FollowService()
        followService.setFollowListView(this)
    }

    private fun initAdapter() {
        followListRVAdapter = FollowListRVAdapter(followList)
        binding.followFollowingRv.adapter = followListRVAdapter
        binding.followFollowingRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        followListRVAdapter.setOnItemClickListener(object: FollowListRVAdapter.OnItemClickListener {
            override fun onItemClick(data: FollowUserDataResult, position: Int) {
                if (followListRVAdapter.returnMode() == "followBtn") {
                    followService.followUnfollow(data.member.memberId)
                } else if (followListRVAdapter.returnMode() == "itemView") {
                    (context as MainActivity).supportFragmentManager.beginTransaction().replace(
                        R.id.main_fl,
                        FollowUserFragment().apply {
                            arguments = Bundle().apply {
                                putString("followInfoMemberSocialId", data.member.socialId)
                            }
                        }
                    ).addToBackStack("followerUser").commit()
                }
            }
        })
    }

    override fun followListSuccessView(followerResult: ArrayList<FollowUserDataResult>) {
        followListRVAdapter.addAllItems(followerResult)

        if (followerResult.size == 0) {
            binding.followerFollowingIv.setImageResource(R.drawable.ic_follow_empty_view)
        }
    }

    override fun followListFailureView() {
        showToast("팔로잉 유저 불러오기 실패")
    }
}