package cmc.sole.android.Scrap

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.Scrap.Retrofit.ScrapCourseResult
import cmc.sole.android.databinding.ItemMyCourseCourseBinding
import cmc.sole.android.databinding.ItemMyCourseCourseCheckBinding
import com.bumptech.glide.Glide

class ScrapCourseRVAdapter1(private val scrapCourseList: ArrayList<ScrapCourseResult>): RecyclerView.Adapter<ScrapCourseRVAdapter1.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener
    private lateinit var itemLongClickListener: OnItemLongClickListener

    interface OnItemClickListener {
        fun onItemClick(data: ScrapCourseResult, position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(data: ScrapCourseResult, position: Int)
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
    ): ViewHolder {
        val binding: ItemMyCourseCourseCheckBinding = ItemMyCourseCourseCheckBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            Log.d("API-TEST", "scrapCourseList 1 = $scrapCourseList")
            itemClickListener.onItemClick(scrapCourseList[position], position)
            for (i in 0 until scrapCourseList.size) {
                Log.d("API-TEST", "check")
                scrapCourseList[i].isChecked = true
            }
            this.notifyDataSetChanged()
            Log.d("API-TEST", "scrapCourseList 2 = $scrapCourseList")
            Log.d("API-TEST", "scrapCourseList 3 = $scrapCourseList")
//            if (holder.binding.myCourseCourseCheckIv.visibility == View.VISIBLE) {
//                holder.binding.myCourseCourseCheckIv.visibility = View.GONE
//            } else {
//                holder.binding.myCourseCourseCheckIv.visibility = View.VISIBLE
//            }
        }
        holder.bind(scrapCourseList[position])
    }

    override fun getItemCount(): Int = scrapCourseList.size

    inner class ViewHolder(val binding: ItemMyCourseCourseCheckBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(scrapCourse: ScrapCourseResult) {
            Glide.with(binding.root.context).load(scrapCourse.thumbnailImg).into(binding.myCourseCourseIv)
            binding.myCourseCourseTitleTv.text = scrapCourse.title
            binding.myCourseCourseLocationTv.text = scrapCourse.address
            binding.myCourseCourseTimeTv.text = scrapCourse.duration.toString() + "시간 소요"
            binding.myCourseCourseDistanceTv.text = scrapCourse.distance.toString() + "km 이동"
            // TODO: 태그 추가하기

            if (scrapCourse.isChecked) {
                binding.myCourseCourseUncheckIv.visibility = View.VISIBLE
            } else {
                binding.myCourseCourseCheckIv.visibility = View.GONE
            }
        }

        fun checkVisible() {
            binding.myCourseCourseCheckIv.visibility = View.GONE
            binding.myCourseCourseUncheckIv.visibility = View.VISIBLE
        }
    }

    fun addItem(item: ScrapCourseResult) {
        scrapCourseList.add(item)
        this.notifyDataSetChanged()
    }

    fun addAllItems(items: ArrayList<ScrapCourseResult>) {
        scrapCourseList.addAll(items)
        this.notifyDataSetChanged()
    }

    fun removeAllItems() {
        scrapCourseList.clear()
    }

    fun getAllItems(): ArrayList<ScrapCourseResult> {
        return scrapCourseList
    }

    fun removeItem(start: Int, end: Int?) {
        if (end != null) {
            for (i in start until end) {
                scrapCourseList.removeAt(i)
            }
        } else {
            scrapCourseList.removeAt(start)
        }
    }

    fun test() {

    }
}