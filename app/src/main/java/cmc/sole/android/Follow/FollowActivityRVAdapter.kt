package cmc.sole.android.Follow

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.Follow.Retrofit.FollowCourseView
import cmc.sole.android.MyPage.FAQ.FAQData
import cmc.sole.android.R
import cmc.sole.android.databinding.ItemFollowActivityBinding
import com.bumptech.glide.Glide

class FollowActivityRVAdapter(private val followActivityList: ArrayList<FollowCourseResult>): RecyclerView.Adapter<FollowActivityRVAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(data: FollowCourseResult, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FollowActivityRVAdapter.ViewHolder {
        val binding: ItemFollowActivityBinding = ItemFollowActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowActivityRVAdapter.ViewHolder, position: Int) {
        holder.binding.itemFollowActivityHeartIv.setOnClickListener {
            followActivityList[position].like = !followActivityList[position].like
            itemClickListener.onItemClick(followActivityList[position], position)
            this.notifyDataSetChanged()
        }
        holder.bind(followActivityList[position])
    }

    override fun getItemCount(): Int = followActivityList.size

    inner class ViewHolder(val binding: ItemFollowActivityBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(followActivity: FollowCourseResult) {
            Glide.with(binding.root.context).load(followActivity.profileImg)
                .placeholder(R.drawable.ic_profile).centerCrop().circleCrop().into(binding.itemFollowActivityProfileIv)
            binding.itemFollowActivityNicknameTv.text = followActivity.nickname
            Glide.with(binding.root.context).load(followActivity.thumbnailImg).centerCrop().into(binding.itemFollowActivityIv)
            binding.itemFollowActivityCourseName.text = followActivity.title
            binding.itemFollowActivityCourseContent.text = followActivity.description

            if (followActivity.like) {
                binding.itemFollowActivityHeartIv.setImageResource(R.drawable.ic_heart_color)
            } else {
                binding.itemFollowActivityHeartIv.setImageResource(R.drawable.ic_heart_empty)
            }
        }
    }

    fun addItem(item: FollowCourseResult) {
        followActivityList.add(item)
        this.notifyDataSetChanged()
    }

    fun addAllItems(items: ArrayList<FollowCourseResult>) {
        followActivityList.addAll(items)
        this.notifyDataSetChanged()
    }
}
