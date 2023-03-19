package cmc.sole.android.Scrap

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.Scrap.Retrofit.ScrapCourseResult
import cmc.sole.android.databinding.ItemMyCourseCourseBinding
import com.bumptech.glide.Glide

class ScrapCourseRVAdapter(private val scrapCourseList: ArrayList<ScrapCourseResult>): RecyclerView.Adapter<ScrapCourseRVAdapter.ViewHolder>() {

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
        val binding: ItemMyCourseCourseBinding = ItemMyCourseCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(scrapCourseList[position], position)
            if (flag == 1) { // MEMO: 편집 모드
                if (holder.binding.myCourseCourseCheckIv.visibility == View.VISIBLE) {
                    holder.binding.myCourseCourseCheckIv.visibility = View.GONE
                } else {
                    holder.binding.myCourseCourseCheckIv.visibility = View.VISIBLE
                }
            } else { // MEMO: 기본 모드
                holder.binding.myCourseCourseCheckIv.visibility = View.GONE
                holder.binding.myCourseCourseUncheckIv.visibility = View.GONE
                
                for (i in 0 until scrapCourseList.size) {
                    Log.d("API-TEST", "check")
                    scrapCourseList[i].isChecked = false
                }
            }
        }
        holder.bind(scrapCourseList[position])
    }

    override fun getItemCount(): Int = scrapCourseList.size

    inner class ViewHolder(val binding: ItemMyCourseCourseBinding): RecyclerView.ViewHolder(binding.root) {
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
                binding.myCourseCourseUncheckIv.visibility = View.GONE
                binding.myCourseCourseCheckIv.visibility = View.GONE
            }
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

    fun getItems(position: Int): ScrapCourseResult {
        return scrapCourseList[position]
    }

    fun removeItem(start: Int, end: Int?) {
        Log.d("API-TEST", "start = $start, end = $end")
        if (end != null) {
            for (i in start until end) {
                Log.d("API-TEST", "i = $i")
                scrapCourseList.removeAt(i)
            }
        } else {
            scrapCourseList.removeAt(start)
        }
    }

    var flag = 0

    fun test(flag: Int): Int {
        this.flag = flag
        return flag
    }
}