package cmc.sole.android.MyCourse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.Home.DefaultCourse
import cmc.sole.android.R
import cmc.sole.android.databinding.ItemCourseDefaultBinding
import cmc.sole.android.databinding.ItemMyCourseCourseBinding

class MyCourseCourseRVAdapter(private val courseList: ArrayList<DefaultCourse>): RecyclerView.Adapter<MyCourseCourseRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyCourseCourseRVAdapter.ViewHolder {
        val binding: ItemMyCourseCourseBinding = ItemMyCourseCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyCourseCourseRVAdapter.ViewHolder, position: Int) {
        holder.bind(courseList[position])
    }

    override fun getItemCount(): Int = courseList.size

    inner class ViewHolder(val binding: ItemMyCourseCourseBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(course: DefaultCourse) {
            binding.myCourseCourseTitleTv.text = course.title
            binding.myCourseCourseLocationTv.text = course.location
            binding.myCourseCourseTimeTv.text = course.time
            binding.myCourseCourseDistanceTv.text = course.distance
            
            // TODO: 태그 추가하기
        }
    }
}