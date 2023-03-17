package cmc.sole.android.MyCourse

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemLongClickListener
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.Home.DefaultCourse
import cmc.sole.android.R
import cmc.sole.android.databinding.ItemMyCourseCourseBinding

class MyCourseCourseRVAdapter(private val courseList: ArrayList<DefaultCourse>): RecyclerView.Adapter<MyCourseCourseRVAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener
    private lateinit var itemLongClickListener: OnItemLongClickListener

    interface OnItemClickListener {
        fun onItemClick(data: DefaultCourse, position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(data: DefaultCourse, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        itemLongClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyCourseCourseRVAdapter.ViewHolder {
        val binding: ItemMyCourseCourseBinding = ItemMyCourseCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyCourseCourseRVAdapter.ViewHolder, position: Int) {
        holder.itemView.setOnLongClickListener {
            itemLongClickListener.onItemLongClick(courseList[position], position)
            holder.binding.myCourseCourseUncheckIv.visibility = View.VISIBLE
            true
        }
        holder.itemView.setOnClickListener {
            if (holder.binding.myCourseCourseUncheckIv.visibility == View.VISIBLE) {
                holder.binding.myCourseCourseUncheckIv.visibility = View.GONE
                holder.binding.myCourseCourseCheckIv.visibility = View.VISIBLE
            } else {
                holder.binding.myCourseCourseUncheckIv.visibility = View.VISIBLE
                holder.binding.myCourseCourseCheckIv.visibility = View.GONE
            }

            itemClickListener.onItemClick(courseList[position], position)
        }
        holder.bind(courseList[position])
    }

    override fun getItemCount(): Int = courseList.size

    inner class ViewHolder(val binding: ItemMyCourseCourseBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(course: DefaultCourse) {
//            if (course.title == "베이커리 맞은 편 일식당") {
//                binding.myCourseCourseIv.setImageResource(R.drawable.test_img_3)
//            } else if (course.title == "발리 다녀와서 파이") {
//                binding.myCourseCourseIv.setImageResource(R.drawable.test_img_4)
//            } else if (course.title == "관람차로 내다보는 속초 바다") {
//                binding.myCourseCourseIv.setImageResource(R.drawable.test_img_5)
//            } else if (course.title == "행궁동 로컬 추천 코스") {
//                binding.myCourseCourseIv.setImageResource(R.drawable.test_img_6)
//            } else if (course.title == "물고기, 고기") {
//                binding.myCourseCourseIv.setImageResource(R.drawable.test_img_7)
//            }
//
//            binding.myCourseCourseTitleTv.text = course.title
//            binding.myCourseCourseLocationTv.text = course.location
//            binding.myCourseCourseTimeTv.text = course.time
//            binding.myCourseCourseDistanceTv.text = course.distance
            
            // TODO: 태그 추가하기
        }
    }
}