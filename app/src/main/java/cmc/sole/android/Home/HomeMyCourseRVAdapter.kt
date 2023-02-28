package cmc.sole.android.Home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.R
import cmc.sole.android.databinding.ItemCourseMineBinding

class HomeMyCourseRVAdapter(private val courseList: ArrayList<MyCourse>): RecyclerView.Adapter<HomeMyCourseRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeMyCourseRVAdapter.ViewHolder {
        val binding: ItemCourseMineBinding = ItemCourseMineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeMyCourseRVAdapter.ViewHolder, position: Int) {
        holder.bind(courseList[position])
    }

    override fun getItemCount(): Int = courseList.size

    inner class ViewHolder(val binding: ItemCourseMineBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(course: MyCourse) {
            binding.itemCourseTitleTv.text = course.title

            if (course.scrap)
                binding.itemCourseHeartIv.setImageResource(R.drawable.ic_heart_color)
            else binding.itemCourseHeartIv.setImageResource(R.drawable.ic_heart_2)

            binding.itemCourseLocationTv.text = course.location
            binding.itemCourseTimeTv.text = course.time
            binding.itemCourseDistanceTv.text = course.distance
            
            // TODO: 태그 추가하기
        }
    }
}