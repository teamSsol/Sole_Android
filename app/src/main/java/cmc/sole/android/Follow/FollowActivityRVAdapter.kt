package cmc.sole.android.Follow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.R
import cmc.sole.android.databinding.ItemFollowActivityBinding

class FollowActivityRVAdapter(private val followActivityList: ArrayList<FollowActivityData>): RecyclerView.Adapter<FollowActivityRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FollowActivityRVAdapter.ViewHolder {
        val binding: ItemFollowActivityBinding = ItemFollowActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowActivityRVAdapter.ViewHolder, position: Int) {
        holder.bind(followActivityList[position])
    }

    override fun getItemCount(): Int = followActivityList.size

    inner class ViewHolder(private val binding: ItemFollowActivityBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(followActivity: FollowActivityData) {
            // UPDATE: 이미지 데이터 받으면 연결해주기
            // binding.itemFollowActivityProfileIv.setImageResource(R.drawable.test_img)
//            if (followActivity.courseName == "행궁동 로컬 추천 코스")
//                binding.itemFollowActivityIv.setImageResource(R.drawable.test_img_6)
//            else if (followActivity.courseName == "물고기, 고기")
//                binding.itemFollowActivityIv.setImageResource(R.drawable.test_img_7)

            binding.itemFollowActivityNicknameTv.text = followActivity.nickname
            // UPDATE: 이미지 데이터 받으면 연결해주기
            // binding.itemFollowActivityIv.setImageResource(R.drawable.test_img_2)
            binding.itemFollowActivityCourseName.text = followActivity.courseName
            binding.itemFollowActivityCourseContent.text = followActivity.courseContent

            if (followActivity.heart) {
                binding.itemFollowActivityHeartIv.setImageResource(R.drawable.ic_heart_color)
            } else {
                binding.itemFollowActivityHeartIv.setImageResource(R.drawable.ic_heart_empty)
            }
        }
    }
}
