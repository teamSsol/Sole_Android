package cmc.sole.android.Follow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Follow.Retrofit.FollowListView
import cmc.sole.android.Follow.Retrofit.FollowService
import cmc.sole.android.Follow.Retrofit.FollowUnfollowView
import cmc.sole.android.Follow.Retrofit.FollowUserInfoView
import cmc.sole.android.MainActivity
import cmc.sole.android.R
import cmc.sole.android.User

import cmc.sole.android.databinding.FragmentFollowerFollowerBinding

class FollowFollowerFragment: Fragment(),
    FollowListView, FollowUnfollowView {

    private var _binding: FragmentFollowerFollowerBinding? = null
    private val binding get() = _binding!!

    private lateinit var followService: FollowService
    lateinit var followListRVAdapter: FollowListRVAdapter
    private var followList = ArrayList<FollowUserDataResult>()
    private var followUnfollowResult = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerFollowerBinding.inflate(inflater, container, false)

        initService()
        initAdapter()

        return binding.root
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
        followListRVAdapter.addAllItems(followerResult)

        if (followerResult.size == 0) {
            binding.followerFollowerIv.setImageResource(R.drawable.ic_follower_empty_view)
        }
    }

    override fun followListFailureView() {
        var message = "팔로우 유저 불러오기 실패"
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun followUnfollowSuccessView() {
        followUnfollowResult = true
        var message = "팔로우/언팔로우 성공"
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun followUnfollowFailureView() {
        var message = "팔로우/언팔로우 실패"
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}