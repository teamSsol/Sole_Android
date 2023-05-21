package cmc.sole.android.Follow

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.R
import cmc.sole.android.databinding.ItemFollowListBinding
import com.bumptech.glide.Glide

class FollowListRVAdapter(private val followList: ArrayList<FollowUserDataResult>): RecyclerView.Adapter<FollowListRVAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener
    var clickMode = ""

    interface OnItemClickListener{
        fun onItemClick(data: FollowUserDataResult, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemFollowListBinding = ItemFollowListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // TODO: 팔로우 및 언팔로우 API 연동
        holder.binding.itemFollowFollowBtn.setOnClickListener {
            setMode("followBtn")
            itemClickListener.onItemClick(followList[position], position)
            holder.binding.itemFollowFollowBtn.visibility = View.GONE
            holder.binding.itemFollowFollowingBtn.visibility = View.VISIBLE
        }
        holder.binding.itemFollowFollowingBtn.setOnClickListener {
            setMode("followBtn")
            itemClickListener.onItemClick(followList[position], position)
            holder.binding.itemFollowFollowBtn.visibility = View.VISIBLE
            holder.binding.itemFollowFollowingBtn.visibility = View.GONE
        }
        holder.binding.itemFollowListLayout.setOnClickListener {
            setMode("itemView")
            itemClickListener.onItemClick(followList[position], position)
        }

        holder.bind(followList[position])
    }

    override fun getItemCount(): Int = followList.size

    inner class ViewHolder(val binding: ItemFollowListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(followList: FollowUserDataResult) {
            Glide.with(binding.root.context).load(followList.member.profileImgUrl)
                .placeholder(R.drawable.ic_profile).centerCrop().circleCrop().into(binding.itemFollowListProfileIv)
            binding.itemFollowListNicknameTv.text = followList.member.nickname
            binding.itemFollowListFollowerTv.text = followList.followerCount.toString() + " 팔로워"
            binding.itemFollowListFollowingTv.text = followList.followingCount.toString() + " 팔로잉"

            if (followList.followStatus == "FOLLOWER") {
                binding.itemFollowFollowingBtn.visibility = View.GONE
                binding.itemFollowFollowBtn.visibility = View.VISIBLE
            } else if (followList.followStatus == "FOLLOWING") {
                binding.itemFollowFollowingBtn.visibility = View.VISIBLE
                binding.itemFollowFollowBtn.visibility = View.GONE
            }
        }
    }

    fun addItem(item: FollowUserDataResult) {
        followList.add(item)
        this.notifyDataSetChanged()
    }

    fun addAllItems(items: ArrayList<FollowUserDataResult>) {
        followList.clear()
        followList.addAll(items)
        this.notifyDataSetChanged()
    }

    // UPDATE: 팔로잉 OR 팔로워 확인
    private fun setMode(mode: String): String {
        clickMode = mode
        return mode
    }

    fun returnMode(): String {
        return clickMode
    }
}