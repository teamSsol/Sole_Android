package cmc.sole.android.MyCourse

import android.support.v4.os.IResultReceiver.Default
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemLongClickListener
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.MyCourse.Write.MyCourseWriteLocationImageRVAdapter
import cmc.sole.android.Scrap.Retrofit.ScrapCourseResult
import cmc.sole.android.databinding.ItemMyCourseCourseBinding
import cmc.sole.android.databinding.ItemMyCourseCourseCheckBinding

class ScrapCourseRVAdapter2(private val scrapCourseList: ArrayList<ScrapCourseResult>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //    private lateinit var itemClickListener: OnItemClickListener
//    private lateinit var itemLongClickListener: OnItemLongClickListener
//
//    interface OnItemClickListener {
//        fun onItemClick(data: ScrapCourseResult, position: Int)
//    }
//
//    interface OnItemLongClickListener {
//        fun onUnCheckItemLongClick(dataList: ArrayList<ScrapCourseResult>)
//    }
//
////    interface OnItemLongClickListener {
////        fun onItemLongClick(data: ScrapCourseResult, position: Int)
////    }
//
//    fun setOnItemClickListener(listener: OnItemClickListener) {
//        itemClickListener = listener
//    }
//
//    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
//        itemLongClickListener = listener
//    }
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): RecyclerView.ViewHolder {
//        return when(viewType) {
//            checkCourse -> {
//                val binding: ItemMyCourseCourseCheckBinding = ItemMyCourseCourseCheckBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//                CheckViewHolder(binding)
//            } else -> {
//                val binding: ItemMyCourseCourseBinding = ItemMyCourseCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//                UnCheckViewHolder(binding)
//            }
//        }
//    }
//
////    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
////        when(scrapCourseList[position].viewType) {
////            checkCourse -> {
//////                holder.itemView.setOnLongClickListener {
//////                    itemLongClickListener.onItemLongClick(courseList[position], position)
//////                    holder.binding.myCourseCourseUncheckIv.visibility = View.VISIBLE
//////                    true
//////                }
//////                holder.itemView.setOnClickListener {
//////                    if (holder.binding.myCourseCourseUncheckIv.visibility == View.VISIBLE) {
//////                        holder.binding.myCourseCourseUncheckIv.visibility = View.GONE
//////                        holder.binding.myCourseCourseCheckIv.visibility = View.VISIBLE
//////                    } else {
//////                        holder.binding.myCourseCourseUncheckIv.visibility = View.VISIBLE
//////                        holder.binding.myCourseCourseCheckIv.visibility = View.GONE
//////                    }
//////
//////                    itemClickListener.onItemClick(courseList[position], position)
//////                }
//////                holder.bind(courseList[position])
////            } else -> {
////                holder.itemView.setOnLongClickListener {
////                    itemLongClickListener.onUnCheckItemLongClick(scrapCourseList)
////                    Log.d("API-TEST", "scrapCourseList Adapter = $scrapCourseList")
//////                    holder.binding.myCourseCourseUncheckIv.visibility = View.VISIBLE
////                    true
////                }
//////                holder.itemView.setOnClickListener {
//////                    if (holder.binding.myCourseCourseUncheckIv.visibility == View.VISIBLE) {
//////                        holder.binding.myCourseCourseUncheckIv.visibility = View.GONE
//////                        holder.binding.myCourseCourseCheckIv.visibility = View.VISIBLE
//////                    } else {
//////                        holder.binding.myCourseCourseUncheckIv.visibility = View.VISIBLE
//////                        holder.binding.myCourseCourseCheckIv.visibility = View.GONE
//////                    }
//////
//////                    itemClickListener.onItemClick(scrapCourseList[position], position)
//////                }
////                (holder as ScrapCourseRVAdapter2.UnCheckViewHolder).bind(scrapCourseList[position])
////                holder.setIsRecyclable(false)
////            }
////        }
//    }
//
//    override fun getItemCount(): Int = scrapCourseList.size
//
//    inner class CheckViewHolder(val binding: ItemMyCourseCourseCheckBinding): RecyclerView.ViewHolder(binding.root) {
//        fun bind(scrapCourse: ScrapCourseResult) {
////            if (course.title == "베이커리 맞은 편 일식당") {
////                binding.myCourseCourseIv.setImageResource(R.drawable.test_img_3)
////            } else if (course.title == "발리 다녀와서 파이") {
////                binding.myCourseCourseIv.setImageResource(R.drawable.test_img_4)
////            } else if (course.title == "관람차로 내다보는 속초 바다") {
////                binding.myCourseCourseIv.setImageResource(R.drawable.test_img_5)
////            } else if (course.title == "행궁동 로컬 추천 코스") {
////                binding.myCourseCourseIv.setImageResource(R.drawable.test_img_6)
////            } else if (course.title == "물고기, 고기") {
////                binding.myCourseCourseIv.setImageResource(R.drawable.test_img_7)
////            }
////
////            binding.myCourseCourseTitleTv.text = course.title
////            binding.myCourseCourseLocationTv.text = course.location
////            binding.myCourseCourseTimeTv.text = course.time
////            binding.myCourseCourseDistanceTv.text = course.distance
//
//            // TODO: 태그 추가하기
//        }
//    }
//
//    inner class UnCheckViewHolder(val binding: ItemMyCourseCourseBinding): RecyclerView.ViewHolder(binding.root) {
//        fun bind(scrapCourse: ScrapCourseResult) {
//
//        }
//    }
//
//    fun addItem(item: ScrapCourseResult) {
//        scrapCourseList.add(item)
//        this.notifyDataSetChanged()
//    }
//
//    fun addAllItems(items: ArrayList<ScrapCourseResult>) {
//        scrapCourseList.addAll(items)
//        this.notifyDataSetChanged()
//    }
//
//    fun removeItem(position: Int) {
//        scrapCourseList.removeAt(position)
//        this.notifyDataSetChanged()
//    }
//
//    fun removeAllItems() {
//        scrapCourseList.clear()
//        this.notifyDataSetChanged()
//    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}