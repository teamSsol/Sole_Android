package cmc.sole.android.Home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.databinding.ItemCoursePopularBinding

class HomePopularCourseRVAdapter(private val courseList: ArrayList<PopularCourse>): RecyclerView.Adapter<HomePopularCourseRVAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(data: PopularCourse, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomePopularCourseRVAdapter.ViewHolder {
        val binding: ItemCoursePopularBinding = ItemCoursePopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomePopularCourseRVAdapter.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(courseList[position], position)
        }
        holder.bind(courseList[position])
    }

    override fun getItemCount(): Int = courseList.size

    inner class ViewHolder(val binding: ItemCoursePopularBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(course: PopularCourse) {
            // TODO: 이미지 URI 코드 연결
            // binding.itemCoursePopularIv.setImageResource()
            // binding.itemCoursePopularTitle.text = course.title
        }
    }
}