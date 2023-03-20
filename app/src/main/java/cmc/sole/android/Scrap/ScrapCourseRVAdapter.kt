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
    private var checkFlag = 0

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
            if (checkFlag == 1) { // MEMO: 편집 모드
                scrapCourseList[position].isChecked = !scrapCourseList[position].isChecked
                if (holder.binding.myCourseCourseCheckIv.visibility == View.VISIBLE) {
                    holder.binding.myCourseCourseCheckIv.visibility = View.GONE
                } else {
                    holder.binding.myCourseCourseCheckIv.visibility = View.VISIBLE
                }
            } else { // MEMO: 기본 모드
                holder.binding.myCourseCourseCheckIv.visibility = View.GONE
                holder.binding.myCourseCourseUncheckIv.visibility = View.GONE
                
                for (i in 0 until scrapCourseList.size) {
                    scrapCourseList[i].checkMode = false
                    scrapCourseList[i].isChecked= false
                }
            }
            this.notifyDataSetChanged()
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

            if (scrapCourse.checkMode) {
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
        scrapCourseList.clear()
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
        if (end != null) {
            for (i in start until end) {
                scrapCourseList.removeAt(i)
            }
        } else {
            scrapCourseList.removeAt(start)
        }
    }

    fun checkMode(flag: Int): Int {
        checkFlag = flag
        return flag
    }
}