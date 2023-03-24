package cmc.sole.android.Home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.Follow.FollowCourseResult
import cmc.sole.android.Home.Retrofit.HomeScrapAddAndCancelView
import cmc.sole.android.Home.Retrofit.HomeService
import cmc.sole.android.MyCourse.MyCourseTagRVAdapter
import cmc.sole.android.R
import cmc.sole.android.databinding.ItemCoursePopularBinding
import com.bumptech.glide.Glide

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
            binding.itemCoursePopularTitle.text = course.courseName
            Glide.with(binding.root.context).load(course.thumbnailImg).centerCrop().into(binding.itemCoursePopularIv)
        }
    }

    fun addItem(item: PopularCourse) {
        courseList.add(item)
        this.notifyDataSetChanged()
    }

    fun addAllItems(items: ArrayList<PopularCourse>) {
        courseList.addAll(items)
        this.notifyDataSetChanged()
    }

    fun clearItems() {
        courseList.clear()
        this.notifyDataSetChanged()
    }
}