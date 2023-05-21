package cmc.sole.android.Follow

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Follow.Retrofit.FollowListView
import cmc.sole.android.Follow.Retrofit.FollowService
import cmc.sole.android.Follow.Retrofit.FollowUnfollowView
import cmc.sole.android.Follow.Retrofit.FollowUserInfoView
import cmc.sole.android.MainActivity
import cmc.sole.android.R
import cmc.sole.android.User
import cmc.sole.android.Utils.BaseFragment
import cmc.sole.android.databinding.FragmentFollowerFollowerBinding

class FollowFollowerFragment: BaseFragment<FragmentFollowerFollowerBinding>(FragmentFollowerFollowerBinding::inflate),
    FollowListView, FollowUnfollowView {

    private lateinit var followService: FollowService
    lateinit var followListRVAdapter: FollowListRVAdapter
    private var followList = ArrayList<FollowUserDataResult>()
    private var followUnfollowResult = false

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
        followService.setFollowUnfollowView(this)
    }

    private fun initAdapter() {
        followListRVAdapter = FollowListRVAdapter(followList)
        binding.followFollowerRv.adapter = followListRVAdapter
        binding.followFollowerRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

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
        Log.d("API-TEST", "followerResult = $followerResult")
        followListRVAdapter.addAllItems(followerResult)

        if (followerResult.size == 0) {
            binding.followerFollowerIv.setImageResource(R.drawable.ic_follow_empty_view)
        }
    }

    override fun followListFailureView() {
        showToast("팔로우 유저 불러오기 실패")
    }

    override fun followUnfollowSuccessView() {
        followUnfollowResult = true
        showToast("팔로우/언팔로우 성공")
    }

    override fun followUnfollowFailureView() {
        showToast("팔로우/언팔로우 실패")
    }
}