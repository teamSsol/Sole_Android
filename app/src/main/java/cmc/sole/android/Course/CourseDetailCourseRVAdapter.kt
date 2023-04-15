package cmc.sole.android.Course

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.CourseTag.Categories
import cmc.sole.android.Follow.FollowUserDataResult
import cmc.sole.android.Home.*
import cmc.sole.android.R
import cmc.sole.android.User
import cmc.sole.android.databinding.ItemCourseDetailLineBinding
import cmc.sole.android.databinding.ItemCourseDetailNumberBinding
import com.bumptech.glide.Glide

class CourseDetailCourseRVAdapter(private val courseList: ArrayList<PlaceResponseDtos>): RecyclerView.Adapter<CourseDetailCourseRVAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

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
        holder.bind(courseList[position])
    }

    override fun getItemCount(): Int = courseList.size

    inner class ViewHolder(val binding: ItemCourseDetailNumberBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(course: PlaceResponseDtos) {
            // UPDATE: Mode 변수 추가 -> Mode 이용해서 View 변화주기
            if (course.viewType == simpleMode) {
                // MEMO: 접힌 상태
                binding.courseDetailDetailInfoLayout.visibility = View.GONE
                binding.courseDetailPlaceDescriptionTv.visibility = View.GONE
            } else if (course.viewType == detailMode) {
                // MEMO: 펼쳐진 상태
                binding.courseDetailDetailInfoLayout.visibility = View.VISIBLE
                binding.courseDetailPlaceDescriptionTv.visibility = View.VISIBLE
            }

            for (i in 0 until courseList.size) {
                if (course.address == courseList[i].address)
                    binding.courseDetailNumberTv.text = (i + 1).toString()
            }

            binding.courseDetailPlaceTitleTv.text = course.placeName
            binding.courseDetailPlaceDescriptionTv.text = course.description
            binding.courseDetailAddressTv.text = course.address

            if (course.placeImgUrls.size > 0)
                Glide.with(binding.root.context).load(course.placeImgUrls[0]).centerCrop().into(binding.courseDetailThumbnailImg)
            else binding.courseDetailThumbnailImgCv.visibility = View.GONE
        }
    }

    fun addItem(item: PlaceResponseDtos) {
        courseList.add(item)
        this.notifyDataSetChanged()
    }

    fun addAllItems(items: ArrayList<PlaceResponseDtos>) {
        courseList.clear()
        courseList.addAll(items)
        this.notifyDataSetChanged()
    }

    fun setViewType(option: Int) {
        for (i in 0 until courseList.size)
            courseList[i].viewType = option

        Log.d("API-TEST", "courseList = $courseList")
        this.notifyDataSetChanged()
    }

    fun returnViewType(): Int? {
        return courseList[0].viewType
    }
}