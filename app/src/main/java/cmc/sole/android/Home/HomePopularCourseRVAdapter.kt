package cmc.sole.android.Home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.databinding.ItemCoursePopularBinding

class HomePopularCourseRVAdapter(private val courseList: ArrayList<PopularCourse>): RecyclerView.Adapter<HomePopularCourseRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomePopularCourseRVAdapter.ViewHolder {
        val binding: ItemCoursePopularBinding = ItemCoursePopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomePopularCourseRVAdapter.ViewHolder, position: Int) {
        holder.bind(courseList[position])
    }

    override fun getItemCount(): Int = courseList.size

    inner class ViewHolder(val binding: ItemCoursePopularBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(course: PopularCourse) {
            // TODO: 이미지 URI 코드 연결
            // binding.itemCoursePopularIv.setImageResource()
            binding.itemCoursePopularTitle.text = course.title
        }
    }
}