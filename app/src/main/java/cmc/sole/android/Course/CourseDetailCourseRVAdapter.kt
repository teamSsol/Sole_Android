package cmc.sole.android.Course

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.Home.DefaultCourse
import cmc.sole.android.Home.courseDetailNumber
import cmc.sole.android.databinding.ItemCourseDetailLineBinding
import cmc.sole.android.databinding.ItemCourseDetailNumberBinding

class CourseDetailCourseRVAdapter(private val courseNameList: ArrayList<DefaultCourse?>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemViewType(position: Int): Int {
        return courseNameList[position]?.viewType!!
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            courseDetailNumber -> {
                val binding = ItemCourseDetailNumberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CourseNumberViewHolder(binding)
            } else -> {
                val binding = ItemCourseDetailLineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CourseLineViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (courseNameList[position]?.viewType) {
            courseDetailNumber -> {
                (holder as CourseDetailCourseRVAdapter.CourseNumberViewHolder).bind(courseNameList[position]!!)
                holder.setIsRecyclable(false)
            } else -> {
                (holder as CourseDetailCourseRVAdapter.CourseLineViewHolder).bind()
                holder.setIsRecyclable(false)
            }
        }
    }

    override fun getItemCount(): Int = courseNameList.size

    inner class CourseNumberViewHolder(val binding: ItemCourseDetailNumberBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(course: DefaultCourse) {
            binding.courseDetailNumberTitle.text = course.title
        }
    }

    inner class CourseLineViewHolder(val binding: ItemCourseDetailLineBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() { }
    }
}