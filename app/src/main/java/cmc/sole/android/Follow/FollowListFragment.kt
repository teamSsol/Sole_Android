package cmc.sole.android.Follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import cmc.sole.android.MainActivity

import cmc.sole.android.databinding.FragmentFollowListBinding
import com.google.android.material.tabs.TabLayoutMediator
import androidx.fragment.app.Fragment
import cmc.sole.android.databinding.FragmentFollowerFollowerBinding

class FollowListFragment: Fragment() {

    private var _binding: FragmentFollowListBinding? = null
    private val binding get() = _binding!!

    private val information = arrayListOf("팔로워", "팔로잉")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowListBinding.inflate(inflater, container, false)

        val followListVPAdapter = FollowListVPAdapter(this)
        binding.followListVp.adapter = followListVPAdapter
        TabLayoutMediator(binding.followListTb, binding.followListVp) {
                tab, position -> tab.text = information[position]
        }.attach()
        binding.followListVp.isUserInputEnabled = false

        initClickListener()

        return binding.root
    }

    private fun initClickListener() {
        binding.followListBackIv.setOnClickListener {
            clearBackStack()
        }
    }

    private fun clearBackStack() {
        val fragmentManager: FragmentManager = (context as MainActivity).supportFragmentManager
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}