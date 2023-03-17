package cmc.sole.android.Home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.R
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
            if (course.courseName == "베이커리 맞은 편 일식당") {
                binding.itemCoursePopularIv.setImageResource(R.drawable.test_img_3)
            } else if (course.courseName == "발리 다녀와서 파이") {
                binding.itemCoursePopularIv.setImageResource(R.drawable.test_img_4)
            } else if (course.courseName == "관람차로 내다보는 속초 바다") {
                binding.itemCoursePopularIv.setImageResource(R.drawable.test_img_5)
            } else if (course.courseName == "행궁동 로컬 추천 코스") {
                binding.itemCoursePopularIv.setImageResource(R.drawable.test_img_6)
            } else if (course.courseName == "물고기, 고기") {
                binding.itemCoursePopularIv.setImageResource(R.drawable.test_img_7)
            }

            binding.itemCoursePopularTitle.text = course.courseName

            // TODO: 이미지 URI 코드 연결
            // binding.itemCoursePopularIv.setImageResource()
            // binding.itemCoursePopularTitle.text = course.title
        }
    }
}