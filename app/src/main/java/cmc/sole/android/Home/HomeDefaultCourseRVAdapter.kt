package cmc.sole.android.Home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.Home.Retrofit.HomeScrapAddAndCancelView
import cmc.sole.android.Home.Retrofit.HomeService
import cmc.sole.android.MyCourse.MyCourseTagRVAdapter
import cmc.sole.android.R
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewHorizontalDecoration
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.Utils.Translator
import cmc.sole.android.databinding.ItemCourseDefaultBinding
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayoutManager

class HomeDefaultCourseRVAdapter(private val courseList: ArrayList<DefaultCourse>): RecyclerView.Adapter<cmc.sole.android.Home.HomeDefaultCourseRVAdapter.ViewHolder>(),
    HomeScrapAddAndCancelView {

    private lateinit var itemClickListener: OnItemClickListener
    private lateinit var tagRVAdapter: MyCourseTagRVAdapter
    private var tagList = ArrayList<String>()
    private lateinit var homeService: HomeService

    interface OnItemClickListener {
        fun onItemClick(data: DefaultCourse, position: Int, mode: String)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeDefaultCourseRVAdapter.ViewHolder {
        val binding: ItemCourseDefaultBinding = ItemCourseDefaultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        tagRVAdapter = MyCourseTagRVAdapter(tagList)
        binding.courseDefaultTagRv.adapter = tagRVAdapter
        binding.courseDefaultTagRv.layoutManager = FlexboxLayoutManager(binding.root.context)
        binding.courseDefaultTagRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 20))
        binding.courseDefaultTagRv.addItemDecoration(RecyclerViewVerticalDecoration("top", 20))

        homeService = HomeService()
        homeService.setHomeScrapAddAndCancelView(this)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeDefaultCourseRVAdapter.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(courseList[position], position, "all")
        }
        holder.binding.itemCourseHeartIv.setOnClickListener {
            if (courseList[position].like)
                holder.binding.itemCourseHeartIv.setImageResource(R.drawable.ic_heart_empty)
            else holder.binding.itemCourseHeartIv.setImageResource(R.drawable.ic_heart_color)

            homeService.scrapAddAndCancel(courseList[position].courseId)
            courseList[position].like = !courseList[position].like

            Log.d("API-TEST", "LIKE = ${courseList[position].like}")
        }
        holder.bind(courseList[position])
    }

    override fun getItemCount(): Int = courseList.size

    inner class ViewHolder(val binding: ItemCourseDefaultBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(course: DefaultCourse) {
            binding.itemCourseTitleTv.text = course.title
            Glide.with(binding.root.context).load(course.thumbnailImg).into(binding.itemCourseImg)
            binding.itemCourseLocationTv.text = course.address
            binding.itemCourseTimeTv.text = "${(course.duration.toDouble() / 60).toInt()} 시간 소요"
            binding.itemCourseDistanceTv.text = course.distance.toInt().toString() + "km 이동"

            if (course.like) {
                binding.itemCourseHeartIv.setImageResource(R.drawable.ic_heart_color)
            } else {
                binding.itemCourseHeartIv.setImageResource(R.drawable.ic_heart_empty)
            }

            // TODO: 태그 추가하기
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

    fun clearItems() {
        courseList.clear()
        this.notifyDataSetChanged()
    }

    override fun homeScrapAddAndCancelSuccessView() {

    }

    override fun homeScrapAddAndCancelFailureView() {

    }
}