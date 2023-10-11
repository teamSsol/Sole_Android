package cmc.sole.android.Course

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.CourseTag.Categories
import cmc.sole.android.Follow.FollowUserDataResult
import cmc.sole.android.Home.*
import cmc.sole.android.Write.MyCourseWriteLocationImageRVAdapter
import cmc.sole.android.R
import cmc.sole.android.User
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewHorizontalDecoration
import cmc.sole.android.databinding.ItemCourseDetailLineBinding
import cmc.sole.android.databinding.ItemCourseDetailNumberBinding
import com.bumptech.glide.Glide

class CourseDetailCourseRVAdapter(private val courseList: ArrayList<PlaceResponseDtos>): RecyclerView.Adapter<CourseDetailCourseRVAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener
    private lateinit var imgRVAdapter: CourseDetailImgRVAdapter

    interface OnItemClickListener{
        fun onItemClick(data: FollowUserDataResult, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseDetailCourseRVAdapter.ViewHolder {
            val binding = ItemCourseDetailNumberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

    override fun onBindViewHolder(holder: CourseDetailCourseRVAdapter.ViewHolder, position: Int) {
        imgRVAdapter = CourseDetailImgRVAdapter(courseList[position].placeImgUrls)
        holder.binding.courseDetailPlaceImgRv.adapter = imgRVAdapter
        holder.binding.courseDetailPlaceImgRv.layoutManager = LinearLayoutManager(holder.binding.root.context, LinearLayoutManager.HORIZONTAL, false)
        holder.binding.courseDetailPlaceImgRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 20))

        if (position == courseList.size - 1) {
            holder.binding.courseDetailDotLine.visibility = View.GONE
        } else {
            holder.binding.courseDetailDotLine.visibility = View.VISIBLE
        }

        holder.bind(courseList[position])
    }

    override fun getItemCount(): Int = courseList.size

    inner class ViewHolder(val binding: ItemCourseDetailNumberBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(course: PlaceResponseDtos) {
            binding.courseDetailDetailInfoLayout.visibility = View.VISIBLE
            binding.courseDetailPlaceDescriptionTv.visibility = View.VISIBLE

            for (i in 0 until courseList.size) {
                if (course.address == courseList[i].address)
                    binding.courseDetailNumberTv.text = (i + 1).toString()

                if (course.address == "")
                    binding.courseDetailAddressTv.visibility = View.GONE
            }

            binding.courseDetailPlaceTitleTv.text = course.placeName
            binding.courseDetailPlaceDescriptionTv.text = course.description
            binding.courseDetailAddressTv.text = course.address
        }
    }

    fun addItem(item: PlaceResponseDtos) {
        courseList.add(item)
        this.notifyDataSetChanged()
    }

    fun addAllItems(items: ArrayList<PlaceResponseDtos>) {
        courseList.clear()
        for (i in 0 until items.size) {
            if (items[i].placeName.length > 1 && items[i].placeImgUrls.isNotEmpty() && items[i].address.length > 1) {
                courseList.add(items[i])
            }
        }
        this.notifyDataSetChanged()
    }
}