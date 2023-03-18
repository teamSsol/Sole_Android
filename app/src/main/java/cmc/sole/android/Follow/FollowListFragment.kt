package cmc.sole.android.Follow

import androidx.fragment.app.FragmentManager
import cmc.sole.android.MainActivity
import cmc.sole.android.Utils.BaseFragment
import cmc.sole.android.databinding.FragmentFollowListBinding
import com.google.android.material.tabs.TabLayoutMediator

class FollowListFragment: BaseFragment<FragmentFollowListBinding>(FragmentFollowListBinding::inflate) {

    private val information = arrayListOf("0 팔로워", "0 팔로잉")

    override fun initAfterBinding() {
        val followListVPAdapter = FollowListVPAdapter(this)
        binding.followListVp.adapter = followListVPAdapter
        TabLayoutMediator(binding.followListTb, binding.followListVp) {
                tab, position -> tab.text = information[position]
        }.attach()
        binding.followListVp.isUserInputEnabled = false

        initClickListener()
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