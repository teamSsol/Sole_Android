package cmc.sole.android.Course

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.CourseTag.Categories
import cmc.sole.android.Follow.FollowUserDataResult
import cmc.sole.android.Home.*
import cmc.sole.android.Write.MyCourseWriteLocationImageRVAdapter
import cmc.sole.android.R
import cmc.sole.android.User
import cmc.sole.android.databinding.ItemCourseDetailLineBinding
import cmc.sole.android.databinding.ItemCourseDetailNumberBinding
import cmc.sole.android.databinding.ItemCourseDetailPlaceImgBinding
import com.bumptech.glide.Glide

class CourseDetailImgRVAdapter(private val imgList: ArrayList<String>): RecyclerView.Adapter<CourseDetailImgRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseDetailImgRVAdapter.ViewHolder {
        val binding = ItemCourseDetailPlaceImgBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseDetailImgRVAdapter.ViewHolder, position: Int) {
        holder.bind(imgList[position])
    }

    override fun getItemCount(): Int = imgList.size

    inner class ViewHolder(val binding: ItemCourseDetailPlaceImgBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(imgUrl: String) {
            Glide.with(binding.root.context).load(imgUrl).centerCrop().into(binding.courseDetailThumbnailImg)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(item: String) {
        imgList.add(item)
        this.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAllItems(items: ArrayList<String>) {
        imgList.addAll(items)
        this.notifyDataSetChanged()
    }

    fun getAllItems(): ArrayList<String> {
        return imgList
    }
}