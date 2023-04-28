package cmc.sole.android.MyCourse

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemLongClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.Follow.FollowCourseResult
import cmc.sole.android.Home.DefaultCourse
import cmc.sole.android.R
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewHorizontalDecoration
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.Utils.Translator
import cmc.sole.android.databinding.ItemMyCourseCourseBinding
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayoutManager
import kotlin.math.roundToInt

class MyCourseCourseRVAdapter(private val courseList: ArrayList<DefaultCourse>): RecyclerView.Adapter<MyCourseCourseRVAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener
    private lateinit var tagRVAdapter: MyCourseTagRVAdapter
    private var tagList = ArrayList<String>()

    interface OnItemClickListener {
        fun onItemClick(data: DefaultCourse, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyCourseCourseRVAdapter.ViewHolder {
        val binding: ItemMyCourseCourseBinding = ItemMyCourseCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        tagRVAdapter = MyCourseTagRVAdapter(tagList)
        binding.myCourseCourseTagRv.adapter = tagRVAdapter
        binding.myCourseCourseTagRv.layoutManager = FlexboxLayoutManager(binding.root.context)
        binding.myCourseCourseTagRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 20))
        binding.myCourseCourseTagRv.addItemDecoration(RecyclerViewVerticalDecoration("top", 20))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyCourseCourseRVAdapter.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(courseList[position], position)
        }
        holder.bind(courseList[position])
    }

    override fun getItemCount(): Int = courseList.size

    inner class ViewHolder(val binding: ItemMyCourseCourseBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(course: DefaultCourse) {
            binding.myCourseCourseTitleTv.text = course.title
            Glide.with(binding.root.context).load(course.thumbnailImg).into(binding.myCourseCourseIv)
            binding.myCourseCourseLocationTv.text = course.address
            binding.myCourseCourseTimeTv.text = "${(course.duration.toDouble() / 60).toInt()} 시간 소요"
            binding.myCourseCourseDistanceTv.text = ((course.distance * 100.0).roundToInt() / 100.0).toString() + "km 이동"

            tagRVAdapter.clearItems()
            for (i in 0 until course.categories.size) {
                tagRVAdapter.addItem(Translator.tagEngToKor(binding.root.context as AppCompatActivity, course.categories.elementAt(i).toString()))
            }
            tagRVAdapter.addItem("")
        }
    }

    fun addItem(item: DefaultCourse) {
        courseList.add(item)
        this.notifyDataSetChanged()
    }

    fun addAllItems(items: ArrayList<DefaultCourse>) {
        courseList.clear()
        courseList.addAll(items)
        this.notifyDataSetChanged()
    }
}