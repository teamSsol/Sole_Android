package cmc.sole.android.Home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.R
import cmc.sole.android.databinding.ItemCourseDefaultBinding

class HomeDefaultCourseRVAdapter(private val courseList: ArrayList<DefaultCourse>): RecyclerView.Adapter<HomeDefaultCourseRVAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(data: DefaultCourse, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeDefaultCourseRVAdapter.ViewHolder {
        val binding: ItemCourseDefaultBinding = ItemCourseDefaultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeDefaultCourseRVAdapter.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(courseList[position], position)
        }
        holder.bind(courseList[position])
    }

    override fun getItemCount(): Int = courseList.size

    inner class ViewHolder(val binding: ItemCourseDefaultBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(course: DefaultCourse) {
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