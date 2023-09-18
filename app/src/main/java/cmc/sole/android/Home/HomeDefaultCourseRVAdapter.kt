package cmc.sole.android.Home

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
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
import cmc.sole.android.databinding.ItemCourseMoreBinding
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayoutManager


// UPDATE: viewType 제거하고 업데이트 필요
class HomeDefaultCourseRVAdapter(private val courseList: ArrayList<DefaultCourse>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            courseMoreItem -> {
                val binding: ItemCourseMoreBinding = ItemCourseMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CourseMoreItemViewHolder(binding)
            } else -> {
                val binding: ItemCourseDefaultBinding = ItemCourseDefaultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                tagRVAdapter = MyCourseTagRVAdapter(tagList)
                binding.courseDefaultTagRv.adapter = tagRVAdapter
                binding.courseDefaultTagRv.layoutManager = FlexboxLayoutManager(binding.root.context)
                binding.courseDefaultTagRv.addItemDecoration(RecyclerViewHorizontalDecoration("right", 20))
                binding.courseDefaultTagRv.addItemDecoration(RecyclerViewVerticalDecoration("top", 20))

                homeService = HomeService()
                CourseItemViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (courseList[position].viewType) {
            courseMoreItem -> {
                (holder as CourseMoreItemViewHolder).bind(courseList[position])
                holder.setIsRecyclable(false)
            } else -> {
                (holder as CourseItemViewHolder).itemView.setOnClickListener {
                    Log.d("API-TEST", "itemView")
                    itemClickListener.onItemClick(courseList[position], position, "all")
                }
                holder.binding.itemCourseHeartIv.setOnClickListener {
                    Log.d("API-TEST", "itemCourseHeartIv")
//                    if (courseList[position].like)
//                        holder.binding.itemCourseHeartIv.setImageResource(R.drawable.ic_heart_empty)
//                    else holder.binding.itemCourseHeartIv.setImageResource(R.drawable.ic_heart_color)
                    itemClickListener.onItemClick(courseList[position], position, "heart")

                    // homeService.scrapAddAndCancel(courseList[position].courseId)

//                    if (courseList[position].like) {
//                        homeService.scrapAddAndCancel(courseList[position].courseId, null)
//                    } else {
//                        val scrapSelectFolderBottomFragment = ScrapSelectFolderBottomFragment()
//                        var bundle = Bundle()
//                        bundle.putInt("courseId", courseList[position].courseId)
//                        scrapSelectFolderBottomFragment.arguments = bundle
//                        // scrapSelectFolderBottomFragment.show((context as AppCompatActivity).supportFragmentManager, "ScrapSelectFolderBottomFragment")
//                        scrapSelectFolderBottomFragment.show(childFragmentManager, "ScrapSelectFolderBottomFragment")
//                        scrapSelectFolderBottomFragment.setOnDialogFinishListener(object: ScrapSelectFolderBottomFragment.OnDialogFinishListener {
//                            override fun finish(isSuccess: Boolean) {
//                                // UPDATE: 성공 여부 받아오기
//                                if (isSuccess) {
//                                    courseList[position].like = !courseList[position].like
//                                }
//                            }
//                        })
//                    }

                    // courseList[position].like = !courseList[position].like
                }
                holder.bind(courseList[position])
                holder.setIsRecyclable(false)
            }
        }
    }

    override fun getItemCount(): Int = courseList.size

    inner class CourseItemViewHolder(val binding: ItemCourseDefaultBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(course: DefaultCourse) {
            Log.d("API-TEST", "6 bind = ${course.like}")
            binding.itemCourseTitleTv.text = course.title
            Glide.with(binding.root.context).load(course.thumbnailImg).into(binding.itemCourseImg)
            binding.itemCourseLocationTv.text = course.address
            binding.itemCourseTimeTv.text = "${(course.duration.toDouble() / 60).toInt()} 시간 소요"
            binding.itemCourseDistanceTv.text = course.distance.toInt().toString() + "km 이동"

            Log.d("API-TEST", "7 bind = ${course.like}")
            if (course.like) {
                binding.itemCourseHeartIv.setImageResource(R.drawable.ic_heart_color)
            } else {
                binding.itemCourseHeartIv.setImageResource(R.drawable.ic_heart_empty)
            }
            Log.d("API-TEST", "8 bind = ${course.like}")

            // TODO: 태그 추가하기
            tagRVAdapter.clearItems()
            for (i in 0 until course.categories.size) {
                tagRVAdapter.addItem(Translator.tagEngToKor(binding.root.context as AppCompatActivity, course.categories.elementAt(i).toString()))
            }
            tagRVAdapter.addItem("")
        }
    }

    inner class CourseMoreItemViewHolder(val binding: ItemCourseMoreBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(course: DefaultCourse) { }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(item: DefaultCourse) {
        courseList.add(item)
        this.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAllItems(items: ArrayList<DefaultCourse>) {
        courseList.addAll(items)
        this.notifyDataSetChanged()
    }

    fun returnAllItems(): ArrayList<DefaultCourse> {
        return courseList
    }

    fun returnItem(position: Int): DefaultCourse {
        return courseList[position]
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeAllItems() {
        courseList.clear()
        this.notifyDataSetChanged()
    }

    fun clearItems() {
        courseList.clear()
        this.notifyDataSetChanged()
    }

    fun changeLikeStatus(position: Int) {
        Log.d("API-TEST", "2 ${courseList[position].like}")
        courseList[position].like = !courseList[position].like
        Log.d("API-TEST", "3 ${courseList[position].like}")
        this.notifyItemChanged(position)
        Log.d("API-TEST", "4 ${courseList[position].like}")
    }
}