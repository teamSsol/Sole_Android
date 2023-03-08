package cmc.sole.android.Follow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.R
import cmc.sole.android.databinding.ItemFollowListBinding

class FollowListRVAdapter(private val followList: ArrayList<FollowListData>): RecyclerView.Adapter<FollowListRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemFollowListBinding = ItemFollowListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(followList[position])
    }

    override fun getItemCount(): Int = followList.size

    inner class ViewHolder(private val binding: ItemFollowListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(followList: FollowListData) {
            binding.itemFollowListProfileIv.setImageResource(R.drawable.test_img)
            binding.itemFollowListNicknameTv.text = followList.nickname
            binding.itemFollowListFollowerTv.text = followList.follower + " 팔로워"
            binding.itemFollowListFollowingTv.text = followList.following + " 팔로잉"

            if (followList.followFlag) {
                binding.itemFollowFollowingBtn.visibility = View.VISIBLE
                binding.itemFollowFollowBtn.visibility = View.GONE
            } else {
                binding.itemFollowFollowingBtn.visibility = View.GONE
                binding.itemFollowFollowBtn.visibility = View.VISIBLE
            }
        }
    }
}